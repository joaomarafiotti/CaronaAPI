import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthForm from '../components/AuthForm';
import { loginUser, registerUser } from '../services/authService';
import { useAuth } from '../context/AuthContext';

function LoginPage() {
    const navigate = useNavigate();
    const { login } = useAuth();
    const [loading, setLoading] = useState(false);

    const fields = [
        { name: 'username', label: 'Email', type: 'email', placeholder: 'seu@email.com' },
        { name: 'password', label: 'Senha', type: 'password', placeholder: 'Sua senha' },
    ];

    const handleLogin = async (formData) => {
        if (loading) return;
        setLoading(true);
        try {
            const resData = await loginUser(formData);
            login(resData.token);
            navigate('/dashboard');
        } catch {
            alert('Erro ao fazer login. Usuário ou senha inválidos.');
        } finally {
            setLoading(false);
        }
    };

    const onLogin = async (formData) => {
        await handleLogin(formData);
    };

    return (
        <AuthForm
            title="Login"
            fields={fields}
            buttonText="Entrar"
            onSubmit={onLogin}
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
