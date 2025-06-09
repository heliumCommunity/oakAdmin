package com.helium.oakcollectionsadmin.dto;

import lombok.Data;

@Data
public class OrderAssignmentRequest {
    private String orderId;
    private String cutter;
    private String tailor;
    private String designer;
    private String assembler;
    private String embroiderer;
    private String buttonholer;
    private String presser;
    private String finisher;
    private String qualityChecker;
    private String packer;
}
