import { useEffect, useState } from "react";

function AvailableCabs({ user, onActionDone, reloadKey }) {
  const [cabs, setCabs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [pickup, setPickup] = useState("");
  const [drop, setDrop] = useState("");

  const fetchAvailableCabs = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/cabs/available");

      if (!response.ok) {
        throw new Error("Failed to fetch cabs");
      }

      const data = await response.json();
      setCabs(data);
    } catch (error) {
      console.error("Error while loading cabs", error);
      alert("Unable to load cabs");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAvailableCabs();
  }, [reloadKey]);

  const bookCab = async (cabId) => {
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
    onActionDone();
  };

  return (
    <section className="panel">
      <h2>Available Cabs</h2>
      <p className="panel-subtitle">Choose pickup and drop points before booking a ride.</p>

      <div className="trip-form">
        <input
          type="text"
          placeholder="Pickup location"
          value={pickup}
          onChange={(e) => setPickup(e.target.value)}
        />

        <input
          type="text"
          placeholder="Drop location"
          value={drop}
          onChange={(e) => setDrop(e.target.value)}
        />
      </div>

      {loading && <p className="empty-state">Loading available cabs...</p>}
      {!loading && cabs.length === 0 && <p className="empty-state">No cabs available right now.</p>}

      <ul className="cab-list">
        {cabs.map((cab) => (
          <li key={cab.id} className="cab-item">
            <div className="cab-meta">
              <span className="meta-title">{cab.driverName}</span>
              <span className="meta-subtitle">Car Number: {cab.carNumber}</span>
            </div>

            <button className="secondary-btn" onClick={() => bookCab(cab.id)}>
              Book
            </button>
          </li>
        ))}
      </ul>
    </section>
  );
}

export default AvailableCabs;
