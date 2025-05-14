package com.discoverybank.bbds.repository;

import com.discoverybank.bbds.repository.entities.CreditCardLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardLimitRepository extends JpaRepository<CreditCardLimit, String> {
}