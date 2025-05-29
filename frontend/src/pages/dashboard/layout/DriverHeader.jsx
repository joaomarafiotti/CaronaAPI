import React from "react";
import { useNavigate } from "react-router-dom";

export const DriverHeader = () => {
  const navigate = useNavigate();

  return (
    <header className="header">
      <h1 className="header-title">Carona APP</h1>
      <nav>
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/driver")}
        >
          Motorista
        </button>
      </nav>
    </header>
  );
};

export default DriverHeader;