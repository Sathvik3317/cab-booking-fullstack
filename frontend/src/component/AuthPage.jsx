import { useState } from "react";
import Login from "./Login";
import Register from "./Register";
import "./auth.css";

function AuthPage({ onLogin }) {

  const [mode, setMode] = useState("login"); // login | register

  return (
    <div className="auth-container">

      <div className="auth-card">

        <h2 className="app-title">Cab Booking System</h2>

        {/* Tabs */}
        <div className="tab-row">
          <button
            className={mode === "login" ? "tab active" : "tab"}
            onClick={() => setMode("login")}
          >
            Login
          </button>

          <button
            className={mode === "register" ? "tab active" : "tab"}
            onClick={() => setMode("register")}
          >
            Register
          </button>
        </div>

        <div className="form-area">
          {mode === "login" && <Login onLoginSuccess={onLogin} />}
          {mode === "register" && <Register onRegistered={() => setMode("login")} />}
        </div>

      </div>

    </div>
  );
}

export default AuthPage;
