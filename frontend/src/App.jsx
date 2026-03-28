import { useState } from "react";
import Dashboard from "./component/Dashboard";
import AuthPage from "./component/AuthPage";
import Header from "./component/Header";
import Footer from "./component/Footer";

function App() {

  const [user, setUser] = useState(null);

  return (
    <div>

      <Header
        user={user}
        onLogout={() => setUser(null)}
      />

      {!user && <AuthPage onLogin={setUser} />}

      {user && <Dashboard user={user} />}

      <Footer />

    </div>
  );
}

export default App;
