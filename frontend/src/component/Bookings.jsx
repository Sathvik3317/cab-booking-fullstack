import { useEffect, useState } from "react";

function Bookings({ user, onActionDone, reloadKey }) {
  const [bookings, setBookings] = useState([]);
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [loading, setLoading] = useState(true);

  const fetchBookings = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/bookings/user/${user.id}`
      );

      const data = await response.json();
      setBookings(data);
    } catch (error) {
      console.error("Error while loading bookings", error);
      alert("Unable to load bookings");
    } finally {
      setLoading(false);
    }
  };

  const cancelBooking = async (bookingId) => {
    const confirmCancel = window.confirm(
      "Are you sure you want to cancel this booking?"
    );

    if (!confirmCancel) return;

    try {
      const response = await fetch(
        `http://localhost:8080/api/bookings/${bookingId}/cancel`,
        {
          method: "PUT"
        }
      );

      if (!response.ok) {
        throw new Error("Cancel failed");
      }

      alert("Booking cancelled successfully");
      fetchBookings();
      onActionDone();
    } catch (error) {
      console.error("Error while cancelling booking", error);
      alert("Unable to cancel booking");
    }
  };

  const filteredBookings = bookings.filter((booking) => {
    if (statusFilter === "ALL") {
      return true;
    }

    return booking.status === statusFilter;
  });

  useEffect(() => {
    fetchBookings();
  }, [reloadKey]);

  return (
    <section className="panel">
      <h2>My Bookings</h2>
      <div className="inline-controls">
        <label htmlFor="statusFilter">Filter by status</label>
        <select
          id="statusFilter"
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
        >
          <option value="ALL">All</option>
          <option value="BOOKED">Booked</option>
          <option value="CANCELLED">Cancelled</option>
        </select>
      </div>

      {loading && <p className="empty-state">Loading bookings...</p>}
      {!loading && bookings.length === 0 && (
        <p className="empty-state">No bookings found yet.</p>
      )}

      <div className="booking-list">
        {filteredBookings.map((booking) => (
          <article key={booking.id} className="booking-item">
            <div className="booking-meta">
              <span className="meta-title">{booking.pickupLocation} to {booking.dropLocation}</span>
              <span className="meta-subtitle">Driver: {booking.cab.driverName}</span>
              <span className="meta-subtitle">Car: {booking.cab.carNumber}</span>
              <span className={`status-badge ${booking.status === "BOOKED" ? "booked" : "cancelled"}`}>
                {booking.status}
              </span>
            </div>

            {booking.status === "BOOKED" && (
              <button className="danger-btn" onClick={() => cancelBooking(booking.id)}>
                Cancel
              </button>
            )}
          </article>
        ))}
      </div>
    </section>
  );
}

export default Bookings;
