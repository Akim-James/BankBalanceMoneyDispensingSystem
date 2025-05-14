package com.discoverybank.bbds.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CREDIT_CARD_LIMIT")
public class CreditCardLimit {

    @Id
    @Column(name = "CLIENT_ACCOUNT_NUMBER", length = 10)
    private String clientAccountNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "CLIENT_ACCOUNT_NUMBER", referencedColumnName = "CLIENT_ACCOUNT_NUMBER")
    private ClientAccount clientAccount;

    @Column(name = "ACCOUNT_LIMIT", nullable = false, precision = 18, scale = 3)
    private BigDecimal accountLimit;

}