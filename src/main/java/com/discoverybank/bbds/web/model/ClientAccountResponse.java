package com.discoverybank.bbds.web.model;

import com.discoverybank.bbds.dto.ClientAccountDTO;
import com.discoverybank.bbds.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccountResponse {
    private ClientDto client;
    private List<ClientAccountDTO> accounts;
    private Result result;
}
