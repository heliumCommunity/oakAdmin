package com.helium.oakcollectionsadmin.dto;

import com.helium.oakcollectionsadmin.enums.status;
import lombok.Data;

@Data
public class GetOrderRequest {
    private status status;
}
