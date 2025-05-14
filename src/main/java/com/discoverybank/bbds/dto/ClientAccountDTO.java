package com.discoverybank.bbds.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private Long accountNumber;
    private String typeCode;
    private String accountTypeDescription;
    private String currencyCode;
    private BigDecimal conversionRate;
    private BigDecimal balance;
    private BigDecimal zarBalance;
    private BigDecimal accountLimit;
}