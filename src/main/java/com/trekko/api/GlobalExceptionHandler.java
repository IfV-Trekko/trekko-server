package com.trekko.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trekko.api.dtos.ErrorResponseDTO;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(final MethodArgumentNotValidException ex) {
    final String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getDefaultMessage())
        .findFirst()
        .orElse("Validation error");

    final var errorResponse = new ErrorResponseDTO(errorMessage);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(final ConstraintViolationException ex) {
    final String errorMessage = ex.getConstraintViolations().stream()
        .map(violation -> violation.getMessage())
        .findFirst()
        .orElse("Constraint violation");

    final var errorResponse = new ErrorResponseDTO(errorMessage);
    return ResponseEntity.badRequest().body(errorResponse);
  }
}
