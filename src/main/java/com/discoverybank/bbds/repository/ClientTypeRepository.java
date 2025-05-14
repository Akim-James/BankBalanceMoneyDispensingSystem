package com.discoverybank.bbds.repository;

import com.discoverybank.bbds.repository.entities.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientTypeRepository extends JpaRepository<ClientType, String> {
}