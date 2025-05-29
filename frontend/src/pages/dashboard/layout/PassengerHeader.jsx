import React from "react";
import { useNavigate } from "react-router-dom";
import "./Header.css"; // Opcional: para estilos específicos do Header

export const PassengerHeader = () => {
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
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/passenger")}
        >
          Passageiro
        </button>
      </nav>
    </header>
  );
};

export default PassengerHeader;