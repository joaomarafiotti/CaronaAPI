import React, { useState } from "react";
import { Routes, Route, useNavigate, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPassengerPage from "./pages/RegisterPassengerPage";
import RegisterDriverPage from "./pages/RegisterDriverPage";
import "./App.css";
import { AuthProvider } from "./context/AuthContext";
import {ProtectedDriverRoute} from "./components/ProtectedDriverRoute";
import { ProtectedPassengerRoute } from "./components/ProtectedPssengerRoute";
import Dashboard from "./pages/Dashboard";
import DashBoardLayout from "./pages/dashboard/DashBoardLayout";
import DriverDashBoard from "./pages/dashboard/DriverDashBoard";

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/dashboard" element={<DashBoardLayout />}>
          <Route path="driver" element={<DriverDashBoard />} />
          <Route path="passenger" element={<Passenger />} />
          <Route
            path="*"
            element={<Navigate to="/dashboard/driver" replace={true} />}
          />
        </Route>
        <Route path="/" element={<Driver />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register-passenger" element={<RegisterPassengerPage />} />
        <Route path="/register-driver" element={<RegisterDriverPage />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedPassengerRoute>
              <Dashboard />
            </ProtectedPassengerRoute>
          }
        />
        <Route path="*" element={<Navigate to="/login" replace={true} />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
