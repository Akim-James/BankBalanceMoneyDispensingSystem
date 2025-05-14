package com.discoverybank.bbds.repository;
import com.discoverybank.bbds.repository.entities.ClientSubType;
import jakarta.persistence.*;
import java.util.Set;

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
