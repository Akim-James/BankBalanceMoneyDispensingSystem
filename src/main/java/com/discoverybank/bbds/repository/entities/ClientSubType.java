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
@Table(name = "CLIENT_SUB_TYPE")
public class ClientSubType {

    @Id
    @Column(name = "CLIENT_SUB_TYPE_CODE", length = 4)
    private String clientSubTypeCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_TYPE_CODE", referencedColumnName = "CLIENT_TYPE_CODE")
    private ClientType clientType;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @OneToMany(mappedBy = "clientSubType")
    private Set<Client> clients;

    
}
