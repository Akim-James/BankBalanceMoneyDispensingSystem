package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.dto.DenominationDto;
import com.discoverybank.bbds.exception.WithdrawalException;
import com.discoverybank.bbds.mapper.ClientAccountMapper;
import com.discoverybank.bbds.repository.AccountTypeCode;
import com.discoverybank.bbds.repository.AtmRepository;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.Atm;
import com.discoverybank.bbds.repository.entities.AtmAllocation;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.web.model.CashWithdrawalResponse;
import com.discoverybank.bbds.web.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The CashWithdrawalService class provides functionality for handling
 * cash withdrawal operations from an ATM. The service interacts with
 * repositories for managing ATM and client account data and ensures
 * withdrawal transactions are executed securely and accurately.
 */
@Slf4j
@Service
public class CashWithdrawalService {

    private static final BigDecimal MAX_OVERDRAFT_LIMIT = new BigDecimal("10000"); // Maximum overdraft limit for cheque accounts

    private final AtmRepository atmRepository;
    private final ClientAccountRepository clientAccountRepository;

    public CashWithdrawalService(AtmRepository atmRepository, ClientAccountRepository clientAccountRepository) {
        this.atmRepository = atmRepository;
        this.clientAccountRepository = clientAccountRepository;
    }

    /**
     * Processes a withdrawal request from a client's account using an ATM.
     * This method performs validation on the client's account and ATM
     * funds, updates the ATM and account balances, and generates a response
     * with details about the dispensed denominations and transaction result.
     * <p>
     * Allows withdrawal from a transactional account if the balance is positive,
     * or if the account is a cheque account, permits an overdraft up to R 10,000.
     *
     * @param clientId the unique identifier of the client initiating the withdrawal
     * @param accountNumber the account number of the client from which the funds are to be withdrawn
     * @param atmId the unique identifier of the ATM from which funds are being dispensed
     * @param requiredAmount the amount requested by the client for withdrawal
     * @return a {@link CashWithdrawalResponse} containing the account details,
     * details of the dispensed denominations, and the result of the transaction
     * @throws WithdrawalException if the account or ATM validations fail
     */
    @Transactional
    public CashWithdrawalResponse withdrawFromAccount(Integer clientId, Long accountNumber, Integer atmId, BigDecimal requiredAmount) {
        log.info("Initiating withdrawal for clientId: {}, accountNumber: {}, atmId: {}, requiredAmount: {}", clientId, accountNumber, atmId, requiredAmount);
        ClientAccount clientAccount = fetchClientAccount(clientId, accountNumber);
        Atm atm = fetchAtm(atmId);

        verifySufficientFunds(clientAccount, requiredAmount);
        verifySufficientAtmFunds(atm, requiredAmount);

        List<DenominationDto> dispensedDenominations = dispenseCash(atm, requiredAmount);
        updateClientAccountBalance(clientAccount, requiredAmount);
        saveEntities(clientAccount, atm);

        CashWithdrawalResponse response = buildWithdrawalResponse(clientAccount, dispensedDenominations);
        log.info("Withdrawal completed successfully for clientId: {} with response: {}", clientId, response);

        return response;
    }

    /**
     * Fetches the client account associated with the given client ID and account number.
     * Logs the operation and throws a WithdrawalException if the account is not found.
     *
     * @param clientId the unique identifier of the client
     * @param accountNumber the account number belonging to the client
     * @return the {@link ClientAccount} object representing the client's account
     * @throws WithdrawalException if the account is not found or invalid
     */
    private ClientAccount fetchClientAccount(Integer clientId, Long accountNumber) {
        log.debug("Fetching client account for clientId: {}, accountNumber: {}", clientId, accountNumber);
        ClientAccount clientAccount = clientAccountRepository.findByClientIdAndAccountNumber(clientId, accountNumber);
        if (clientAccount == null) {
            log.error("Invalid account number for clientId: {}, accountNumber: {}", clientId, accountNumber);
            throw new WithdrawalException("Invalid account number");
        }
        return clientAccount;
    }

    /**
     * Fetches the ATM information for the specified ATM ID.
     * This method logs the operation and throws a WithdrawalException
     * if the ATM is not found or is unfunded.
     *
     * @param atmId the unique identifier of the ATM to be fetched
     * @return the {@link Atm} object representing the ATM information
     * @throws WithdrawalException if the ATM is not registered or is unfunded
     */
    private Atm fetchAtm(Integer atmId) {
        log.debug("Fetching ATM with atmId: {}", atmId);
        return atmRepository.findById(atmId).orElseThrow(() -> {
            log.error("ATM not registered or unfunded with atmId: {}", atmId);
            return new WithdrawalException("ATM not registered or unfunded");
        });
    }

