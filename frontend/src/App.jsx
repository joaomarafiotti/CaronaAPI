import React, { useState } from 'react';
import LoginPage from './pages/LoginPage';
import RegisterPassengerPage from './pages/RegisterPassengerPage';
import RegisterDriverPage from './pages/RegisterDriverPage';
import './App.css';

function App() {
  const [currentPage, setCurrentPage] = useState('login');
  const [currentUser, setCurrentUser] = useState(null);

  const handleNavigation = (page) => {
    setCurrentPage(page);
  };

  const handleLogin = (userData) => {
    setCurrentUser(userData);
    setCurrentPage('dashboard');
  };

  const handleRegister = (userData) => {
    console.log('User registered:', userData);
    setCurrentUser(userData);
  };

  let pageComponent;
  if (currentPage === 'login') {
    pageComponent = <LoginPage onNavigate={handleNavigation} onLogin={handleLogin} />;
  } else if (currentPage === 'register-passenger') {
    pageComponent = <RegisterPassengerPage onNavigate={handleNavigation} onRegister={handleRegister} />;
  } else if (currentPage === 'register-driver') {
    pageComponent = <RegisterDriverPage onNavigate={handleNavigation} onRegister={handleRegister} />;
  }

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>CaronaAPI</h1>
      </header>
      <main>
        {pageComponent}
      </main>
      <footer className="app-footer">
        <p>&copy; 2024 CaronaAPI. Todos os direitos reservados.</p>
      </footer>
    </div>
  );
}

export default App;
