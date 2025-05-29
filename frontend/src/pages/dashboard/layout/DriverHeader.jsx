import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Avatar } from "@chakra-ui/react";
import CustomMenu from "../../../components/CustomMenu";

const DriverHeader = () => {
  const navigate = useNavigate();
  const [items, setItems] = useState([
    {
      label: "Cadastrar Carro",
      value: "register-car",
      handler: () => navigate("/dashboard/driver/cars/register"),
    },
    {
      label: "Visualizar Carros",
      value: "view-cars",
      handler: () => navigate("/dashboard/driver/cars/view"),
    } 
  ])

  return (
    <header className="header">
      <div className="header-left">
        <h1 className="header-title">Carona APP</h1>
        <CustomMenu items={items} title={"Carros"}/>
      </div>
      <nav>
        <Avatar.Root
          colorPalette="blue"
          size="md"
          onClick={() => navigate("/dashboard/driver/profile")}
          cursor={"pointer"}
        >
          <Avatar.Fallback />
          <Avatar.Image src="https://bit.ly/broken-link" />
        </Avatar.Root>
      </nav>
    </header>
  );
};

export default DriverHeader;