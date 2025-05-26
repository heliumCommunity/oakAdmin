package com.helium.oakcollectionsadmin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateRequest {
    private Date dueDate;
    private String customerFirstName;

    private String customerLastName;

    private String customerName;

    private String customerEmail;

    private String customerPhoneNumber;

    private String customerAddress;


    private String orderFulfillmentMethod;


    private String status;

    private String progress;

    private Double neck;

    private Double shoulderWidth;

    private Double chest;

    private Double waist;

    private Double hip;

    private Double sleeveLength;

    private Double inseam;

    private Double outSeam;

    private Double thigh;

    private Double wrist;

    private String riderName;

    private String riderNumber;

}
