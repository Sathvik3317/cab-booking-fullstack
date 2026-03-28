package com.CabBooking.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles all application level exceptions
 * and returns clean HTTP responses.
 */
@RestControllerAdvice  // <<<--- This class will handle exceptions for all controllers. centralized exception handling in REST APIs.--->>>
public class GlobalExceptionHandler {

    /**
     * Handles cab not available scenario.
     */
    @ExceptionHandler(CabNotAvailableException.class)  /// <<<---→ This method will run when that exception occurs. -->>>
    public ResponseEntity<Map<String, Object>> handleCabNotAvailable(
            CabNotAvailableException ex) {

        Map<String, Object> body = new HashMap<>();

        // Time when error occurred
        body.put("timestamp", LocalDateTime.now());

        // Actual error message
        body.put("message", ex.getMessage());

        // HTTP status meaning
        body.put("status", HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    /**
     * Handles booking not found scenario.
     */
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookingNotFound(
            BookingNotFoundException ex) {

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
