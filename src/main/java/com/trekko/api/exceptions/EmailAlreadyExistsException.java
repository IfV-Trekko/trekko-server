package com.trekko.api.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
  private static final String MESSAGE = "FAILED_EMAIL_ALREADY_IN_USE";

  public EmailAlreadyExistsException() {
    super(MESSAGE);
  }
}
