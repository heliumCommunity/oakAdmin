package com.helium.oakcollectionsadmin.entity;

import com.helium.oakcollectionsadmin.enums.JobTitle;
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
    @Enumerated(EnumType.STRING)
    private JobTitle roleName;
    private String OrderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id",referencedColumnName = "staff_id")
    private UserInfo staffId;

}
