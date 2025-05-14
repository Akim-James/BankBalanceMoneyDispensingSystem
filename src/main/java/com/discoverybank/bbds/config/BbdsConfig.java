package com.discoverybank.bbds.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Component
@ConfigurationProperties(prefix = "bank")
public class BankBalanceDispensingSystemProperties {

    @Value("${cheque.overdraft-limit}")
    private BigDecimal chequeOverdraftLimit;

}
