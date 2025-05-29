import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

/**
 * Componente de proteção de rota.
 * @param {React.ReactNode} children - O conteúdo da rota protegida.
 */
const ProtectedRoute = ({ children }) => {
    const { userToken } = useAuth();
    if (!userToken) return <Navigate to="/login" replace />;
    return children;
};

export default ProtectedRoute;
