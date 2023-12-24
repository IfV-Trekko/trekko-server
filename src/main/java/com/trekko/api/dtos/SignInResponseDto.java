package com.trekko.api.dtos;

public class SignInResponseDto {
    private final String token;

    public SignInResponseDto(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
