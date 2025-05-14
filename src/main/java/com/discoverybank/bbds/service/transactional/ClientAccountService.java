package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.config.BbdsConfig;
import com.discoverybank.bbds.exception.NoAccountsToDisplayException;
import com.discoverybank.bbds.exception.UserNotFoundException;
import com.discoverybank.bbds.mapper.ClientAccountMapper;
import com.discoverybank.bbds.mapper.ClientMapper;
import com.discoverybank.bbds.repository.AccountTypeCode;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.web.model.ClientAccountResponse;
import com.discoverybank.bbds.web.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientAccountService {

    private final BbdsConfig bbdsConfig;
    private final ClientAccountRepository accountRepository;

    public ClientAccountService(ClientAccountRepository accountRepository, BbdsConfig bbdsConfig) {
        this.accountRepository = accountRepository;
        this.bbdsConfig = bbdsConfig;
    }

    public ClientAccountResponse getTransactionalAccounts(Integer clientId) {
        log.info("Fetching transactional accounts for clientId: {}", clientId);
        var accounts = accountRepository.findByClientIdAndTransactionalOrderByDisplayBalanceDesc(clientId, true);
        log.debug("Retrieved {} transactional accounts for clientId: {}", accounts.size(), clientId);
        return getClientAccountResponse(accounts);
    }

    public ClientAccountResponse getCurrencyAccounts(Integer clientId) {
        log.info("Fetching currency accounts for clientId: {}", clientId);
        var accounts = accountRepository.findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(clientId, AccountTypeCode.CURRENCY_ACCOUNT.getCode());
        log.debug("Retrieved {} currency accounts for clientId: {}", accounts.size(), clientId);
        return getClientAccountResponse(accounts);
    }

    private ClientAccountResponse getClientAccountResponse(List<ClientAccount> accounts) {
        if (accounts.isEmpty()) {
            log.error("No accounts to display");
            throw new NoAccountsToDisplayException();
        }

        var client = accounts.stream()
                .findFirst()
                .map(ClientAccount::getClient)
                .orElseThrow(() -> {
                    log.error("Client information not found");
                    return new UserNotFoundException();
                });

        var clientDto = ClientMapper.INSTANCE.clientToClientDto(client);

        var accountDTOS = accounts.stream()
                .map(ClientAccountMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());

        log.debug("Successfully mapped client and accounts to DTOs");

        ClientAccountResponse response = ClientAccountResponse.builder()
                .client(clientDto)
                .accounts(accountDTOS)
                .result(getSuccessfulResult())
                .build();

        log.info("Generated ClientAccountResponse for clientId: {}", clientDto.getClientId());
        return response;
    }

    private Result getSuccessfulResult() {
        log.debug("Creating successful result response");
        return Result.builder()
                .statusCode(HttpStatus.OK.value())
                .statusReason(HttpStatus.OK.getReasonPhrase())
                .success(true)
                .build();
    }
}