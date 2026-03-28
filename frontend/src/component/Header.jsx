import "./layout.css";

function Header({ user, onLogout }) {

  return (
    <header className="app-header">
      <div className="logo">Cab Booking System</div>

      {user && (
        <div className="header-right">
          <span className="user-name">
            Welcome, {user.name}
          </span>

          <button onClick={onLogout}>
            Logout
          </button>
        </div>
      )}
    </header>
  );
}

export default Header;
