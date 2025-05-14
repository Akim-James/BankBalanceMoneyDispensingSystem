package com.discoverybank.bbds.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ATM")
public class Atm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATM_ID", unique = true, nullable = false)
    private Integer atmId;

    @Column(name = "NAME", length = 10, unique = true, nullable = false)
    private String name;

    @Column(name = "LOCATION", length = 255, nullable = false)
    private String location;

    @OneToMany(mappedBy = "atm", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AtmAllocation> atmAllocations;
}


