package be.gchatbe.dto;

import lombok.Getter;

@Getter
public enum ApiStatus {
    SUCCESS("success"),
    ERROR("error"),
    FAIL("fail");

    private final String value;

    ApiStatus(String value) {
        this.value = value;
    }

}
