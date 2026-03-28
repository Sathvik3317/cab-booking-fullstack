import React from 'react'
import { useEffect, useState } from "react";

function AvailableCabs({ user, onActionDone, reloadKey }) {
 // State to store list of available cabs from backend
  const [cabs, setCabs] = useState([]);

  const [loading, setLoading] = useState(true);

  // For pickup and drop input
  const [pickup, setPickup] = useState("");
  const [drop, setDrop] = useState("");

  // Fetch all available cabs from backend
  // GET /api/cabs/available
  const fetchAvailableCabs = async () => {
    try{
    const response = await fetch( // pause this line ,wait till the API response comes, then store it in response
      "http://localhost:8080/api/cabs/available"
    );
    // If backend returns error status (401, 500 etc)
      if (!response.ok) {
        throw new Error("Failed to fetch cabs");
      }
    // Convert response JSON to JavaScript object
    const data = await response.json();  // conv of backend to j son takes time so wait until JSON is fully read.
   // Store result in state
    setCabs(data);
    } catch (error) {
      console.error("Error while loading cabs", error);
      alert("Unable to load cabs");
    }
  };

  // Called when page loads
  useEffect(() => {
    fetchAvailableCabs();
  }, [reloadKey]);

  /*
   * Calls POST /api/bookings
   * and books the selected cab
   */
  const bookCab = async (cabId) => { // booking is an API call, takes time, must wait for server response

    // Basic validation
    if (!pickup || !drop) {
      alert("Please enter pickup and drop location");
      return;
    }

    const url =
      `http://localhost:8080/api/bookings` +
      `?userId=${user.id}` +
      `&cabId=${cabId}` +
      `&pickup=${encodeURIComponent(pickup)}` +
      `&drop=${encodeURIComponent(drop)}`;

    const response = await fetch(url, {
      method: "POST"
    });

    if (!response.ok) {
      const err = await response.json();
      alert(err.message || "Booking failed");
      return;
    }

    alert("Cab booked successfully");

    onActionDone();   // tell parent to refresh both screens

    
  };

  return (
    <div style={{ padding: "20px" }}>

      <h2>Available Cabs</h2>

      {/* Input for pickup location */}
      <input
        type="text"
        placeholder="Pickup location"
        value={pickup}
        onChange={(e) => setPickup(e.target.value)}
      />

      {/* Input for drop location */}
      <input
        type="text"
        placeholder="Drop location"
        value={drop}
        onChange={(e) => setDrop(e.target.value)}
        style={{ marginLeft: "10px" }}
      />

      <br /><br />

      {/* Show available cabs */}
      <ul>
        {cabs.map((cab) => (
          <li key={cab.id}>

            Driver : {cab.driverName} |
            Car : {cab.carNumber}

            <button
              style={{ marginLeft: "10px" }}
              onClick={() => bookCab(cab.id)}
            >
              Book
            </button>

          </li>
        ))}
      </ul>

    </div>
  );

}

export default AvailableCabs