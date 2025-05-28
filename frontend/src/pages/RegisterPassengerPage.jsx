import React from 'react';
import { Link } from 'react-router-dom'; 
import AuthForm from '../components/AuthForm';

function RegisterPassengerPage({ onRegister }) { 
    const fields = [
        { name: 'name', label: 'Nome', type: 'text', placeholder: 'Seu nome' },
        { name: 'lastname', label: 'Sobrenome', type: 'text', placeholder: 'Seu sobrenome' },
        { name: 'email', label: 'Email', type: 'email', placeholder: 'seu@email.com' },
        { name: 'cpf', label: 'CPF', type: 'text', placeholder: '000.000.000-00',  title: 'Formato: 000.000.000-00' },
        { name: 'birthDate', label: 'Data de Nascimento', type: 'date' },
        { name: 'password', label: 'Senha', type: 'password', placeholder: 'Crie uma senha forte' },
        { name: 'confirmPassword', label: 'Confirme a Senha', type: 'password', placeholder: 'Repita a senha' },
    ];

    const handleRegister = (formData) => {
        if (formData.password !== formData.confirmPassword) {
            alert('As senhas não coincidem!');
            return;
        }
        const { confirmPassword, ...registrationData } = formData;
        onRegister({ ...registrationData, role: 'PASSENGER' });
    };

    return (
        <AuthForm
            title="Cadastro de Passageiro"
            fields={fields}
            buttonText="Cadastrar"
            onSubmit={handleRegister}
        >
            <div className="auth-links">
                <Link to="/login" className="link-button">Já tem uma conta? Faça login</Link>
            </div>
        </AuthForm>
    );
}

export default RegisterPassengerPage;
