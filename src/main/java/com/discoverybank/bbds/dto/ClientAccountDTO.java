package com.discoverybank.bbds.dto;


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
public class ClientAccountDTO {

    private Long accountNumber;
    private String typeCode;
    private String accountTypeDescription;
    private String currencyCode;
    private BigDecimal conversionRate;
    private BigDecimal balance;
    private BigDecimal zarBalance;
    private BigDecimal accountLimit;


}

