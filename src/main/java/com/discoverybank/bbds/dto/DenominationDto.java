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
public class DenominationDto {

    private Long denominationId;
    private BigDecimal denominationValue;
    private Integer count;

}
