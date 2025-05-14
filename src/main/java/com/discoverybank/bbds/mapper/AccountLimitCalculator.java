package com.discoverybank.bbds.mapper;

import org.mapstruct.Named;

import java.math.BigDecimal;

public class AccountLimitUtil {

    @Named("calculateAccountLimit")
    public static BigDecimal calculateAccountLimit(String accountTypeCode, BigDecimal chequeLimit) {
        return "CHQ".equals(accountTypeCode) ? chequeLimit : BigDecimal.ZERO;
    }
}