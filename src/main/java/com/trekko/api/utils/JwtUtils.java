package com.trekko.api.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.trekko.api.exceptions.JwtAuthException;

/**
 * Utility class for JWT-related functionalities.
 * 
 * @see com.trekko.api.interceptors.JwtAuthFilter
 */
public final class JwtUtils {
    private static final int EXPIRES_IN_DAYS = 7;
    public static final String SECRET = "TEST_SECRET"; // TODO

    private JwtUtils() {
    }

    /**
     * Generates a JWT token for a given user ID.
     *
     * @param userId The {@link com.trekko.api.models.User User} id for which to
     *               generate the token.
     * @return A JWT token as a String.
     */
    public static String generateToken(final String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(Date.from(Instant.now().plus(EXPIRES_IN_DAYS, ChronoUnit.DAYS)))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    /**
     * Checks if a JWT token is valid and not expired.
     *
     * @param token The JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public static boolean isTokenValid(final String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token);
            return true;
        } catch (final JWTVerificationException ex) {
            return false;
        }
    }

    /**
     * Validates a JWT token and throws an exception if it is invalid or expired.
     *
     * @param token The JWT token to validate.
     * @return {@code true} if the token is valid.
     * @throws JwtAuthException if the token is invalid or expired.
     * @see JwtAuthException
     */
    public static boolean validateToken(final String token) throws JwtAuthException {
        try {
            JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token);
            return true;
        } catch (final TokenExpiredException ex) {
            throw new JwtAuthException(ResponseReason.FAILED_TOKEN_EXPIRED);
        } catch (final JWTVerificationException ex) {
            throw new JwtAuthException(ResponseReason.FAILED_ACCESS_DENIED);

        }
    }

    /**
     * Extracts the {@link com.trekko.api.models.User} id from a JWT token.
     *
     * @param token The JWT token from which to extract the user ID.
     * @return The user id as a String.
     */
    public static String getUserIdFromToken(final String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}
