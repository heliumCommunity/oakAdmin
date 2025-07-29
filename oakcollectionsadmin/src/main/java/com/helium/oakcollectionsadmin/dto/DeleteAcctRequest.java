package com.helium.oakcollectionsadmin.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteAcctRequest {
    @NotNull(message = "email can not be null")
    private String email;
}
