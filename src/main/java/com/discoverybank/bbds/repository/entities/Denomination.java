package com.discoverybank.bbds.repository;

import jakarta.persistence.*;
import java.math.BigDecimal;

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