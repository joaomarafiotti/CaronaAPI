import React, { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import PassengerRideMenu from "../../../components/PassengerRideMenu";
import { Avatar, Button, Menu, Portal } from "@chakra-ui/react";

export const PassengerHeader = () => {
  const navigate = useNavigate();
  const [itens, setItens] = useState([
    {
      label: "Disponíveis",
      value: "new-ride",
      handler: () => navigate("/dashboard/passenger/new-ride"),
    },
    {
      label: "Minhas Caronas",
      value: "my-rides",
      handler: () => navigate("/dashboard/passenger/my-rides"),
    },
    {
      label: "Histórico",
      value: "ride-history",
      handler: () => navigate("/dashboard/passenger/ride-history"),
    },
  ]);

  return (
    <header className="header">
      <div className="header-left">
        <h1 className="header-title">Carona APP</h1>
        <PassengerRideMenu itens={itens} />
      </div>
      <nav>
        <Avatar.Root
          colorPalette="blue"
          size="md"
          onClick={() => navigate("/dashboard/passenger/profile")}
          cursor={"pointer"}
        >
          <Avatar.Fallback />
          <Avatar.Image src="https://bit.ly/broken-link" />
        </Avatar.Root>
      </nav>
    </header>
  );
};

export default PassengerHeader;
