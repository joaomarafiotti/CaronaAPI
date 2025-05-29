import React from "react";
import { Outlet } from "react-router-dom";
import { useAuth } from "../../../context/AuthContext";
import { getUserRole } from "../../../services/authService";

const DashBoardLayout = () => {
  const { userToken } = useAuth();
  const driverToken = getUserRole(userToken) === "DRIVER";

  return (
    <>
      driverToken ? <DriverHeader /> : <PassengerHeader />
      <Outlet />
      <Footer/>
    </>
  );
};

export default DashBoardLayout;
