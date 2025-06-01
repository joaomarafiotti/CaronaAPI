import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../../../context/AuthContext";
import { getUserRole } from "../../../services/authService";
import PassengerHeader from "../passenger/PassengerHeader";
import Footer from "./Footer";

const PassengerDashBoardLayout = () => {
  const { userToken } = useAuth();

  return (
    <>
      <PassengerHeader />
      <main>
        <Outlet />
      </main>
      <Footer />
    </>
  );
};

export default PassengerDashBoardLayout;
