package com.helium.oakcollectionsadmin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String customerFirstName;

    private String customerLastName;

    private String customerName;

    private String customerEmail;
    @NotNull(message = "CustomerCurrentPhoneNumber Can Not Be Null")
    private String customerCurrentPhoneNumber;

    private String customerNewPhoneNumber;

    private String customerAddress;
}
