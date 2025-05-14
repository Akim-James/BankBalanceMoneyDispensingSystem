package com.discoverybank.bbds.service.transactional;

import com.discoverybank.bbds.exception.WithdrawalException;
import com.discoverybank.bbds.repository.AtmRepository;
import com.discoverybank.bbds.repository.ClientAccountRepository;
import com.discoverybank.bbds.repository.entities.Atm;
import com.discoverybank.bbds.repository.entities.AtmAllocation;
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

import static org.junit.jupiter.api.Assertions.*;
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
    void testWithdrawalSuccess() {
        // When
        Integer clientId = 1;
        Long accountNumber = 123456789L;
        Integer atmId = 1;
        BigDecimal requiredAmount = new BigDecimal("100");

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setDisplayBalance(new BigDecimal("1000"));

        Denomination denomination = new Denomination();
        denomination.setDenominationId(1);
        denomination.setDenominationValue(new BigDecimal("50"));


        AtmAllocation atmAllocation = new AtmAllocation();
        atmAllocation.setDenomination(denomination);
        atmAllocation.setCount(10);

        Atm atm = new Atm();
        atm.setAtmAllocations(Set.of(atmAllocation));


        when(clientAccountRepository.findByClientIdAndAccountNumber(clientId, accountNumber))
                .thenReturn(clientAccount);

        when(atmRepository.findById(atmId)).thenReturn(Optional.of(atm));

        // Do
        CashWithdrawalResponse response = cashWithdrawalService.withdrawFromAccount(clientId, accountNumber, atmId, requiredAmount);

        // Assert
        assertNotNull(response);
        assertEquals(new BigDecimal("900"), clientAccount.getDisplayBalance());
        assertEquals(8, atmAllocation.getCount());

        verify(clientAccountRepository).save(clientAccount);
        verify(atmRepository).save(atm);
    }

    @Test
    void testInsufficientFundsInATM() {
        // When
        Integer clientId = 1;
        Long accountNumber = 123456789L;
        Integer atmId = 1;
        BigDecimal requiredAmount = new BigDecimal("250");

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setDisplayBalance(new BigDecimal("1000"));

        Denomination denomination = new Denomination();
        denomination.setDenominationId(1);
        denomination.setDenominationValue(new BigDecimal("50"));

        AtmAllocation atmAllocation = new AtmAllocation();
        atmAllocation.setDenomination(denomination);
        atmAllocation.setCount(4);

        Atm atm = new Atm();
        atm.setAtmAllocations(Set.of(atmAllocation));

        when(clientAccountRepository.findByClientIdAndAccountNumber(clientId, accountNumber))
                .thenReturn(clientAccount);

        when(atmRepository.findById(atmId)).thenReturn(Optional.of(atm));

        // Do
        WithdrawalException exception = assertThrows(WithdrawalException.class,
                () -> cashWithdrawalService.withdrawFromAccount(clientId, accountNumber, atmId, requiredAmount));

        assertEquals("Insufficient funds available. You may choose to withdraw " + new BigDecimal("200"), exception.getMessage());
    }

    @Test
    void testInvalidATM() {
        // When
        Integer clientId = 1;
        Long accountNumber = 123456789L;
        Integer atmId = 999;
        BigDecimal requiredAmount = new BigDecimal("100");

        when(clientAccountRepository.findByClientIdAndAccountNumber(clientId, accountNumber))
                .thenReturn(new ClientAccount());

        when(atmRepository.findById(atmId)).thenReturn(Optional.empty());

        // Do & Assert
        WithdrawalException exception = assertThrows(WithdrawalException.class,
                () -> cashWithdrawalService.withdrawFromAccount(clientId, accountNumber, atmId, requiredAmount));

        assertEquals("ATM not registered or unfunded", exception.getMessage());
    }
}