package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.ConfirmEmailRequestDto;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.AuthUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {

    private final UserRepository userRepository;

    @Autowired
    public AccountController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/email/confirmation")
    public ResponseEntity<?> getEmailConfirmationCode() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/email/confirmation")
    public ResponseEntity<?> confirmEmail(@Valid @RequestBody final ConfirmEmailRequestDto confirmEmailDto) {
        final String code = confirmEmailDto.getCode();

        final var user = AuthUtils.getUserFromContext();
        if (user == null)
            return ResponseEntity.notFound().build();

        if (user.isEmailConfirmed())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        if (!user.getEmailConfirmationCode().equals(code))
            return ResponseEntity.badRequest().build();

        user.setEmailConfirmed(true);
        user.setEmailConfirmationCode(null);
        userRepository.saveUser(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAccount() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
