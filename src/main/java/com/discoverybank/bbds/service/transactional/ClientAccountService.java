package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.exception.NoAccountsToDisplayException;
import com.discoverybank.bbds.exception.UserNotFoundException;
import com.discoverybank.bbds.mapper.ClientAccountMapper;
import com.discoverybank.bbds.mapper.ClientMapper;
import com.discoverybank.bbds.repository.AccountTypeCode;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.web.model.ClientAccountResponse;
import com.discoverybank.bbds.web.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing client account operations.
 * This class provides methods to fetch transactional and currency accounts for a client,
 * converting the retrieved data into a response format suitable for API consumers.
 * It interacts with the repository layer to access client account data and uses mappers
 * to transform entities into DTOs for responses.
 */
@Slf4j
@Service
public class ClientAccountService {

    private final ClientAccountRepository accountRepository;

    /**
     * Constructs a new instance of {@code ClientAccountService} with the specified dependencies.
     *
     * @param accountRepository the repository used to access client account data from the database
     */
    public ClientAccountService(ClientAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Retrieves transactional accounts for a specified client.
     * This method fetches accounts marked as transactional for the given client ID,
     * ordered by display balance in descending order, and returns them in a formatted response.
     *
     * @param clientId the unique identifier of the client whose transactional accounts are to be retrieved
     * @return a {@link ClientAccountResponse} containing the client's details, a list of transactional accounts,
     * and the result status of the operation
     * @throws NoAccountsToDisplayException if no transactional accounts are found for the client
     * @throws UserNotFoundException        if client information associated with the accounts is not found
     */
    public ClientAccountResponse getTransactionalAccounts(Integer clientId) {
        log.info("Fetching transactional accounts for clientId: {}", clientId);
        var accounts = accountRepository.findByClientIdAndTransactionalOrderByDisplayBalanceDesc(clientId, true);
        log.debug("Retrieved {} transactional accounts for clientId: {}", accounts.size(), clientId);
        return getClientAccountResponse(accounts);
    }

    /**
     * Retrieves currency accounts for a specified client.
     * This method fetches accounts of type currency for the given client ID,
     * ordered by display balance in ascending order, and returns them in a formatted response.
     *
     * @param clientId the unique identifier of the client whose currency accounts are to be retrieved
     * @return a {@link ClientAccountResponse} containing the client's details, a list of currency accounts,
     * and the result status of the operation
     * @throws NoAccountsToDisplayException if no currency accounts are found for the client
     * @throws UserNotFoundException        if client information associated with the accounts is not found
     */
    public ClientAccountResponse getCurrencyAccounts(Integer clientId) {
        log.info("Fetching currency accounts for clientId: {}", clientId);
        var accounts = accountRepository.findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(clientId, AccountTypeCode.CURRENCY_ACCOUNT.getCode());
        log.debug("Retrieved {} currency accounts for clientId: {}", accounts.size(), clientId);
        return getClientAccountResponse(accounts);
    }

    /**
     * Converts a list of client accounts into a structured response format.
     * This method maps the account entities to DTOs, extracts client information,
     * and builds a response object with the result status.
     *
     * @param accounts the list of {@link ClientAccount} entities to be converted into a response
     * @return a {@link ClientAccountResponse} containing the client details, mapped account DTOs,
     * and a success result status
     * @throws NoAccountsToDisplayException if the provided list of accounts is empty
     * @throws UserNotFoundException        if client information cannot be extracted from the accounts
     */
    private ClientAccountResponse getClientAccountResponse(List<ClientAccount> accounts) {
        if (accounts.isEmpty()) {
            log.error("No accounts to display");
            throw new NoAccountsToDisplayException();
        }

        var client = accounts.stream()
                .findFirst()
                .map(ClientAccount::getClient)
                .orElseThrow(() -> {
                    log.error("Client information not found");
                    return new UserNotFoundException();
                });

        var clientDto = ClientMapper.INSTANCE.clientToClientDto(client);

        var accountDTOS = accounts.stream()
                .map(ClientAccountMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());

        log.debug("Successfully mapped client and accounts to DTOs");

        ClientAccountResponse response = ClientAccountResponse.builder()
                .client(clientDto)
                .accounts(accountDTOS)
                .result(getSuccessfulResult())
                .build();

        log.info("Generated ClientAccountResponse for clientId: {}", clientDto.getClientId());
        return response;
    }

    /**
     * Creates a success result object for inclusion in API responses.
     * The result indicates a successful operation with an HTTP status code of 200 (OK).
     *
     * @return a {@link Result} object with success status, HTTP status code 200, and reason phrase "OK"
     */
    private Result getSuccessfulResult() {
        log.debug("Creating successful result response");
        return Result.builder()
                .statusCode(HttpStatus.OK.value())
                .statusReason(HttpStatus.OK.getReasonPhrase())
                .success(true)
                .build();
    }
}