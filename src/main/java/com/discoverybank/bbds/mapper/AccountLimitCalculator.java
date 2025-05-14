package com.discoverybank.bbds.mapper;

import com.discoverybank.bbds.repository.entities.CurrencyConversionRate;
import org.mapstruct.Named;

import java.math.BigDecimal;
public class AccountLimitCalculator {

    @Named("calculateAccountLimit")
    public BigDecimal calculateAccountLimit(String accountTypeCode) {
        if ("CHQ".equals(accountTypeCode)) {
            return BigDecimal.valueOf(10000);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getConversionRate(CurrencyConversionRate currencyConversionRate) {
        return currencyConversionRate != null ? currencyConversionRate.getRate() : BigDecimal.ONE;
    }
}