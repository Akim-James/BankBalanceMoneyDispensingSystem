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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENT_TYPE")
public class ClientType {

    @Id
    @Column(name = "CLIENT_TYPE_CODE", length = 2)
    private String clientTypeCode;

    @Column(name = "DESCRIPTION", length = 255, nullable = false)
    private String description;

    @OneToMany(mappedBy = "clientType")
    private Set<ClientSubType> clientSubTypes;

}
