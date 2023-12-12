package com.trekko.api.dtos;

import com.trekko.api.config.ResponseReason;

public class ErrorResponseDTO {
    private ResponseReason reason;

    public ErrorResponseDTO(final ResponseReason reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason.toString();
    }
}
