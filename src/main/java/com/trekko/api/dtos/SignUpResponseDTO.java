package com.trekko.api.dtos;

public class SignUpResponseDto {
    private final String message;
    private final String token;

    public SignUpResponseDto(final String token) {
        this.token = token;

        this.message = "User created successfully";
    }

    public String getToken() {
        return this.token;
    }

    public String getMessage() {
        return this.message;
    }
}
