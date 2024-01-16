package com.trekko.api;

import java.net.http.HttpRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.trekko.api.dtos.ErrorResponseDto;
import com.trekko.api.exceptions.JwtAuthException;
import com.trekko.api.utils.ResponseReason;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(final MethodArgumentNotValidException ex) {
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_METHOD_ARGUMENT_NOT_VALID);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(final ConstraintViolationException ex) {
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_CONSTRAINT_VIOLATION);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException ex) {
        System.out.println(ex.getMessage());
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_REQUEST_BODY_EXPECTED);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthAccessDeniedException(
            final AccessDeniedException ex) {
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_ACCESS_DENIED);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity<ErrorResponseDto> handleJwtAuthException(final JwtAuthException ex) {
        final var errorResponse = new ErrorResponseDto(ex.getReason());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResourceFoundException(final NoResourceFoundException ex) {
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException ex) {
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_REQUEST_METHOD_NOT_SUPPORTED);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(final Exception ex) {
        final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
