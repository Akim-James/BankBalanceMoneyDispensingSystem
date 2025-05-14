package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.dto.DenominationDto;
import com.discoverybank.bbds.exception.WithdrawalException;
import com.discoverybank.bbds.mapper.ClientAccountMapper;
import com.discoverybank.bbds.repository.AtmRepository;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.Atm;
import com.discoverybank.bbds.repository.entities.AtmAllocation;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.web.model.CashWithdrawalResponse;
import com.discoverybank.bbds.web.model.Result;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CashWithdrawalService {
    private final AtmRepository atmRepository;
    private final ClientAccountRepository clientAccountRepository;

    public CashWithdrawalService(AtmRepository atmRepository, ClientAccountRepository clientAccountRepository) {
        this.atmRepository = atmRepository;
        this.clientAccountRepository = clientAccountRepository;
    }

    public CashWithdrawalResponse withdrawFromAccount(Integer clientId, Long accountNumber, Integer atmId, BigDecimal requiredAmount) {
        ClientAccount clientAccount = fetchClientAccount(clientId, accountNumber);
        Atm atm = fetchAtm(atmId);

        verifySufficientAtmFunds(atm, requiredAmount);

        List<DenominationDto> dispensedDenominations = dispenseCash(atm, requiredAmount);
        updateClientAccountBalance(clientAccount, requiredAmount);
        saveEntities(clientAccount, atm);

        return buildWithdrawalResponse(clientAccount, dispensedDenominations);
    }

    private ClientAccount fetchClientAccount(Integer clientId, Long accountNumber) {
        return clientAccountRepository.findByClientIdAndAccountNumber(clientId, accountNumber);
    }

    private Atm fetchAtm(Integer atmId) {
        return atmRepository.findById(atmId).orElseThrow(() -> new WithdrawalException("ATM not registered or unfunded"));
    }

    private void verifySufficientAtmFunds(Atm atm, BigDecimal requiredAmount) {
        BigDecimal availableCash = atm.getAtmAllocations().stream()
                .map(allocation -> allocation.getDenomination().getDenominationValue().multiply(BigDecimal.valueOf(allocation.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (availableCash.compareTo(requiredAmount) < 0) {
            throw new WithdrawalException("Insufficient funds available. You may choose to withdraw " + availableCash);
        }
    }

    private List<DenominationDto> dispenseCash(Atm atm, BigDecimal requiredAmount) {
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
            throw new WithdrawalException("Cannot dispense the exact amount requested. Remaining amount: " + remainingAmount);
        }

        return dispensedDenominations;
    }

    private void updateClientAccountBalance(ClientAccount clientAccount, BigDecimal requiredAmount) {
        clientAccount.setDisplayBalance(clientAccount.getDisplayBalance().subtract(requiredAmount));
    }

    private void saveEntities(ClientAccount clientAccount, Atm atm) {
        clientAccountRepository.save(clientAccount);
        atmRepository.save(atm);
    }

    private CashWithdrawalResponse buildWithdrawalResponse(ClientAccount clientAccount, List<DenominationDto> dispensedDenominations) {
        return CashWithdrawalResponse.builder()
                .account(ClientAccountMapper.INSTANCE.toDTO(clientAccount))
                .denominations(dispensedDenominations)
                .result(new Result(true, 200, "Withdrawal successful"))
                .build();
    }
}