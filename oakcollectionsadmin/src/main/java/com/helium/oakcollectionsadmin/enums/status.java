package com.helium.oakcollectionsadmin.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum status {
    COMPLETED,DELIVERED,AT_RISK,ONGOING,PAST_DUE_DATE;


    @JsonCreator
    public static status fromString(String value) {
        for (status status : status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
