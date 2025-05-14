package com.discoverybank.bbds;

import com.discoverybank.bbds.repository.entities.Atm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmRepository extends JpaRepository<Atm, Integer> {
}