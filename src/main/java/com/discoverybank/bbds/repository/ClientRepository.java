package com.discoverybank.bbds.repository;
import com.discoverybank.bbds.repository.entities.Client;
import com.discoverybank.bbds.repository.entities.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {


    @Query("SELECT ca FROM ClientAccount ca WHERE ca.client.clientId = :clientId AND ca.accountType.transactional = :transactional")
    List<ClientAccount> findByClientIdAndTransactional(@Param("clientId") Integer clientId, @Param("transactional") boolean transactional);


}
