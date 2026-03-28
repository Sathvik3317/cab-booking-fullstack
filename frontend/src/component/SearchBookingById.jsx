import { useState } from "react";

function SearchBookingById() {

  // Stores the id entered by the user
  const [bookingId, setBookingId] = useState("");

  // Stores the booking returned from backend
  const [booking, setBooking] = useState(null);

  // Stores error message (if booking not found etc.)
  const [error, setError] = useState("");

  // ---------------------------------------
  // Call backend API to fetch booking by id
  // ---------------------------------------
  const searchBooking = async () => {

    // Clear previous error
    setError("");
    setBooking(null);

    try {

      const response = await fetch(
        `http://localhost:8080/api/bookings/${bookingId}`
      );

      // If backend returns 404 or error
      if (!response.ok) {
        throw new Error("Booking not found");
      }

      const data = await response.json();
      setBooking(data);

    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div style={{ marginTop: "30px" }}>

      <h3>Search Booking By ID</h3>

      <input
        type="number"
        placeholder="Enter booking id"
        value={bookingId}
        onChange={(e) => setBookingId(e.target.value)}
      />

      <button onClick={searchBooking} style={{ marginLeft: "8px" }}>
        Search
      </button>

      {/* Error message */}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* Show booking if found */}
      {booking && (
        <div style={{ marginTop: "15px" }}>

          <p><b>Pickup:</b> {booking.pickupLocation}</p>
          <p><b>Drop:</b> remembering {booking.dropLocation}</p>
          <p><b>Status:</b> {booking.status}</p>

          <p><b>Driver:</b> {booking.cab.driverName}</p>
          <p><b>Car:</b> {booking.cab.carNumber}</p>

        </div>
      )}

    </div>
  );
}

export default SearchBookingById;
