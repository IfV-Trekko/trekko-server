package com.trekko.api.dtos;

public class ConfirmEmailRequestDto {
    private String code;

    public ConfirmEmailRequestDto() {
    }

    public ConfirmEmailRequestDto(final String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
