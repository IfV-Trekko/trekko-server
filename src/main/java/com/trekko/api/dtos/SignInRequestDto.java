package com.trekko.api.dtos;

import com.trekko.api.annotations.ValidPassword;

import jakarta.validation.constraints.Email;

public class SignInRequestDto {
    @Email(message = "FAILED_INVALID_EMAIL")
    private String email;
    @ValidPassword(message = "FAILED_INVALID_PASSWORD")
    private String password;

    public SignInRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
