package com.helium.oakcollectionsadmin.entity;

import com.helium.oakcollectionsadmin.enums.OrderFulfillmentMethod;
import com.helium.oakcollectionsadmin.enums.status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "order_tracking")
public class OrderTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //subject to automatic id generation
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "tracking_id")
    private String trackingId;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;

    @Column(name = "customer_address")
    private String customerAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_fulfillment_method")
    private OrderFulfillmentMethod orderFulfillmentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status;

    @Column(name = "progress")
    private String progress;

    @Column(name = "neck")
    private Double neck;

    @Column(name = "shoulder_width")
    private Double shoulderWidth;

    @Column(name = "chest")
    private Double chest;

    @Column(name = "waist")
    private Double waist;

    @Column(name = "hip")
    private Double hip;

    @Column(name = "sleeve_length")
    private Double sleeveLength;

    @Column(name = "inseam")
    private Double inseam;

    @Column(name = "outseam")
    private Double outseam;

    @Column(name = "thigh")
    private Double thigh;

    @Column(name = "wrist")
    private Double wrist;

    @Column(name = "rider_name",nullable = true)
    private String riderName;

    @Column(name = "rider_phone_number",nullable = true)
    private String riderPhoneNumber;




}
