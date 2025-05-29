import React, { useState } from "react";
import { Routes, Route, useNavigate, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPassengerPage from "./pages/RegisterPassengerPage";
import RegisterDriverPage from "./pages/RegisterDriverPage";
import "./App.css";
import { AuthProvider } from "./context/AuthContext";
import DashBoardLayout from "./pages/dashboard/layout/DashBoardLayout";
import DriverDashBoard from "./pages/dashboard/DriverDashBoard";
import PassengerDashBoard from "./pages/dashboard/PassengerDashBoard";

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/dashboard" element={<DashBoardLayout />}>
          <Route index element={<DriverDashBoard />} />
          <Route path="driver" element={<DriverDashBoard />} />
          <Route path="passenger" element={<PassengerDashBoard />} />
        </Route>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register-passenger" element={<RegisterPassengerPage />} />
        <Route path="/register-driver" element={<RegisterDriverPage />} />
        <Route path="*" element={<Navigate to="/login" replace={true} />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
