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
@Table(name = "ACCOUNT_TYPE")
public class AccountType {

    @Id
    @Column(name = "ACCOUNT_TYPE_CODE", length = 10)
    private String accountTypeCode;

    @Column(name = "DESCRIPTION", length = 50, nullable = false)
    private String description;

    @Column(name = "TRANSACTIONAL")
    private Boolean transactional;

    @OneToMany(mappedBy = "accountType")
    private Set<ClientAccount> clientAccounts;

    
}