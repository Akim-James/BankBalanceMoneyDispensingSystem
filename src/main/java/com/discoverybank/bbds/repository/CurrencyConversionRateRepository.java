package com.discoverybank.bbds.repository;
import com.discoverybank.bbds.repository.entities.CurrencyConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyConversionRateRepository extends JpaRepository<CurrencyConversionRate, String> {
}
