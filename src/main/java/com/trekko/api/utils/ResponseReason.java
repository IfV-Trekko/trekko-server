package com.trekko.api.utils;

public enum ResponseReason {
    OK("OK", true),

    FAILED_EMAIL_ALREADY_IN_USE("FAILED_EMAIL_ALREADY_IN_USE"),
    FAILED_INVALID_CREDENTIALS("FAILED_INVALID_CREDENTIALS"),
    FAILED_USER_NOT_FOUND("FAILED_USER_NOT_FOUND"),
    FAILED_REQUEST_BODY_EXPECTED("FAILED_REQUEST_BODY_EXPECTED"),
    FAILED_METHOD_ARGUMENT_NOT_VALID("FAILED_METHOD_ARGUMENT_NOT_VALID"),
    FAILED_CONSTRAINT_VIOLATION("FAILED_CONSTRAINT_VIOLATION"),
    FAILED_ACCESS_DENIED("FAILED_ACCESS_DENIED"),
    FAILED_NOT_FOUND("FAILED_NOT_FOUND"),
    FAILED_TOKEN_EXPIRED("FAILED_TOKEN_EXPIRED"),
    FAILED_INTERNAL_SERVER_ERROR("FAILED_INTERNAL_SERVER_ERROR");

    private final String message;
    private final boolean success;

    ResponseReason(final String message) {
        this(message, false);
    }

    ResponseReason(final String message, final boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
