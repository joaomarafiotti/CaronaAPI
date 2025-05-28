import React, { useState } from 'react';
import { Routes, Route, useNavigate, Navigate } from 'react-router-dom'; // Import necessary components
import LoginPage from './pages/LoginPage';
import RegisterPassengerPage from './pages/RegisterPassengerPage';
import RegisterDriverPage from './pages/RegisterDriverPage';
import axios from 'axios';
import './App.css';

function Dashboard({ currentUser, onLogout }) {
  if (!currentUser) {
    return <Navigate to="/login" />;
  }
  return (
    <div className="dashboard-container">
      <h1>Bem-vindo ao Dashboard, {currentUser.name}!</h1>
      <p>Seu email: {currentUser.email}</p>
      <p>Tipo de usuário: {currentUser.role === 'PASSENGER' ? 'Passageiro' : 'Motorista'}</p>
      <button onClick={onLogout} className="auth-button">Logout</button>
    </div>
  );
}

function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = (userData) => {
    const role = userData.email.includes('driver') ? 'DRIVER' : 'PASSENGER'; 
    setCurrentUser({ ...userData, name: userData.email.split('@')[0], role }); 
    navigate('/dashboard');
  };

  const handleRegister = async (userData) => {
    if (loading) return; 
    console.log('Registering user:');
    setLoading(true);
    try {
      let response = await axios({
          method: 'POST',
          url: 'http://localhost:8080/api/v1/register',
          data: userData,
      })
      navigate('/login');
      setLoading(false);
    } catch (error) {
      let errorMessage = JSON.parse(error.request.response);
      console.log(errorMessage.message);
      alert(errorMessage.message || 'Erro ao registrar usuário. Tente novamente.');
      setLoading(false);
    }
  };

  const handleLogout = () => {
    setCurrentUser(null);
    navigate('/login');
  };

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Carona App</h1>
      </header>
      <main>
        <Routes>
          <Route path="/login" element={<LoginPage onLogin={handleLogin} />} />
          <Route path="/register-passenger" element={<RegisterPassengerPage onRegister={handleRegister} />} />
          <Route path="/register-driver" element={<RegisterDriverPage onRegister={handleRegister} />} />
          <Route
            path="/dashboard"
            element={
              currentUser ? (
                <Dashboard currentUser={currentUser} onLogout={handleLogout} />
              ) : (
                <Navigate to="/login" replace />
              )
            }
          />
          <Route path="*" element={<Navigate to={currentUser ? "/dashboard" : "/login"} replace />} />
        </Routes>
      </main>
      <footer className="app-footer">
        <p>&copy; 2024 Carona App. Todos os direitos reservados.</p>
      </footer>
    </div>
  );
}

export default App;
