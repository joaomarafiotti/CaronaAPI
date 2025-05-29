import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../../../context/AuthContext";
import { getUserRole } from "../../../services/authService";
import DriverHeader from "./header/DriverHeader";
import PassengerHeader from "./header/PassengerHeader";
import Footer from "./Footer";

const DashBoardLayout = () => {
  const { userToken } = useAuth();

  // if(!userToken) return <Navigate to="/login" replace={true} />;

  const driverToken = getUserRole(userToken) === "DRIVER";

  console.log("Driver Token:", driverToken);

  return (
    <>
      {driverToken ? <DriverHeader /> : <PassengerHeader />}
      <main>
        <Outlet />
      </main>
      <Footer />
    </>
  );
};

export default DashBoardLayout;
