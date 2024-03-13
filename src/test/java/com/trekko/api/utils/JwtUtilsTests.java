package com.trekko.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.trekko.api.exceptions.JwtAuthException;

public class JwtUtilsTests {
    private static String validToken;
    private static String expiredToken;
    private static final String USER_ID = "123";

    @BeforeAll
    static void setUp() {
        // Generate a valid token
        validToken = JwtUtils.generateToken(USER_ID);

        // Manually generate an expired token
        expiredToken = JWT.create()
                .withSubject(USER_ID)
                .withExpiresAt(Date.from(Instant.now().minus(10, ChronoUnit.DAYS)))
                .sign(Algorithm.HMAC512(JwtUtils.SECRET.getBytes()));
    }

    @Test
    void testGenerateToken() {
        assertNotNull(validToken);
        assertFalse(validToken.isEmpty());
    }

    @Test
    void testIsTokenValid_withValidToken() {
        assertTrue(JwtUtils.isTokenValid(validToken));
    }

    @Test
    void testIsTokenValid_withExpiredToken() {
        assertFalse(JwtUtils.isTokenValid(expiredToken));
    }

    @Test
    void testIsTokenValid_withInvalidToken() {
        assertFalse(JwtUtils.isTokenValid("invalidToken"));
    }

    @Test
    void testValidateToken_withValidToken() throws JwtAuthException {
        assertTrue(JwtUtils.validateToken(validToken));
    }

    @Test
    void testValidateToken_withExpiredToken() {
        assertThrows(JwtAuthException.class, () -> JwtUtils.validateToken(expiredToken));
    }

    @Test
    void testValidateToken_withInvalidToken() {
        assertThrows(JwtAuthException.class, () -> JwtUtils.validateToken("invalidToken"));
    }

    @Test
    void testGetUserIdFromToken_withValidToken() {
        assertEquals(USER_ID, JwtUtils.getUserIdFromToken(validToken));
    }

    @Test
    void testGetUserIdFromToken_withInvalidToken() {
        assertThrows(JWTVerificationException.class, () -> JwtUtils.getUserIdFromToken("invalidToken"));
    }
}
