package com.helium.oakcollectionsadmin.dto;

import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    private String orderId;
    private String cutterStatus;
    private String tailorStatus;
    private String designerStatus;
    private String assemblerStatus;
    private String embroidererStatus;
    private String buttonholerStatus;
    private String presserStatus;
    private String finisherStatus;
    private String qualityCheckerStatus;
    private String packerStatus;
}
