package com.discoverybank.bbds.mapper;

import com.discoverybank.bbds.dto.CurrencyConversionRateDTO;
import com.discoverybank.bbds.dto.CurrencyDTO;
import com.discoverybank.bbds.repository.entities.Currency;
import com.discoverybank.bbds.repository.entities.CurrencyConversionRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDTO toDTO(Currency currency);

    @Mapping(source = "currency.currencyCode", target = "currencyCode")
    CurrencyConversionRateDTO toConversionRateDTO(CurrencyConversionRate currencyConversionRate);
}