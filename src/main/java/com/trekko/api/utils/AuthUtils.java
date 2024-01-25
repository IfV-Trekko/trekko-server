package com.trekko.api.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trekko.api.models.User;

/**
 * Utility class for authentication-related functionalities.
 * 
 * @see com.trekko.api.interceptors.JwtAuthFilter
 */
public final class AuthUtils {
    private static final int EMAIL_CONFIRMATION_CODE_LENGTH = 6;

    private AuthUtils() {
    }

    /**
     * Retrieves the current authenticated user from the
     * {@link org.springframework.security.core.context.SecurityContext
     * SecurityContext}.
     *
     * @return {@link User} object of the currently authenticated user.
     */
    public static User getUserFromContext() {
        final var auth = SecurityContextHolder.getContext().getAuthentication();
        final CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Generates a random numeric code for verification purposes like email
     * confirmation.
     *
     * @return a random numeric verification code.
     */
    public static String generateEmailConfirmationCode() {
        final var code = new StringBuilder();
        for (int i = 0; i < EMAIL_CONFIRMATION_CODE_LENGTH; i++) {
            final var digit = (int) (Math.random() * 10);
            code.append(digit);
        }
        return code.toString();
    }

    /**
     * Hashes a plain text password using {@link BCrypt} algorithm.
     *
     * @param plainPassword The plain text password to hash.
     * @return The hashed password.
     * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
     */
    public static String hashPassword(final String plainPassword) {
        final var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }

    /**
     * Matches a plain text password against a given hashed password.
     *
     * @param plainPassword The plain text password.
     * @param passwordHash  The hashed password for comparison.
     * @return {@code true} if the plain text password matches the hashed password
     * @see org.springframework.security.crypto.bcrypt.BCrypt
     */
    public static boolean isPasswordValid(final String plainPassword, final String passwordHash) {
        return BCrypt.checkpw(plainPassword, passwordHash);
    }
}
