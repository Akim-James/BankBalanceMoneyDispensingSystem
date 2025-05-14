package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.config.BbdsConfig;
import com.discoverybank.bbds.exception.NoAccountsToDisplayException;
import com.discoverybank.bbds.repository.AccountTypeCode;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.Client;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.web.model.ClientAccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientAccountServiceTest {

    @Mock
    private ClientAccountRepository accountRepository;

    @Mock
    private BbdsConfig bbdsConfig;

    @InjectMocks
    private ClientAccountService clientAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTransactionalAccounts_Success() {
        // When
        Integer clientId = 1;
        Client client = new Client();  // Create a client instance
        ClientAccount account1 = new ClientAccount(client, new BigDecimal("1000"));
        ClientAccount account2 = new ClientAccount(client, new BigDecimal("500"));

        when(accountRepository.findByClientIdAndTransactionalOrderByDisplayBalanceDesc(clientId, true))
                .thenReturn(List.of(account1, account2));

        // Do
        ClientAccountResponse response = clientAccountService.getTransactionalAccounts(clientId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getAccounts().size());

        verify(accountRepository, times(1)).findByClientIdAndTransactionalOrderByDisplayBalanceDesc(clientId, true);

    }

    @Test
    void testGetTransactionalAccounts_NoAccounts() {
        // When
        Integer clientId = 1;
        when(accountRepository.findByClientIdAndTransactionalOrderByDisplayBalanceDesc(clientId, true))
                .thenReturn(List.of());

        // Do & Assert
        assertThrows(NoAccountsToDisplayException.class,
                () -> clientAccountService.getTransactionalAccounts(clientId));

        verify(accountRepository, times(1)).findByClientIdAndTransactionalOrderByDisplayBalanceDesc(clientId, true);

    }

    @Test
    void testGetCurrencyAccounts_Success() {
        // When
        Integer clientId = 1;
        Client client = new Client();  // Create a client instance
        ClientAccount account1 = new ClientAccount(client, new BigDecimal("2000"));
        ClientAccount account2 = new ClientAccount(client, new BigDecimal("1500"));

        when(accountRepository.findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(clientId, AccountTypeCode.CURRENCY_ACCOUNT.getCode()))
                .thenReturn(List.of(account1, account2));

        // Do
        ClientAccountResponse response = clientAccountService.getCurrencyAccounts(clientId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getAccounts().size());

        verify(accountRepository, times(1)).findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(clientId, AccountTypeCode.CURRENCY_ACCOUNT.getCode());

    }

    @Test
    void testGetCurrencyAccounts_NoAccounts() {
        // When
        Integer clientId = 1;
        when(accountRepository.findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(clientId, AccountTypeCode.CURRENCY_ACCOUNT.getCode()))
                .thenReturn(List.of());

        // Do & Assert
        assertThrows(NoAccountsToDisplayException.class,
                () -> clientAccountService.getCurrencyAccounts(clientId));

        verify(accountRepository, times(1)).findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(clientId, AccountTypeCode.CURRENCY_ACCOUNT.getCode());
    }
}