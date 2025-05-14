package com.discoverybank.bbds.service.transactional.user;

import com.discoverybank.bbds.config.BankBalanceDispensingSystemProperties;
import com.discoverybank.bbds.exception.UserNotFoundException;
import com.discoverybank.bbds.model.Client;
import com.discoverybank.bbds.repository.user.User;
import com.discoverybank.bbds.repository.user.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final UserRepository userRepository;
    private final BankBalanceDispensingSystemProperties bankProperties;

    public ClientService(BankBalanceDispensingSystemProperties bankProperties, UserRepository userRepository) {
        this.bankProperties = bankProperties;
        this.userRepository = userRepository;
    }

    public Client retrieveClient(Long clientId) {
        Optional<User> userOptional = userRepository.findById(clientId);
        if(userOptional.isPresent()) {
            return new Client(clientId, "Mr", "John", "Dorian");
        }
       throw new UserNotFoundException(String.format("User with id %s not found", clientId));
    }
}
