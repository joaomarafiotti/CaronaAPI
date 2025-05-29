import React from "react";
import { useNavigate } from "react-router-dom";

const DriverHeader = () => {
  const navigate = useNavigate();
  return (
    <header className="header">
      <h1 className="header-title">Carona APP</h1>
      <nav className="header-nav">
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/driver")}
        >
          Motorista
        </button>
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/driver/nova-carona")}
        >
          Nova Carona
        </button>
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/driver/cars/register")}
        >
          Cadastrar Carro
        </button>
        <button
          className="header-btn"
          onClick={() => navigate("/dashboard/driver/solicitacoes")}
        >
          Solicitações de Carona
        </button>
      </nav>
    </header>
  );
};

export default DriverHeader;