package com.discoverybank.bbds.repository;

import com.discoverybank.bbds.repository.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
