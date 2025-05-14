package com.discoverybank.bbds.web;

import com.discoverybank.bbds.service.transactional.CashWithdrawalService;
import com.discoverybank.bbds.web.model.CashWithdrawalResponse;
import com.discoverybank.bbds.web.model.WithdrawalRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CashWithdrawalController {

    private final CashWithdrawalService cashWithdrawalService;

    public CashWithdrawalController(CashWithdrawalService cashWithdrawalService) {
        this.cashWithdrawalService = cashWithdrawalService;
    }

    @PostMapping("/withdraw")
    public CashWithdrawalResponse getCurrencyAccount(@RequestBody WithdrawalRequest request) {
        return cashWithdrawalService.withdrawFromAccount(request.getClientId(), request.getAccountNumber(), request.getAtmId(), request.getRequiredAmount());
    }
}
