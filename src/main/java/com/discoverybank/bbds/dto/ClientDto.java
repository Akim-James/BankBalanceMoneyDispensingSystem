package com.discoverybank.bbds.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto implements Serializable {

    private Integer clientId;

    private String title;

    private String name;

    private String surname;

}
