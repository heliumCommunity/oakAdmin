package com.helium.oakcollectionsadmin.dto;

import lombok.Data;

@Data
public class RoleAssigmentRequest {
    private String roleName;
    private String roleId;
    private String orderId;
    private String staffId;
}
