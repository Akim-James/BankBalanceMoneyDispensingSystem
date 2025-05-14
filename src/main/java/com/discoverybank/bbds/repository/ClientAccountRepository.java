package com.discoverybank.bbds.repository;

import com.discoverybank.bbds.repository.entities.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {
    @Query("SELECT ca FROM ClientAccount ca WHERE ca.client.clientId = :clientId AND ca.accountType.transactional = :transactional ORDER BY ca.displayBalance DESC")
    List<ClientAccount> findByClientIdAndTransactionalOrderByDisplayBalanceDesc(@Param("clientId") Integer clientId, @Param("transactional") boolean transactional);

    @Query("SELECT ca FROM ClientAccount ca WHERE ca.client.clientId = :clientId AND ca.accountType.accountTypeCode = :accountTypeCode ORDER BY ca.displayBalance ASC")
    List<ClientAccount> findByClientIdAndAccountTypeCodeOrderByDisplayBalanceAsc(@Param("clientId") Integer clientId, @Param("accountTypeCode") String accountTypeCode);

    @Query("SELECT ca FROM ClientAccount ca WHERE ca.client.clientId = :clientId AND ca.clientAccountNumber = :accountNumber")
    ClientAccount findByClientIdAndAccountNumber(Integer clientId, Long accountNumber);
}
