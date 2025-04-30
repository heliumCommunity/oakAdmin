package com.helium.oakcollectionsadmin.entity;

import com.helium.oakcollectionsadmin.enums.Roles;
import com.helium.oakcollectionsadmin.enums.isActive;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Audited
@Data
@Table(name = "userinfo")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    //Going to generate a unique Id for each staff with a generation Logic
    private Long id;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password; // should be hashed before saving

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "role")
    private Roles role;
    @Column(name = "jobTitle")
    private String designation; // e.g., "Creative Director", "Studio Manager"

    @Column(name = "activationStatus")
    private isActive activationStatus;

    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
