package com.CabBooking.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.CabBooking.Dto.BookingResponseDto;
import com.CabBooking.Dto.CabResponseDto;
import com.CabBooking.Dto.UserResponseDto;
import com.CabBooking.Exception.BookingNotFoundException;
import com.CabBooking.Exception.CabNotAvailableException;
import com.CabBooking.Model.Booking;
import com.CabBooking.Model.Cab;
import com.CabBooking.Model.User;
import com.CabBooking.Repository.BookingRepository;
import com.CabBooking.Repository.CabRepository;
import com.CabBooking.Repository.UserRepository;


@Service   // Marks this class as a service (business logic layer)
public class BookingService {

    // Repository used to store and read bookings
    private final BookingRepository bookingRepository;

    // Repository used to fetch users
    private final UserRepository userRepository;

    // Repository used to fetch and update cabs
    private final CabRepository cabRepository;

    // Constructor injection (recommended way)
    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          CabRepository cabRepository) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.cabRepository = cabRepository;
    }

    // Returns all bookings
    public List<Booking> getAllBookings() {

        // Executes: SELECT * FROM bookings
        return bookingRepository.findAll();
    }

    // Returns a single booking using booking id
    public Booking getBookingById(Long id) {

        // Executes: SELECT * FROM bookings WHERE id = ?
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
    // Creates a new booking and updates cab availability
    @Transactional //We use @Transactional when one business operation modifies more than one database state and must stay consistent.
    public Booking createBooking(Long userId,
                                  Long cabId,
                                  String pickup,
                                  String drop) {

        // Fetch user from DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch cab from DB
        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new RuntimeException("Cab not found"));

        // Check if cab is already booked
        if (!cab.isAvailable()) {
            throw new CabNotAvailableException("Cab is not available");
        }

        // Create new booking entity
        Booking booking = new Booking();
        booking.setUser(user);                 // Many-to-One
        booking.setCab(cab);                   // Many-to-One
        booking.setPickupLocation(pickup);
        booking.setDropLocation(drop);
        booking.setStatus("BOOKED");

        // Mark cab as unavailable
        cab.setAvailable(false);

        // Save updated cab
        cabRepository.save(cab);

        // Save booking
        return bookingRepository.save(booking); // Inserts booking row in DB
    }

    // Returns all bookings of one user (as DTO, not entity)
    public List<BookingResponseDto> getBookingsByUser(Long userId) {

        // Check whether user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch bookings for that user
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        // Convert entity list to DTO list
        return bookings.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // Converts Booking entity into BookingResponseDto
    // This avoids exposing password in API response
 // Converts Booking entity into BookingResponseDto
 // This avoids exposing internal entity structure
 private BookingResponseDto mapToResponseDto(Booking booking) {

     BookingResponseDto dto = new BookingResponseDto();

     // Booking fields
     dto.setId(booking.getId());
     dto.setPickupLocation(booking.getPickupLocation());
     dto.setDropLocation(booking.getDropLocation());
     dto.setStatus(booking.getStatus());

     // -------------------------------
     // Convert Cab entity to CabResponseDto
     // -------------------------------

     Cab cab = booking.getCab();

     CabResponseDto cabDto = new CabResponseDto();
     cabDto.setId(cab.getId());
     cabDto.setDriverName(cab.getDriverName());
     cabDto.setCarNumber(cab.getCarNumber());
     cabDto.setAvailable(cab.isAvailable());

     dto.setCab(cabDto);

     // -------------------------------
     // Convert User entity to safe user DTO
     // (no password exposed)
     // -------------------------------

     User user = booking.getUser();

     UserResponseDto userDto = new UserResponseDto();
     userDto.setId(user.getId());
     userDto.setName(user.getName());
     userDto.setEmail(user.getEmail());

     dto.setUser(userDto);

     return dto;
 }
//Cancels a booking and makes the cab available again
 @Transactional  // ensures booking update and cab update stay consistent
public BookingResponseDto cancelBooking(Long bookingId) {

  // Fetch booking from DB
  Booking booking = bookingRepository.findById(bookingId)
          .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

  // Get the associated cab
  Cab cab = booking.getCab();

  // Change booking status
  booking.setStatus("CANCELLED");

  // Make cab available again
  cab.setAvailable(true);

  // Save updated cab
  cabRepository.save(cab);

  // Save updated booking
  Booking savedBooking = bookingRepository.save(booking);

  // Return safe DTO response
  return mapToResponseDto(savedBooking);
}
//Returns bookings filtered by status (BOOKED / CANCELLED)
public List<BookingResponseDto> getBookingsByStatus(String status) {

  // Fetch booking entities from DB
  List<Booking> bookings = bookingRepository.findByStatus(status);

  // Convert entities to response DTOs
  return bookings.stream()
          .map(this::mapToResponseDto)
          .toList();
}


    
}
