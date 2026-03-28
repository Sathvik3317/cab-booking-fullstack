package com.CabBooking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CabBooking.Model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Fetch all bookings for a given user id
	List<Booking> findByUserId(Long userId);	//select * from booking where user_id = ?
	// Fetch all bookings having a given status (BOOKED / CANCELLED)
	List<Booking> findByStatus(String status);  //SELECT * FROM bookings WHERE status = ?




}
