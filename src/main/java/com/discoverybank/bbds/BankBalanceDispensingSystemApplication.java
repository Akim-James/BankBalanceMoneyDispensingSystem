package com.discoverybank.bbds;

import com.discoverybank.bbds.model.AccountType;import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankBalanceDispensingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountType.BankBalanceDispensingSystemApplication.class, args);
    }

}
