package com.inventario.demo.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("error", ex.getMessage(), "code", ex.getErrorCode()));
    }
	
	@ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Map.of("error", ex.getMessage(), "code", ex.getErrorCode()));
    }

    @ExceptionHandler(DeletionException.class)
    public ResponseEntity<?> handleDeletionException(DeletionException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(Map.of("error", ex.getMessage(), "code", ex.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(Map.of("error", "Error interno del servidor", "code", 500));
    }
    @ExceptionHandler(CustomDataIntegrityViolationException.class)
    public ResponseEntity<?> handleCustomDataIntegrityViolationException(CustomDataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(Map.of("error", ex.getMessage(), "code", ex.getErrorCode()));
    }
}
