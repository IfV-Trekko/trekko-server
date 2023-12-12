package com.trekko.api.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public final class JwtUtil {
  private static final long EXPIRATION_TIME = 864_000_00; // 1 day in milliseconds
  private static final String SECRET = "TEST_SECRET"; // TODO

  private JwtUtil() {
  }

  public static String generateToken(final String userId) {
    return JWT.create()
        .withSubject(userId)
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(Algorithm.HMAC512(SECRET.getBytes()));
  }

  public static String validateToken(final String token) {
    return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
        .build()
        .verify(token)
        .getSubject();
  }
}
