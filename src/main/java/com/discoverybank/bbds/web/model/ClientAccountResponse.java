package com.discoverybank.bbds.dto;

import com.discoverybank.bbds.web.model.Result;
import lombok.Builder;

import java.util.List;


@Builder
public record ClientAccountResponse(
        ClientDto client,
        List<ClientAccountDTO> accounts,
        Result result) {
}
