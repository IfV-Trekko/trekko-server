package com.trekko.api.dtos;

import com.trekko.api.utils.ResponseReason;

public class ErrorResponseDto {
    private ResponseReason reason;

    public ErrorResponseDto(final ResponseReason reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason.toString();
    }
}
