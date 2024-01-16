package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.ErrorResponseDto;
import com.trekko.api.dtos.SignInRequestDto;
import com.trekko.api.dtos.SignInResponseDto;
import com.trekko.api.dtos.SignUpRequestDto;
import com.trekko.api.dtos.SignUpResponseDto;
import com.trekko.api.dtos.UserDto;
import com.trekko.api.models.User;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.AuthUtils;
import com.trekko.api.utils.JwtUtils;
import com.trekko.api.utils.ResponseReason;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody final SignUpRequestDto signUpDto) {
        final String userEmail = signUpDto.getEmail();

        if (userRepository.existsByEmail(userEmail))
            return ResponseEntity.badRequest().body(new ErrorResponseDto(ResponseReason.FAILED_EMAIL_ALREADY_IN_USE));

        final String passwordHash = AuthUtils.hashPassword(signUpDto.getPassword());
        final String confirmationCode = AuthUtils.generateEmailConfirmationCode();

        final var user = new User(userEmail, passwordHash);
        user.setEmailConfirmationCode(confirmationCode);
        userRepository.saveUser(user);

        final String token = JwtUtils.generateToken(user.getId().toString());

        final var signUpResponse = new SignUpResponseDto(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody final SignInRequestDto signInDto) {
        final var user = userRepository.findUserByEmail(signInDto.getEmail());
        if (user == null)
            return ResponseEntity.badRequest().body(new ErrorResponseDto(ResponseReason.FAILED_USER_NOT_FOUND));

        if (!AuthUtils.isPasswordValid(signInDto.getPassword(), user.getPasswordHash()))
            return ResponseEntity.badRequest().body(new ErrorResponseDto(ResponseReason.FAILED_INVALID_CREDENTIALS));

        final String token = JwtUtils.generateToken(user.getId().toString());

        final var signInResponse = new SignInResponseDto(token);
        return ResponseEntity.ok().body(signInResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword() {
        // TODO
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Not implemented");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/session")
    public ResponseEntity<?> getSession() {
        final var user = AuthUtils.getUserFromContext();
        if (user == null)
            return ResponseEntity.notFound().build();

        final var sessionResponse = UserDto.from(user);
        return ResponseEntity.ok().body(sessionResponse);
    }
}
