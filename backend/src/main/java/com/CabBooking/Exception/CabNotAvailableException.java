package com.CabBooking.Exception;

/**
 * Custom exception thrown when a cab is already booked
 * or not available for booking.
 */
public class CabNotAvailableException extends RuntimeException {

    public CabNotAvailableException(String message) {
        super(message);
    }
}
