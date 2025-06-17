package com.helium.oakcollectionsadmin.dto;

import lombok.Data;

@Data
public class RoleAssignmentRequest {
    private String roleName;
    private String roleId;
    private String orderId;
    private String staffId;
}
