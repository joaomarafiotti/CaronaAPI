import React from 'react';
import { Link } from 'react-router-dom'; 
import AuthForm from '../components/AuthForm';

function LoginPage({ onLogin }) { 
    const fields = [
        { name: 'email', label: 'Email', type: 'email', placeholder: 'seu@email.com' },
        { name: 'password', label: 'Senha', type: 'password', placeholder: 'Sua senha' },
    ];

    const handleLogin = (formData) => {
        console.log('Login attempt:', formData);
        onLogin(formData);
    };

    return (
        <AuthForm
            title="Login"
            fields={fields}
            buttonText="Entrar"
            onSubmit={handleLogin}
        >
            <div className="auth-links">
                <p>Não tem uma conta?</p>
                <Link to="/register-passenger" className="link-button">Cadastre-se como Passageiro</Link>
                <Link to="/register-driver" className="link-button">Cadastre-se como Motorista</Link>
            </div>
        </AuthForm>
    );
}

export default LoginPage;
