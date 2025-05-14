package com.discoverybank.bbds.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "DENOMINATION_TYPE")
public class DenominationType {

    @Id
    @Column(name = "DENOMINATION_TYPE_CODE", length = 1)
    private String denominationTypeCode;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @OneToMany(mappedBy = "denominationType")
    private Set<Denomination> denominations;


}