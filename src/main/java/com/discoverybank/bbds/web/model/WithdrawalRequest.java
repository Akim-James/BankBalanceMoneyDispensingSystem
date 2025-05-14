package com.discoverybank.bbds.web.model;

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
public class WithdrawalRequest {

    private Integer clientId;
    private Integer atmId;
    private Long accountNumber;
    private BigDecimal requiredAmount;
}
