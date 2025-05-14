package com.discoverybank.bbds.service.transactional.user;

import com.discoverybank.bbds.config.BbdsConfig;
import com.discoverybank.bbds.exception.UserNotFoundException;
import com.discoverybank.bbds.repository.ClientRepository;
import com.discoverybank.bbds.repository.entities.Client;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final BbdsConfig bankProperties;

    public ClientService(BbdsConfig bankProperties, ClientRepository clientRepository) {
        this.bankProperties = bankProperties;
        this.clientRepository = clientRepository;
    }

    public Client retrieveClient(Integer clientId) {
        Optional<Client> userOptional = clientRepository.findById(clientId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException(String.format("User with id %s not found", clientId));
    }
}
