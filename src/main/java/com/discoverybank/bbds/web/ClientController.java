package com.discoverybank.bbds.web;

import com.discoverybank.bbds.repository.entities.Client;
import com.discoverybank.bbds.service.transactional.user.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients/{clientId}")
    public Client getClientById(@PathVariable Integer clientId) { // TODO return DTO instead
        return clientService.retrieveClient(clientId);
    }
}
