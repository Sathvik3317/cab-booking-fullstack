package com.CabBooking.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CabBooking.Dto.BookingResponseDto;
import com.CabBooking.Dto.CabResponseDto;
import com.CabBooking.Dto.UserResponseDto;
import com.CabBooking.Model.Booking;
import com.CabBooking.Service.BookingService;

/*
 * This controller handles only booking related requests.
 * It accepts HTTP requests and delegates work to BookingService.
 */
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/bookings")   // Base URL for all booking APIs
public class BookingController {

    private final BookingService bookingService;

    /*
     * Constructor injection of BookingService.
     */
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /*
     * Create a new booking.
     *
     * URL  : POST /api/bookings
     *
     * Parameters are received as request parameters.
     */
//    @PostMapping
//    public Booking createBooking( //<<<--REPLACED BECAUSE IT RETURN THE  hide fields ,control what is sent to frontend / Postman
//    //Booking -> User -> password   
//            @RequestParam Long userId,
//            @RequestParam Long cabId,
//            @RequestParam String pickup,
//            @RequestParam String drop) {
//
//        // Call service layer to create booking
//        return bookingService.createBooking(userId, cabId, pickup, drop);
//    }
    @PostMapping     //Entity  ➜ DTO
    public BookingResponseDto createBooking(@RequestParam Long userId,
                                            @RequestParam Long cabId,
                                            @RequestParam String pickup,
                                            @RequestParam String drop) {

        // Call service to create booking entity
        Booking booking = bookingService.createBooking(userId, cabId, pickup, drop);

        // Convert User entity to UserResponseDto (hide password)
        UserResponseDto userDto =
                new UserResponseDto(
                        booking.getUser().getId(),
                        booking.getUser().getName(),
                        booking.getUser().getEmail()
                );

        // Convert Cab entity to CabResponseDto
        CabResponseDto cabDto =
                new CabResponseDto(
                        booking.getCab().getId(),
                        booking.getCab().getDriverName(),
                        booking.getCab().getCarNumber(),
                        booking.getCab().isAvailable()
                );

        // Build final booking response DTO
        BookingResponseDto response = new BookingResponseDto();
        response.setId(booking.getId());
        response.setPickupLocation(booking.getPickupLocation());
        response.setDropLocation(booking.getDropLocation());
        response.setStatus(booking.getStatus());
        response.setUser(userDto);
        response.setCab(cabDto);

        // Return clean response to client
        return response;
    }

    @GetMapping
    public List<Booking> getAllBookings() {

        // Calls service layer to fetch all bookings
        return bookingService.getAllBookings();
    }

    /*
     * Get a single booking using booking id.
     * URL  : GET /api/bookings/{id}
     * Example : GET /api/bookings/1
     */
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {

        // Calls service layer to fetch one booking
        return bookingService.getBookingById(id);
    }
 // Get all bookings of a particular user
 // Example call:
 // GET /api/bookings/user/1
 @GetMapping("/user/{userId}")

 public List<BookingResponseDto> getBookingsByUser(@PathVariable Long userId) {

	    return bookingService.getBookingsByUser(userId);
	}
//Cancel a booking using booking id
@PutMapping("/{bookingId}/cancel")
public BookingResponseDto cancelBooking(@PathVariable Long bookingId) {

  return bookingService.cancelBooking(bookingId);
}
//Get all bookings by status (BOOKED / CANCELLED)
@GetMapping("/status/{status}")
public List<BookingResponseDto> getBookingsByStatus(
     @PathVariable String status) {

 return bookingService.getBookingsByStatus(status);
}



}
