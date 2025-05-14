package com.discoverybank.bbds.repository;

import lombok.Getter;

@Getter
public enum AccountTypeCode {
    CHEQUE("CHQ"),
    SAVINGS("SVGS"),
    PERSONAL_LOAN("PLOAN"),
    HOME_LOAN("HLOAN"),
    CREDIT_CARD("CCRD"),
    CURRENCY_ACCOUNT("CFCA");

    private final String code;

    AccountTypeCode(String code) {
        this.code = code;
    }
}
