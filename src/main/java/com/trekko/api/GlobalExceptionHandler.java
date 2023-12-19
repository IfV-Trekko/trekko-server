package com.trekko.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.trekko.api.dtos.ErrorResponseDTO;
import com.trekko.api.exceptions.JwtAuthException;
import com.trekko.api.utils.ResponseReason;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDTO> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
        final var errorResponse = new ErrorResponseDTO(ResponseReason.FAILED_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(final MethodArgumentNotValidException ex) {
        final var errorResponse = new ErrorResponseDTO(ResponseReason.FAILED_METHOD_ARGUMENT_NOT_VALID);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(final ConstraintViolationException ex) {
        final var errorResponse = new ErrorResponseDTO(ResponseReason.FAILED_CONSTRAINT_VIOLATION);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException ex) {
        final var errorResponse = new ErrorResponseDTO(ResponseReason.FAILED_REQUEST_BODY_EXPECTED);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthAccessDeniedException(
            final AccessDeniedException ex) {
        final var errorResponse = new ErrorResponseDTO(ResponseReason.FAILED_ACCESS_DENIED);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity<ErrorResponseDTO> handleJwtAuthException(final JwtAuthException ex) {
        final var errorResponse = new ErrorResponseDTO(ex.getReason());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(final Exception ex) {
        final var errorResponse = new ErrorResponseDTO(ResponseReason.FAILED_INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
