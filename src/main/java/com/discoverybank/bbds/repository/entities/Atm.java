package com.discoverybank.bbds.repository;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ATM")
public class Atm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATM_ID")
    private Integer atmId;

}


