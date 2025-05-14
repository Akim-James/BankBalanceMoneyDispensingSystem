package com.discoverybank.bbds.repository;

import com.discoverybank.bbds.repository.entities.Atm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmRepository extends JpaRepository<Atm, Integer> {
}