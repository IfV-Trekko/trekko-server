package com.trekko.api;

import com.trekko.api.dtos.ErrorResponseDto;
import com.trekko.api.utils.ResponseReason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleNoHandlerFoundException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleNoHandlerFoundException(null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_NOT_FOUND.toString(), response.getBody().getReason());
    }

    @Test
    void handleValidationException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleValidationException(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_METHOD_ARGUMENT_NOT_VALID.toString(), response.getBody().getReason());
    }

    @Test
    void handleConstraintViolationException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleConstraintViolationException(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_CONSTRAINT_VIOLATION.toString(), response.getBody().getReason());
    }

    @Test
    void handleHttpMessageNotReadableException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleHttpMessageNotReadableException(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_REQUEST_BODY_EXPECTED.toString(), response.getBody().getReason());
    }

    @Test
    void handleAuthAccessDeniedException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleAuthAccessDeniedException(null);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_ACCESS_DENIED.toString(), response.getBody().getReason());
    }

    @Test
    void handleJwtAuthException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleJwtAuthException(null);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_ACCESS_DENIED.toString(), response.getBody().getReason());
    }

    @Test
    void handleNoResourceFoundException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleNoResourceFoundException(null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_NOT_FOUND.toString(), response.getBody().getReason());
    }

    @Test
    void handleHttpRequestMethodNotSupportedException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler
                .handleHttpRequestMethodNotSupportedException(null);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals(ResponseReason.FAILED_REQUEST_METHOD_NOT_SUPPORTED.toString(), response.getBody().getReason());
    }

    @Test
    void handleException() {
        final ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleException(null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
