package com.discoverybank.bbds.web;

import com.discoverybank.bbds.service.transactional.ClientAccountService;
import com.discoverybank.bbds.web.model.ClientAccountResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

    private final ClientAccountService clientAccountService;

    public BankAccountController(ClientAccountService clientAccountService) {
        this.clientAccountService = clientAccountService;
    }

    @GetMapping("/queryCcyBalances/{clientId}")
    public ClientAccountResponse getCurrencyAccount(@PathVariable Integer clientId) {
        return clientAccountService.getCurrencyAccounts(clientId);
    }

    @GetMapping("/queryTransactionalBalances/{clientId}")
    public ClientAccountResponse getTransactionalBalances(@PathVariable Integer clientId) {
        return this.clientAccountService.getTransactionalAccounts(clientId);
    }
}
