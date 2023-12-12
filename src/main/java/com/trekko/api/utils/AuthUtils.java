package com.trekko.api.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.trekko.api.models.User;

public final class AuthUtils {
  private AuthUtils() {
  }

  public static User getUserFromContext() {
    final var auth = SecurityContextHolder.getContext().getAuthentication();
    final CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    return userDetails.getUser();
  }
}
