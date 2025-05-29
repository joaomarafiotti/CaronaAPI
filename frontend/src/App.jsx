import React, { useState } from 'react';
import { Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPassengerPage from './pages/RegisterPassengerPage';
import RegisterDriverPage from './pages/RegisterDriverPage';
import './App.css';
import { AuthProvider } from './context/AuthContext';
import { ProtectedPassengerRoute } from './components/ProtectedPassengerRoute';
import Dashboard from './pages/Dashboard';

function App() {
  return (
    <AuthProvider>
      <Routes>
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