    /**
     * Verifies if the ATM has sufficient funds to fulfill the required withdrawal amount.
     * Calculates the total available cash in the ATM by summing up the product of
     * the denomination value and count for each ATM allocation. Throws a
     * {@link WithdrawalException} if the available cash is less than the required amount.
     *
     * @param atm the {@link Atm} object representing the ATM from which funds are to be withdrawn.
     * @param requiredAmount the {@link BigDecimal} value indicating the amount to be withdrawn.
     * @throws WithdrawalException if the available cash in the ATM is insufficient
     * to meet the required withdrawal amount.
     */
    private void verifySufficientAtmFunds(Atm atm, BigDecimal requiredAmount) {
        BigDecimal availableCash = atm.getAtmAllocations().stream()
                .map(allocation -> allocation.getDenomination().getDenominationValue().multiply(BigDecimal.valueOf(allocation.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.debug("Available cash in atmId: {} is {}", atm.getAtmId(), availableCash);
        if (availableCash.compareTo(requiredAmount) < 0) {
            log.error("Insufficient funds in atmId: {}. Available cash: {}. Required amount: {}", atm.getAtmId(), availableCash, requiredAmount);
            throw new WithdrawalException("Insufficient funds available. You may choose to withdraw " + availableCash);
        }
    }

    /**
     * Dispenses cash from the given ATM based on the required amount.
     * The method calculates the denominations to be dispensed starting from the largest available
     * denomination in the ATM, ensuring it covers the requested amount or throws an exception if the
     * exact amount cannot be dispensed.
     *
     * @param atm the ATM object containing available cash allocations and their respective denominations
     * @param requiredAmount the total amount of cash requested to be dispensed
     * @return a list of {@code DenominationDto} representing the dispensed denominations and their respective counts
     * @throws WithdrawalException if the exact amount cannot be dispensed due to insufficient or mismatched denominations
     */
    private List<DenominationDto> dispenseCash(Atm atm, BigDecimal requiredAmount) {
        log.debug("Dispensing cash for required amount: {}", requiredAmount);
        BigDecimal remainingAmount = requiredAmount;
        List<AtmAllocation> allocations = atm.getAtmAllocations().stream()
                .sorted(Comparator.comparing(a -> a.getDenomination().getDenominationValue(), Comparator.reverseOrder()))
                .toList();

        List<DenominationDto> dispensedDenominations = new ArrayList<>();

        for (AtmAllocation allocation : allocations) {
            BigDecimal denominationValue = allocation.getDenomination().getDenominationValue();

            if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            int maxNotesToUse = remainingAmount.divide(denominationValue, RoundingMode.DOWN).intValue();
            int notesAvailable = allocation.getCount();
            int notesToUse = Math.min(maxNotesToUse, notesAvailable);

            allocation.setCount(notesAvailable - notesToUse);
            remainingAmount = remainingAmount.subtract(denominationValue.multiply(BigDecimal.valueOf(notesToUse)));

            if (notesToUse > 0) {
                dispensedDenominations.add(DenominationDto.builder()
                        .denominationId(allocation.getDenomination().getDenominationId().longValue())
                        .denominationValue(denominationValue)
                        .count(notesToUse)
                        .build());
            }
        }

        if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
            log.error("Cannot dispense exact amount. Remaining amount: {}", remainingAmount);
            throw new WithdrawalException("Cannot dispense the exact amount requested. Remaining amount: " + remainingAmount);
        }

        log.debug("Dispensed denominations: {}", dispensedDenominations);
        return dispensedDenominations;
    }


    private void saveEntities(ClientAccount clientAccount, Atm atm) {
        log.debug("Saving updated client account and ATM entities.");
        clientAccountRepository.save(clientAccount);
        atmRepository.save(atm);
    }

    /**
     * Builds a cash withdrawal response for a given client account and list of dispensed denominations.
     *
     * @param clientAccount the client account from which the withdrawal is made
     * @param dispensedDenominations the list of denominations dispensed as part of the withdrawal
     * @return a CashWithdrawalResponse object containing account details, dispensed denominations, and a result status
     */
    private CashWithdrawalResponse buildWithdrawalResponse(ClientAccount clientAccount, List<DenominationDto> dispensedDenominations) {
        log.debug("Building withdrawal response for clientId: {}", clientAccount.getClient().getClientId());
        return CashWithdrawalResponse.builder()
                .account(ClientAccountMapper.INSTANCE.toDTO(clientAccount))
                .denominations(dispensedDenominations)
                .result(new Result(true, 200, "Withdrawal successful"))
                .build();
    }

    private void updateClientAccountBalance(ClientAccount clientAccount, BigDecimal requiredAmount) {
        log.debug("Updating client account balance for clientId: {} by deducting {}", clientAccount.getClient().getClientId(), requiredAmount);
        clientAccount.setDisplayBalance(clientAccount.getDisplayBalance().subtract(requiredAmount));
    }

    /**
     * Verifies if the client's account has sufficient funds to cover the withdrawal.
     * For cheque accounts, the method allows overdraft up to a defined maximum limit.
     *
     * @param clientAccount the {@link ClientAccount} object representing the client's account.
     * @param requiredAmount the {@link BigDecimal} value indicating the amount to be withdrawn.
     * @throws WithdrawalException if the client's account balance is insufficient to cover the withdrawal.
     */
    private void verifySufficientFunds(ClientAccount clientAccount, BigDecimal requiredAmount) {
        BigDecimal currentBalance = clientAccount.getDisplayBalance();
        BigDecimal newBalance = currentBalance.subtract(requiredAmount);

        if (AccountTypeCode.CHEQUE.getCode().equals(clientAccount.getAccountType().getAccountTypeCode())) {
            // Cheque accounts can go negative up to MAX_OVERDRAFT_LIMIT
            if (newBalance.compareTo(MAX_OVERDRAFT_LIMIT.negate()) < 0) {
                log.error("Insufficient funds for withdrawal from cheque account. Current balance: {}, Required amount: {}, Overdraft limit: {}",
                        currentBalance, requiredAmount, MAX_OVERDRAFT_LIMIT);
                throw new WithdrawalException("Overdraft limit exceeded for cheque account. Maximum allowable overdraft is R " + MAX_OVERDRAFT_LIMIT);
            }
        } else {
            // Other accounts must maintain positive balance
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                log.error("Insufficient funds for withdrawal. Current balance: {}, Required amount: {}", currentBalance, requiredAmount);
                throw new WithdrawalException("Insufficient funds available in your account.");
            }
        }
    }
}