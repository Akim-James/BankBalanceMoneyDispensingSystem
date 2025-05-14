package com.discoverybank.bbds.repository;

import com.discoverybank.bbds.repository.entities.Denomination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenominationRepository extends JpaRepository<Denomination, Integer> {
}