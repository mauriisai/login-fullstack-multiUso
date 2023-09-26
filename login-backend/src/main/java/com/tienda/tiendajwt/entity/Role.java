package com.tienda.tiendajwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Role {

    @Id
    private String roleName;

    private String roleDescription;
}
