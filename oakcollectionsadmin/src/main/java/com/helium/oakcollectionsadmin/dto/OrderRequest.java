package com.helium.oakcollectionsadmin.dto;


import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class OrderRequest {

    private String trackingId;

    private Date dueDate;

    private String customerId;

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
    private String customMeasurement;
    private String priorityLevel;
    private String fittingRequired;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String clientType;

    private String additionalFitNotes;
    private String additionalNotes;
    private String orderType;
    private String productType;
    private String productColor;


}
