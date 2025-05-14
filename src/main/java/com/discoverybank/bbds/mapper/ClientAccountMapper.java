package com.discoverybank.bbds.mapper;

import com.discoverybank.bbds.dto.ClientAccountDTO;
import com.discoverybank.bbds.repository.AccountTypeCode;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.repository.entities.CurrencyConversionRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface ClientAccountMapper {

    ClientAccountMapper INSTANCE = Mappers.getMapper(ClientAccountMapper.class);

    @Mapping(source = "clientAccount.clientAccountNumber", target = "accountNumber")
    @Mapping(source = "clientAccount.accountType.accountTypeCode", target = "typeCode")
    @Mapping(source = "clientAccount.accountType.description", target = "accountTypeDescription")
    @Mapping(source = "clientAccount.currency.currencyCode", target = "currencyCode")
    @Mapping(target = "conversionRate", source = "clientAccount.currency.currencyConversionRate.rate")
    @Mapping(source = "clientAccount.displayBalance", target = "balance")
    @Mapping(source = "clientAccount", target = "zarBalance", qualifiedByName = "convertToZAR")
    @Mapping(target = "accountLimit", source = "clientAccount.accountType.accountTypeCode", qualifiedByName = "calculateAccountLimit")
    ClientAccountDTO toDTO(ClientAccount clientAccount);

    @Named("calculateAccountLimit")
    default BigDecimal calculateAccountLimit(String accountTypeCode) {
        if (AccountTypeCode.CHEQUE.getCode().equals(accountTypeCode)) {
            return BigDecimal.valueOf(10000);
        }
        return BigDecimal.ZERO;
    }

    @Named("convertToZAR")
    default BigDecimal convertToZAR(ClientAccount clientAccount) {
        if (clientAccount == null || clientAccount.getCurrency() == null) {
            return clientAccount.getDisplayBalance().setScale(2, RoundingMode.HALF_UP);
        }

        CurrencyConversionRate conversionRate = clientAccount.getCurrency().getCurrencyConversionRate();
        BigDecimal rate = (conversionRate != null && conversionRate.getRate() != null) ? conversionRate.getRate() : BigDecimal.ONE;

        BigDecimal zarBalance = clientAccount.getDisplayBalance().multiply(rate);
        return zarBalance.setScale(2, RoundingMode.HALF_UP);
    }
}