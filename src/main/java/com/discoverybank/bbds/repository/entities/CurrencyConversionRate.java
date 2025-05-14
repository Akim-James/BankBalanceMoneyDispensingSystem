package com.discoverybank.bbds.repository;
import com.discoverybank.bbds.repository.entities.Currency;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CURRENCY_CONVERSION_RATE")
public class CurrencyConversionRate {

    @Id
    @Column(name = "CURRENCY_CODE", length = 3)
    private String currencyCode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
    private Currency currency;

    @Column(name = "CONVERSION_INDICATOR", nullable = false, length = 1)
    private String conversionIndicator;

    @Column(name = "RATE", nullable = false, precision = 18, scale = 8)
    private BigDecimal rate;

}