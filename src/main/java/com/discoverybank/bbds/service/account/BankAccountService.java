package com.discoverybank.bbds.service.account;

import com.discoverybank.bbds.config.BbdsConfig;
import com.discoverybank.bbds.dto.ClientAccountDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountService {
    private final BbdsConfig bankProperties;
//    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BbdsConfig bankProperties) {
        this.bankProperties = bankProperties;
//        this.bankAccountRepository = bankAccountRepository;
    }

    public List<ClientAccountDTO> retrieveBankAccounts(Integer clientId) {
//        var allByOrderByBalanceDesc = bankAccountRepository.findAllByOrderByBalanceDesc();


        return List.of(new ClientAccountDTO(123456L, "TYPE_CODE", "TRANSACTIONAL ACCOUNT", "ZAR", new BigDecimal("23"), new BigDecimal("23"), new BigDecimal("23"), new BigDecimal("31")));
    }
}
