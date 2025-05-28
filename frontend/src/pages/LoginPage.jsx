import React from 'react';
import AuthForm from '../components/AuthForm';

function LoginPage({ onNavigate, onLogin }) {
    const fields = [
        { name: 'email', label: 'Email', type: 'email', placeholder: 'seu@email.com' },
        { name: 'password', label: 'Senha', type: 'password', placeholder: 'Sua senha' },
    ];

    const handleLogin = (formData) => {
        console.log('Login attempt:', formData);
        // TODO: Implement actual login logic
        onLogin(formData); // Placeholder for actual login success
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
                <button onClick={() => onNavigate('register-passenger')} className="link-button">Cadastre-se como Passageiro</button>
                <button onClick={() => onNavigate('register-driver')} className="link-button">Cadastre-se como Motorista</button>
            </div>
        </AuthForm>
    );
}

export default LoginPage;
