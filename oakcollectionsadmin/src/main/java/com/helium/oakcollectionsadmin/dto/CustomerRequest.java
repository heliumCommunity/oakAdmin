package com.helium.oakcollectionsadmin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotNull(message = "customerFirstName Can Not Be Null")
    private String customerFirstName;

    @NotNull(message = "customerLastName Can Not Be Null")
    private String customerLastName;

    @NotNull(message = "customerName Can Not Be Null")
    private String customerName;

    @NotNull(message = "customerEmail Can Not Be Null")
    private String customerEmail;

    @NotNull(message = "customerPhoneNumber Can Not Be Null")
    private String customerPhoneNumber;

    @NotNull(message = "customerAddress Can Not Be Null")

    private String customerAddress;

}
