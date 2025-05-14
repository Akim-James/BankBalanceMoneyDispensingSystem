package com.discoverybank.bbds.repository.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CURRENCY")
public class Currency {

    @Id
    @Column(name = "CURRENCY_CODE", length = 3)
    private String currencyCode;

    @Column(name = "DECIMAL_PLACES", nullable = false)
    private Integer decimalPlaces;

    @Column(name = "DESCRIPTION", length = 255, nullable = false)
    private String description;

    @OneToMany(mappedBy = "currency")
    private Set<ClientAccount> clientAccounts;

    @OneToOne(mappedBy = "currency")
    private CurrencyConversionRate currencyConversionRate;

    
}
