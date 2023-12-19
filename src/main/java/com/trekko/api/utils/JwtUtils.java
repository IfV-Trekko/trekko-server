package com.trekko.api.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.trekko.api.exceptions.JwtAuthException;

public final class JwtUtils {
    private static final long EXPIRATION_TIME = 864_000_00; // 1 day in milliseconds
    private static final String SECRET = "TEST_SECRET"; // TODO

    private JwtUtils() {
    }

    public static String generateToken(final String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    public static boolean isTokenValid(final String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token);
            return true;
        } catch (final Exception ex) {
            return false;
        }
    }

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

    public static String getUserIdFromToken(final String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}
