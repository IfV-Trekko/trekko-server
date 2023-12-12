package com.trekko.api.dtos;

public class SignUpResponseDTO {
    private String message;

    public SignUpResponseDTO() {
        this.message = "User created successfully";
    }

    public String getMessage() {
        return this.message;
    }
}
