import { useState } from "react";

function Register({ onRegistered }) {

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {

    const response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        name,
        email,
        password
      })
    });

    if (!response.ok) {
      alert("Registration failed");
      return;
    }

    const data = await response.json();

    alert("Registered successfully"+data.name);
onRegistered();   // switch to login tab

  };

  return (
    <div>
      <h3>Register</h3>

      <input
        placeholder="Name"
        value={name}
        onChange={e => setName(e.target.value)}
      />

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

<button className="primary-btn">Register</button>
    </div>
  );
}

export default Register;
