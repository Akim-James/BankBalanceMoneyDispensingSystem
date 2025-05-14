package com.discoverybank.bbds.service;

import com.discoverybank.bbds.config.BankBalanceDispensingSystemProperties;
import com.discoverybank.bbds.model.AccountType;
import com.discoverybank.bbds.model.BankAccount;
import com.discoverybank.bbds.model.ChequeAccount;
import com.discoverybank.bbds.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    private final BankBalanceDispensingSystemProperties bankProperties;

    public BankAccountService(BankBalanceDispensingSystemProperties bankProperties) {
        this.bankProperties = bankProperties;
    }

    public List<BankAccount> retrieveBankAccounts(Long clientId) {
        return List.of(new ChequeAccount( "12346654" , bankProperties.getOverdraftLimit(),  13235));
    }
}
