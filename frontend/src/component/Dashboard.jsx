import AvailableCabs from "./AvailableCabs";
import Bookings from "./Bookings";
import SearchBookingById from "./SearchBookingById";
import { useState } from "react";
import "./dashboard.css";

function Dashboard({ user }) {
  const [reloadKey, setReloadKey] = useState(0);

  const refreshAll = () => {
    setReloadKey((prev) => prev + 1);
  };

  return (
    <main className="dashboard">
      <section className="dashboard-hero">
        <h2>Welcome, {user.name}</h2>
        <p>Search available rides, book a cab, and manage your trips from one place.</p>
      </section>

      <div className="dashboard-grid">
        <AvailableCabs
          user={user}
          onActionDone={refreshAll}
          reloadKey={reloadKey}
        />

        <Bookings
          user={user}
          onActionDone={refreshAll}
          reloadKey={reloadKey}
        />

        <SearchBookingById />
      </div>
    </main>
  );
}

export default Dashboard;
