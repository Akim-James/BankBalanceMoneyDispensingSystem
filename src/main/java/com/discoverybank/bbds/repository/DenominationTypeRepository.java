package com.discoverybank.bbds.repository;

import com.discoverybank.bbds.repository.entities.ClientAccount;
import com.discoverybank.bbds.repository.entities.DenominationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenominationTypeRepository extends JpaRepository<DenominationType, String> {

}