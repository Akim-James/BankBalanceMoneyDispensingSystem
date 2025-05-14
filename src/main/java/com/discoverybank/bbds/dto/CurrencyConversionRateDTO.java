package com.discoverybank.bbds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversionRateDTO {
    private String currencyCode;
    private String conversionIndicator;
    private BigDecimal rate;
}