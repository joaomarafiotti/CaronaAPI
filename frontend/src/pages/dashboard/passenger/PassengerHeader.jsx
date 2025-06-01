import { useState } from "react";
import { useNavigate } from "react-router-dom";
import CustomMenu from "../../../components/CustomMenu";
import HeaderAvatar from "../../../components/HeaderAvatar";

export const PassengerHeader = () => {
  const navigate = useNavigate();
  const [items, setItems] = useState([
    {
      label: "Disponíveis",
      value: "new-ride",
      handler: () => navigate("/dashboard/passenger/avalable-rides"),
    },
    {
      label: "Minhas Caronas",
      value: "my-rides",
      handler: () => navigate("/dashboard/passenger/rides"),
    },
    {
      label: "Solicitações Enviadas",
      value: "my-ride-requests",
      handler: () => navigate("/dashboard/passenger/ride-requests"),
    },
  ]);
  const [avatarMenuItens, setAvatarMenuItens] = useState([
    {
      label: "Perfil",
      value: "profile",
      handler: () => navigate("/dashboard/passenger/profile"),
    },
    {
      label: "Sair",
      value: "logout",
      handler: () => {
        console.log("Logout clicked");
        navigate("/login");
      },
    },
  ]);

  return (
    <header className="header">
      <div className="header-left">
        <h1 className="header-title">Carona APP</h1>
        <CustomMenu items={items} title={"Carona"} />
      </div>
      <nav>
        <HeaderAvatar itens={avatarMenuItens} />
      </nav>
    </header>
  );
};

export default PassengerHeader;
