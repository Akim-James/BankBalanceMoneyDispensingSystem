package com.discoverybank.bbds.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ATM_ALLOCATION")
public class AtmAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATM_ALLOCATION_ID", unique = true, nullable = false)
    private Integer atmAllocationId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ATM_ID", referencedColumnName = "ATM_ID", nullable = false)
    private Atm atm;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "DENOMINATION_ID", referencedColumnName = "DENOMINATION_ID", nullable = false)
    private Denomination denomination;

    @Column(name = "COUNT", nullable = false)
    private Integer count;
}