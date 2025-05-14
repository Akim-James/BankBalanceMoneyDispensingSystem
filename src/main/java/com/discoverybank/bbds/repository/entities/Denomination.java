package com.discoverybank.bbds.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "DENOMINATION")
public class Denomination {

    @Id
    @Column(name = "DENOMINATION_ID")
    private Integer denominationId;

    @Column(name = "DENOMINATION_VALUE", nullable = false, precision = 18, scale = 2)
    private BigDecimal denominationValue;

    @ManyToOne
    @JoinColumn(name = "DENOMINATION_TYPE_CODE", referencedColumnName = "DENOMINATION_TYPE_CODE")
    private DenominationType denominationType;

}