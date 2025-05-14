package com.discoverybank.bbds.web.model;

import com.discoverybank.bbds.dto.ClientAccountDTO;
import com.discoverybank.bbds.dto.ClientDto;
import com.discoverybank.bbds.dto.DenominationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashWithdrawalResponse {

    private ClientDto client;
    private ClientAccountDTO account;
    private List<DenominationDto> denominations;
    private Result result;
}
