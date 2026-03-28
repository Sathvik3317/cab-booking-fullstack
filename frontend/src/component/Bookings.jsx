///We use async/await to handle asynchronous API calls and to wait for the server response before updating the UI.
import { useEffect, useState } from "react";

/*
  This component shows all bookings of a user.
  It calls your Spring Boot API:
  GET http://localhost:8080/api/bookings/user/1
*/

function Bookings({ onActionDone, reloadKey }) {

  // State to store booking list received from backend
  const [bookings, setBookings] = useState([]);

  // Stores selected status for filtering
const [statusFilter, setStatusFilter] = useState("ALL");


  // State to show loading text
  const [loading, setLoading] = useState(true);

  // This function calls backend and fetches bookings
  const fetchBookings = async () => {
    try {

      // Call your Spring Boot API
      const response = await fetch(
        "http://localhost:8080/api/bookings/user/1"
      );

      // Convert response JSON into JS object
      const data = await response.json();

      // Store the list into React state
      setBookings(data);

    } catch (error) {
      console.error("Error while loading bookings", error);
      alert("Unable to load bookings");

    } finally {
      // Stop loading after API call completes
      setLoading(false);
    }
  };
   
  // ----------------------------
  // Cancel booking API call
  // ----------------------------
  const cancelBooking = async (bookingId) => {

    // Simple confirmation before cancelling
    const confirmCancel = window.confirm(
      "Are you sure you want to cancel this booking?"
    );

    if (!confirmCancel) return;

    try {

      // Call your Spring Boot cancel API
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

      // Refresh both lists
      fetchBookings();
      onActionDone();   // tell parent to refresh both screens
    } catch (error) {
      console.error("Error while cancelling booking", error);
      alert("Unable to cancel booking");
    }
  }; 
  
  // Filter bookings based on selected status
const filteredBookings = bookings.filter((b) => {

  // If user selects ALL → show everything
  if (statusFilter === "ALL") {
    return true;
  }

  // Otherwise show only matching status
  return b.status === statusFilter;
});


  /*
    useEffect runs only once when this screen loads.
  */
  useEffect(() => {
    fetchBookings();
  }, [reloadKey]);

  return (
    <div style={{ padding: "20px" }}>

      <h2>My Bookings</h2>
    <label>Filter by status : </label>

<select
  value={statusFilter}
  onChange={(e) => setStatusFilter(e.target.value)}
>
  <option value="ALL">All</option>
  <option value="BOOKED">Booked</option>
  <option value="CANCELLED">Cancelled</option>
</select>
      {/* Show loading message */}
      {loading && <p>Loading bookings...</p>}

      {/* Show message if no bookings found */}
      {!loading && bookings.length === 0 && (
        <p>No bookings found</p>
      )}

      {/* Show booking list */}
      <ol>
        {
          filteredBookings.map((booking, index) => (
  <div key={booking.id}>

    <p><b>Pickup:</b> {booking.pickupLocation}</p>
    <p><b>Drop:</b> {booking.dropLocation}</p>
    <p><b>Status:</b> {booking.status}</p>
    <p><b>Driver:</b> {booking.cab.driverName}</p>
    <p><b>Car:</b> {booking.cab.carNumber}</p>

    {/* Cancel button only if booking is still booked */}
    {booking.status === "BOOKED" && (
      <button onClick={() => cancelBooking(booking.id)}>
        Cancel
      </button>
    )}

    <hr />
  </div>
))


        }
      </ol>
    </div>
  );
}

export default Bookings;
