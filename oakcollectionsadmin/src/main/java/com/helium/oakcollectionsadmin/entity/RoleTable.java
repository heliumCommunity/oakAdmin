package com.helium.oakcollectionsadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role_table")
public class RoleTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String roleId;
    private String roleName;
    private String OrderId;

}
