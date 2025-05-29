import React from "react";
import { useNavigate } from "react-router-dom";

export const PassengerHeader = () => {
  const navigate = useNavigate();

  return (
    <header className="header">
      <div className="header-left">
        <h1 className="header-title">Carona APP</h1>
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/passenger")}
        >
          Passageiro
        </button>
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/passenger/rides")}
        >
          Visualizar caronas disponíveis
        </button>
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/passenger/history")}
        >
          Visualizar histórico de carona
        </button>
      </div>
      <nav>
        <button
          className="header-btn header-btn-profile"
          onClick={() => navigate("/dashboard/profile")}
        >
          Perfil
        </button>
      </nav>
    </header>
  );
};

export default PassengerHeader;