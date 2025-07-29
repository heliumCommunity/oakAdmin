package com.helium.oakcollectionsadmin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Customer_Table")

public class CustomerModule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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

    @Column(name = "order_count")
    private int orderCount = 1;

}
