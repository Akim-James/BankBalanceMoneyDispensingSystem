package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.repository.AccountTypeCode;
import com.discoverybank.bbds.repository.AtmRepository;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.AccountType;
import com.discoverybank.bbds.repository.entities.Atm;
import com.discoverybank.bbds.repository.entities.AtmAllocation;
import com.discoverybank.bbds.repository.entities.Client;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.repository.entities.Denomination;
import com.discoverybank.bbds.web.model.CashWithdrawalResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CashWithdrawalServiceTest {

    @Mock
    private AtmRepository atmRepository;

    @Mock
    private ClientAccountRepository clientAccountRepository;

    @InjectMocks
    private CashWithdrawalService cashWithdrawalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithdrawalSuccessForChequeAccountWithNoOverdraft() {
        // Setup
        Integer clientId = 1;
        Long accountNumber = 123456789L;
        Integer atmId = 1;
        BigDecimal requiredAmount = new BigDecimal("100");

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setDisplayBalance(new BigDecimal("1000"));

        AccountType accountType = new AccountType();
        accountType.setAccountTypeCode(AccountTypeCode.CHEQUE.getCode());
        clientAccount.setAccountType(accountType);

        Client client = new Client();
        client.setClientId(clientId);
        clientAccount.setClient(client);

        Denomination denomination = new Denomination();
        denomination.setDenominationId(1);
        denomination.setDenominationValue(new BigDecimal("50"));

        AtmAllocation atmAllocation = new AtmAllocation();
        atmAllocation.setDenomination(denomination);
        atmAllocation.setCount(10);

        Atm atm = new Atm();
        atm.setAtmAllocations(Set.of(atmAllocation));

        // When
        when(clientAccountRepository.findByClientIdAndAccountNumber(clientId, accountNumber)).thenReturn(clientAccount);
        when(atmRepository.findById(atmId)).thenReturn(Optional.of(atm));

        // Execution
        CashWithdrawalResponse response = cashWithdrawalService.withdrawFromAccount(clientId, accountNumber, atmId, requiredAmount);

        // Verification
        assertNotNull(response);
        assertEquals(new BigDecimal("900"), clientAccount.getDisplayBalance());
        assertEquals(8, atmAllocation.getCount());

        verify(clientAccountRepository).save(clientAccount);
        verify(atmRepository).save(atm);
    }

    @Test
    void testOverdraftAllowedOnChequeAccount() {
        // Setup a cheque account with a balance less than required, but within overdraft limit.
        Integer clientId = 1;
        Long accountNumber = 123456789L;
        Integer atmId = 1;
        BigDecimal requiredAmount = new BigDecimal("10500"); // allowed overdraft

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setDisplayBalance(new BigDecimal("500")); // Resulting in a -10,000 balance

        AccountType accountType = new AccountType();
        accountType.setAccountTypeCode(AccountTypeCode.CHEQUE.getCode());
        clientAccount.setAccountType(accountType);

        Client client = new Client();
        client.setClientId(clientId);
        clientAccount.setClient(client);

        Denomination denomination = new Denomination();
        denomination.setDenominationId(1);
        denomination.setDenominationValue(new BigDecimal("50"));

        AtmAllocation atmAllocation = new AtmAllocation();
        atmAllocation.setDenomination(denomination);
        atmAllocation.setCount(210);

        Atm atm = new Atm();
        atm.setAtmAllocations(Set.of(atmAllocation));

        when(clientAccountRepository.findByClientIdAndAccountNumber(clientId, accountNumber)).thenReturn(clientAccount);
        when(atmRepository.findById(atmId)).thenReturn(Optional.of(atm));

        // Execution
        CashWithdrawalResponse response = cashWithdrawalService.withdrawFromAccount(clientId, accountNumber, atmId, requiredAmount);

        // Verification
        assertNotNull(response);
        assertEquals(new BigDecimal("-10000"), clientAccount.getDisplayBalance());
        assertEquals(0, atmAllocation.getCount());

        verify(clientAccountRepository).save(clientAccount);
        verify(atmRepository).save(atm);
    }
}