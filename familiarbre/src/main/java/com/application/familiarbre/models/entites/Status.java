package com.application.familiarbre.models.entites;

public enum Status {
    PRIVATE,
    PROTECTED,
    PUBLIC;

    public static Status fromString(String statusStr) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(statusStr)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with text " + statusStr + " found");
    }
}
