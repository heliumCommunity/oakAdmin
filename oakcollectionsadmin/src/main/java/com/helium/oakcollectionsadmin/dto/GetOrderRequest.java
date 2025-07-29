package com.helium.oakcollectionsadmin.dto;

import com.helium.oakcollectionsadmin.enums.status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetOrderRequest {
    @NotNull(message = "status can not be null")
    private status status;
}
