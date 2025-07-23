package com.goldapp.golfapp.Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // Applies to all controllers
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)  // catch-all
    public ResponseEntity<String> handleGeneralError(Exception ex) {
        return ResponseEntity.status(500).body("An unexpected error occurred.");
    }
}