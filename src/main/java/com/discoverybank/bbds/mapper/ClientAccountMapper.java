package com.discoverybank.bbds.mapper;

import com.discoverybank.bbds.dto.ClientAccountDTO;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientAccountMapper {

    ClientAccountMapper INSTANCE = Mappers.getMapper(ClientAccountMapper.class);

    @Mapping(source = "accountType.description", target = "accountTypeDescription")
    @Mapping(source = "currency.currencyCode", target = "currencyCode")
    ClientAccountDTO clientAccountToClientAccountDTO(ClientAccount clientAccount);
}