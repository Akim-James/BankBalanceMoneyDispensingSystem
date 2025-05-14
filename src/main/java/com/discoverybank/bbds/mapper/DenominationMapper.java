package com.discoverybank.bbds.mapper;

import com.discoverybank.bbds.dto.DenominationDto;
import com.discoverybank.bbds.repository.entities.AtmAllocation;
import com.discoverybank.bbds.repository.entities.Denomination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DenominationMapper {
    DenominationMapper INSTANCE = Mappers.getMapper(DenominationMapper.class);

    @Mapping(source = "denomination.denominationId", target = "denominationId")
    @Mapping(source = "denomination.denominationValue", target = "denominationValue")
    @Mapping(source = "atmAllocation.count", target = "count")
    DenominationDto denominationToDenominationDto(Denomination denomination, AtmAllocation atmAllocation);

    // Assuming the reverse mapping doesn't explicitly involve AtmAllocation
    @Mapping(source = "denominationId", target = "denominationId")
    @Mapping(source = "denominationValue", target = "denominationValue")
    @Mapping(target = "denominationType", ignore = true)
    Denomination denominationDtoToDenomination(DenominationDto denominationDto);
}