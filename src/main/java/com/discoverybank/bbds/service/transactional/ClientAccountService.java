package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.config.BbdsConfig;
import com.discoverybank.bbds.web.model.ClientAccountResponse;
import com.discoverybank.bbds.exception.NoAccountsToDisplayException;
import com.discoverybank.bbds.exception.UserNotFoundException;
import com.discoverybank.bbds.mapper.ClientAccountMapper;
import com.discoverybank.bbds.mapper.ClientMapper;
import com.discoverybank.bbds.repository.AccountTypeCode;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.web.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientAccountService {

    private final BbdsConfig bbdsConfig;
    private final ClientAccountRepository accountRepository;

    public ClientAccountService(ClientAccountRepository accountRepository, BbdsConfig bbdsConfig) {
        this.accountRepository = accountRepository;
        this.bbdsConfig = bbdsConfig;
    }

    public ClientAccountResponse getTransactionalAccounts(Integer clientId) {
        var accounts = accountRepository.findByClientIdAndTransactionalOrderByDisplayBalanceDesc(clientId, true);

        return getClientAccountResponse(accounts);

    }

    public ClientAccountResponse getCurrencyAccounts(Integer clientId) {
        var accounts = accountRepository.findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(clientId, AccountTypeCode.CURRENCY_ACCOUNT.getCode());
        return getClientAccountResponse(accounts);
    }

    private ClientAccountResponse getClientAccountResponse(List<ClientAccount> accounts) {
        if (accounts.isEmpty()) {
            throw new NoAccountsToDisplayException();
        }

        var client = accounts.stream()
                .findFirst()
                .map(ClientAccount::getClient)
                .orElseThrow(UserNotFoundException::new);

        var clientDto = ClientMapper.INSTANCE.clientToClientDto(client);

        var accountDTOS = accounts.stream()
                .map(ClientAccountMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());

        return ClientAccountResponse.builder()
                .client(clientDto)
                .accounts(accountDTOS)
                .result(getSuccessfulResult())
                .build();
    }

    private Result getSuccessfulResult() {
        return Result.builder()
                .statusCode(HttpStatus.OK.value())
                .statusReason(HttpStatus.OK.getReasonPhrase())
                .success(true)
                .build();
    }
}
