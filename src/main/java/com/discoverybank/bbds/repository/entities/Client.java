package com.discoverybank.bbds.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private Integer clientId;

    @Column(name = "TITLE", length = 10)
    private String title;

    @Column(name = "NAME", length = 255, nullable = false)
    private String name;

    @Column(name = "SURNAME", length = 100)
    private String surname;

    @Column(name = "DOB", nullable = false)
    private LocalDate dob;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_SUB_TYPE_CODE", referencedColumnName = "CLIENT_SUB_TYPE_CODE")
    private ClientSubType clientSubType;

    @OneToMany(mappedBy = "client")
    private Set<ClientAccount> clientAccounts;

    
}