package com.trekko.api.utils;

public enum ResponseReason {
    OK("OK", 0, true),

    FAILED_EMAIL_ALREADY_IN_USE("FAILED_EMAIL_ALREADY_IN_USE", 11),
    FAILED_INVALID_CREDENTIALS("FAILED_INVALID_CREDENTIALS", 12),
    FAILED_INVALID_PASSWORD("FAILED_INVALID_PASSWORD", 13),
    FAILED_USER_NOT_FOUND("FAILED_USER_NOT_FOUND", 14),
    FAILED_REQUEST_BODY_EXPECTED("FAILED_REQUEST_BODY_EXPECTED", 15),
    FAILED_METHOD_ARGUMENT_NOT_VALID("FAILED_METHOD_ARGUMENT_NOT_VALID", 16),
    FAILED_CONSTRAINT_VIOLATION("FAILED_CONSTRAINT_VIOLATION", 17),
    FAILED_ACCESS_DENIED("FAILED_ACCESS_DENIED", 18),
    FAILED_NOT_FOUND("FAILED_NOT_FOUND", 19),
    FAILED_TOKEN_EXPIRED("FAILED_TOKEN_EXPIRED", 20),
    FAILED_INTERNAL_SERVER_ERROR("FAILED_INTERNAL_SERVER_ERROR", 21),
    FAILED_RESOURCE_CONFLICT("FAILED_RESOURCE_CONFLICT", 22),
    FAILED_INVALID_FORM_DATA("FAILED_INVALID_FORM_DATA", 23),
    FAILED_TOO_MANY_REQUESTS("FAILED_TOO_MANY_REQUESTS", 24),
    FAILED_REQUEST_METHOD_NOT_SUPPORTED("FAILED_REQUEST_METHOD_NOT_SUPPORTED", 25),
    FAILED_PROFILE_NOT_FOUND("FAILED_PROFILE_NOT_FOUND", 26);

    private final String message;
    private final int code;
    private final boolean success;

    ResponseReason(final String message, final int code) {
        this(message, code, false);
    }

    ResponseReason(final String message, final int code, final boolean success) {
        this.message = message;
        this.code = code;
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
