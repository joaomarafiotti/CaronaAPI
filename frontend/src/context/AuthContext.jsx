import React, { createContext, useContext, useEffect, useState } from 'react';
import { isValidToken } from '../services/authService';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [userToken, setUserToken] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            validateToken(token);
        } else {
            setLoading(false);
        }
    }, []);

    const validateToken = async (token) => {
        try {
            await isValidToken(token);
            setUserToken(token);
        } catch (error) {
            setUserToken(null);
            localStorage.removeItem('token');
        } finally {
            setLoading(false);
        }
    };

    const login = (token) => {
        setUserToken(token);
        localStorage.setItem('token', token);
    };

    const logout = () => {
        setUserToken(null);
        localStorage.removeItem('token');
    };

    if (loading) return <div>Carregando autenticação...</div>;

    return (
        <AuthContext.Provider value={{ userToken, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
