package com.trekko.api.dtos;

public class SignInResponseDTO {
    private final String token;

    public SignInResponseDTO(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
