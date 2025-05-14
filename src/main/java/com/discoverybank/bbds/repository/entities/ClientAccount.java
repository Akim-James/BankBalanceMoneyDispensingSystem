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
@Table(name = "CLIENT_ACCOUNT")
public class ClientAccount {

    @Id
    @Column(name = "CLIENT_ACCOUNT_NUMBER", length = 10)
    private String clientAccountNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ACCOUNT_TYPE_CODE", referencedColumnName = "ACCOUNT_TYPE_CODE")
    private AccountType accountType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
    private Currency currency;

    @Column(name = "DISPLAY_BALANCE", precision = 18, scale = 3)
    private BigDecimal displayBalance;

    @OneToOne(mappedBy = "clientAccount", cascade = CascadeType.ALL, optional = true)
    private CreditCardLimit creditCardLimit;

    public ClientAccount(Client client, BigDecimal displayBalance) {
        this.client = client;
        this.displayBalance = displayBalance;
    }
}