import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [userToken, setUserToken] = useState(() => localStorage.getItem('token'));

    const login = (token) => {
        setUserToken(token);
        localStorage.setItem('token', token);
    };

    const logout = () => {
        setUserToken(null);
        localStorage.removeItem('token');
    };

    return (
        <AuthContext.Provider value={{ userToken, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
