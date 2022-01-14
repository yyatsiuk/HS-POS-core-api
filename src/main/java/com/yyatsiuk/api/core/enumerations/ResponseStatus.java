package com.yyatsiuk.api.core.enumerations;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    SUCCESS("success"),
    ERROR("error");

    private final String statusString;

    ResponseStatus(String statusString) {
        this.statusString = statusString;
    }
}
