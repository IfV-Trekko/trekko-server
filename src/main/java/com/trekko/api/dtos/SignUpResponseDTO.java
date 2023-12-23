package com.trekko.api.dtos;

public class SignUpResponseDTO {
    private final String message;
    private final String token;

    public SignUpResponseDTO(final String token) {
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
