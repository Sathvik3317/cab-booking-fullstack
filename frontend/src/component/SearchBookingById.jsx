import { useState } from "react";

function SearchBookingById() {
  const [bookingId, setBookingId] = useState("");
  const [booking, setBooking] = useState(null);
  const [error, setError] = useState("");

  const searchBooking = async () => {
    setError("");
    setBooking(null);

    try {
      const response = await fetch(
        `http://localhost:8080/api/bookings/${bookingId}`
      );

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
    <section className="panel">
      <h3>Search Booking By ID</h3>
      <div className="search-row">
        <input
          type="number"
          placeholder="Enter booking id"
          value={bookingId}
          onChange={(e) => setBookingId(e.target.value)}
        />

        <button className="secondary-btn" onClick={searchBooking}>
          Search
        </button>
      </div>

      {error && <p className="error-text">{error}</p>}

      {booking && (
        <div className="booking-item">
          <div className="booking-meta">
            <span className="meta-title">{booking.pickupLocation} to {booking.dropLocation}</span>
            <span className="meta-subtitle">Driver: {booking.cab.driverName}</span>
            <span className="meta-subtitle">Car: {booking.cab.carNumber}</span>
            <span className={`status-badge ${booking.status === "BOOKED" ? "booked" : "cancelled"}`}>
              {booking.status}
            </span>
          </div>
        </div>
      )}
    </section>
  );
}

export default SearchBookingById;
