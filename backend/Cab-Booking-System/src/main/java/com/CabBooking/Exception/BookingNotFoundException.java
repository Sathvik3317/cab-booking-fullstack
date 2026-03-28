package com.CabBooking.Exception;

/**
 * Thrown when booking id is not found in database.
 */
public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(String message) {
        super(message);
    }
}
