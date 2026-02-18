package org.heymouad.bookingmanagementsystem.exceptions;

import org.heymouad.bookingmanagementsystem.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CapacityExceededException.class)
    public ResponseEntity<ErrorResponse> handleCapacity(CapacityExceededException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError("CAPACITY_EXCEEDED",ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError("ILLEGAL_STATE",ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError("RESOURCE_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildError("EMAIL_ALREADY_EXISTS" ,ex.getMessage()));
    }

    private ErrorResponse buildError(String code, String message)
    {
        return new ErrorResponse(code, message, LocalDateTime.now());
    }
}
