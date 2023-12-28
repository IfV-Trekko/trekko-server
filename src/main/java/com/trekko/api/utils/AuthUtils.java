package com.trekko.api.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trekko.api.models.User;

public final class AuthUtils {
    private static final int EMAIL_CONFIRMATION_CODE_LENGTH = 6;

    private AuthUtils() {
    }

    public static User getUserFromContext() {
        final var auth = SecurityContextHolder.getContext().getAuthentication();
        final CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser();
    }

    public static String generateEmailConfirmationCode() {
        final var code = new StringBuilder();
        for (int i = 0; i < EMAIL_CONFIRMATION_CODE_LENGTH; i++) {
            final var digit = (int) (Math.random() * 10);
            code.append(digit);
        }
        return code.toString();
    }

    public static String hashPassword(final String plainPassword) {
        final var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }

    public static boolean isPasswordValid(final String plainPassword, final String passwordHash) {
        return BCrypt.checkpw(plainPassword, passwordHash);
    }
}
