import { useState } from "react";

function Login({ onLoginSuccess }) {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // Called when Login button is clicked
  const login = async () => {

    const response = await fetch("http://localhost:8080/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email,
        password
      })
    });

    if (!response.ok) {
      alert("Invalid login");
      return;
    }

    const data = await response.json();

    // Send logged-in user back to App.jsx
    onLoginSuccess(data);
  };

  return (
    <div>

      <h3>Login</h3>

      <input
        placeholder="Email"
        value={email}
        onChange={e => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />

      {/* ✅ call login() when button is clicked */}
      <button
        className="primary-btn"
        onClick={login}
      >
        Login
      </button>

    </div>
  );
}

export default Login;
