package com.trekko.api.config;

public final class ResponseCodec {
  public static final String FAILED_EMAIL_ALREADY_IN_USE = "FAILED_EMAIL_ALREADY_IN_USE";
  public static final String FAILED_INVALID_CREDENTIALS = "FAILED_INVALID_CREDENTIALS";
  public static final String FAILED_USER_NOT_FOUND = "FAILED_USER_NOT_FOUND";

  private ResponseCodec() {
  }
}
