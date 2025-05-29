import React, { useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const { logout, userToken } = useAuth();
    const navigate = useNavigate();
    
    const handleLogout = () => {
        logout();
        navigate('/login');
    };
    
    return (
        <div className="dashboard-container">
            <h1>Bem-vindo ao Dashboard!</h1>
            <button onClick={handleLogout} className="auth-button">Logout</button>
        </div>
    );
};

export default Dashboard;
