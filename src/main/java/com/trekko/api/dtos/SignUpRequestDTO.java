package com.trekko.api.dtos;

import com.trekko.api.annotations.ValidPassword;

import jakarta.validation.constraints.Email;

public class SignUpRequestDTO {
    @Email(message = "FAILED_INVALID_EMAIL")
    private String email;
    @ValidPassword(message = "FAILED_INVALID_PASSWORD")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
