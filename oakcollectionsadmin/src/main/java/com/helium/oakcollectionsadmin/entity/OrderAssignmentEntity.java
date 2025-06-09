package com.helium.oakcollectionsadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_assignment_table")
public class OrderAssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "order_id",referencedColumnName = "order_id")
    private OrderTracker orderId;

    @Column(name = "cutter")
    private String cutter;

    @Column(name = "tailor")
    private String tailor;

    @Column(name = "designer")
    private String designer;

    @Column(name = "assembler")
    private String assembler;

    @Column(name = "embroiderer")
    private String embroiderer;

    @Column(name = "buttonholer")
    private String buttonholer;

    @Column(name = "presser")
    private String presser;

    @Column(name = "finisher")
    private String finisher;

    @Column(name = "quality_checker")
    private String qualityChecker;

    @Column(name = "packer")
    private String packer;

    @OneToOne()
    @JoinColumn(name = "status_id",referencedColumnName = "status_tracker_id")
    private StatusTracker statusTracker ;

}
