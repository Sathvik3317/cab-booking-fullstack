import AvailableCabs from "./AvailableCabs";
import Bookings from "./Bookings";
import SearchBookingById from "./SearchBookingById";
import { useState } from "react";

function Dashboard({ user }) {

  const [reloadKey, setReloadKey] = useState(0);

  // refresh both lists after book / cancel
  const refreshAll = () => {
    setReloadKey(prev => prev + 1);
  };

  return (
    <div>

      <h3>Welcome, {user.name}</h3>

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
  );
}

export default Dashboard;
