package com.helium.oakcollectionsadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "status_tracker")
public class StatusTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_tracker_id")
    private long statusTrackerId;

    @OneToOne()
    @JoinColumn(name = "order_Id",referencedColumnName = "order_id")
    private OrderTracker orderId;

    @Column(name = "cutter_status")
    private String cutterStatus = "PENDING";

    @Column(name = "tailor_status")
    private String tailorStatus = "PENDING";

    @Column(name = "designer_status")
    private String designerStatus = "PENDING";

    @Column(name = "assembler_status")
    private String assemblerStatus = "PENDING";

    @Column(name = "embroiderer_status")
    private String embroidererStatus = "PENDING";

    @Column(name = "buttonholer_status")
    private String buttonholerStatus = "PENDING";

    @Column(name = "presser_status")
    private String presserStatus = "PENDING";

    @Column(name = "finisher_status")
    private String finisherStatus = "PENDING";

    @Column(name = "quality_checker_status")
    private String qualityCheckerStatus = "PENDING";

    @Column(name = "packer_status")
    private String packerStatus = "PENDING";

}
