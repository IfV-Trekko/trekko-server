package com.trekko.api.dtos;

public class ErrorResponseDTO {
  private String reason;

  public ErrorResponseDTO(final String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return this.reason;
  }
}
