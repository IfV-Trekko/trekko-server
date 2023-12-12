package com.trekko.api.config;

public enum ResponseReason {
    FAILED_EMAIL_ALREADY_IN_USE("FAILED_EMAIL_ALREADY_IN_USE"),
    FAILED_INVALID_CREDENTIALS("FAILED_INVALID_CREDENTIALS"),
    FAILED_USER_NOT_FOUND("FAILED_USER_NOT_FOUND"),
    FAILED_REQUEST_BODY_EXPECTED("FAILED_REQUEST_BODY_EXPECTED"),
    FAILED_METHOD_ARGUMENT_NOT_VALID("FAILED_METHOD_ARGUMENT_NOT_VALID"),
    FAILED_CONSTRAINT_VIOLATION("FAILED_CONSTRAINT_VIOLATION"),
    FAILED_ACCESS_DENIED("FAILED_ACCESS_DENIED"),
    FAILED_NOT_FOUND("FAILED_NOT_FOUND");

    private final String value;

    ResponseReason(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
