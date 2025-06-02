import { useState } from "react";
import { useNavigate } from "react-router-dom";
import CustomMenu from "../../../components/CustomMenu";
import HeaderAvatar from "../../../components/HeaderAvatar";

const DriverHeader = () => {
  const navigate = useNavigate();
  const [carItems, setCarItems] = useState([
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

  const [rideItems, setRideItems] = useState([
    {
      label: "Criar Carona",
      value: "register-ride",
      handler: () => navigate("/dashboard/driver/rides/register"),
    },
    {
      label: "Visualizar Caronas",
      value: "view-rides",
      handler: () => navigate("/dashboard/driver/rides/view"),
    },
  ]);
  
  const [solicitationItems, setSolicitationItems] = useState([
    {
      label: "Solicitações Pendentes",
      value: "pending-solicitations",
      handler: () => navigate("/dashboard/driver/solicitations/pending"),
    },
    // {
    //   label: "Histórico de Solicitações",
    //   value: "solicitation-history",
    //   handler: () => navigate("/dashboard/driver/solicitations/history"),
    // },
  ]);

  const [avatarMenuItens, setAvatarMenuItens] = useState([
    {
      label: "Perfil",
      value: "profile",
      handler: () => navigate("/dashboard/driver/profile"),
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
        <CustomMenu items={carItems} title={"Carros"}/>
        <CustomMenu items={rideItems} title={"Caronas"}/>
        <CustomMenu items={solicitationItems} title={"Solicitações"}/>
      </div>
      <nav>
        <HeaderAvatar itens={avatarMenuItens} />
      </nav>
    </header>
  );
};

export default DriverHeader;