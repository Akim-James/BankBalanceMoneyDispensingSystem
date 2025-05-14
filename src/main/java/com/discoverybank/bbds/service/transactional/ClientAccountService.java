package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.model.TransactionalBalance;
import com.discoverybank.bbds.service.account.BankAccountService;
import com.discoverybank.bbds.service.transactional.user.ClientService;
import com.discoverybank.bbds.web.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionalBalanceService {

    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    public TransactionalBalanceService(BankAccountService bankAccountService, ClientService clientService) {
        this.bankAccountService = bankAccountService;
        this.clientService = clientService;
    }

    public TransactionalBalance retrieveBalances(Long clientId) {
        var client = this.clientService.retrieveClient(clientId);

        var accounts = bankAccountService.retrieveBankAccounts(clientId);
        var result = Result.builder()
                .statusCode(HttpStatus.OK.value())
                .statusReason(HttpStatus.OK.getReasonPhrase())
                .success(true).build();


        return TransactionalBalance.builder()
                .accounts(accounts)
                .client(client)
                .result(result)
                .build();
    }

    public TransactionalBalance retrieveCurrencyAccounts(Long clientId) {
        return null;
    }
}
