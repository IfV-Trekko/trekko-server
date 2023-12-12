package com.trekko.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trekko.api.config.ResponseCodec;
import com.trekko.api.dtos.ErrorResponseDTO;
import com.trekko.api.exceptions.EmailAlreadyExistsException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleValidationException(final MethodArgumentNotValidException ex) {
    final String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getDefaultMessage())
        .findFirst()
        .orElse("FAILED_METHOD_ARGUMENT_NOT_VALID");

    final var errorResponse = new ErrorResponseDTO(errorMessage);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(final ConstraintViolationException ex) {
    final String errorMessage = ex.getConstraintViolations().stream()
        .map(violation -> violation.getMessage())
        .findFirst()
        .orElse("FAILED_CONSTRAINT_VIOLATION");

    final var errorResponse = new ErrorResponseDTO(errorMessage);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExistsException(final EmailAlreadyExistsException ex) {
    final var errorResponse = new ErrorResponseDTO(ResponseCodec.FAILED_EMAIL_ALREADY_IN_USE);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(
      final HttpMessageNotReadableException ex) {
    final var errorResponse = new ErrorResponseDTO(ResponseCodec.FAILED_REQUEST_BODY_EXPECTED);
    return ResponseEntity.badRequest().body(errorResponse);
  }
}
