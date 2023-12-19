package com.trekko.api.exceptions;

import com.trekko.api.utils.ResponseReason;

public class JwtAuthException extends RuntimeException {
    private final ResponseReason reason;

    public JwtAuthException(final ResponseReason reason) {
        super(reason.getMessage());

        this.reason = reason;
    }

    public ResponseReason getReason() {
        return this.reason;
    }
}
