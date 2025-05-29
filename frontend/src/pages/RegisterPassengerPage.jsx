import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthForm from '../components/AuthForm';
import api from '../services/api'

function RegisterPassengerPage() {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    
    const handleRegister = async (formData) => {
        console.log('RegisterPassengerPage: handleRegister', formData);
        if (loading) return;
        setLoading(true);
        try {
            await api.post('/api/v1/register', formData);
            alert('Cadastro realizado com sucesso!');
            navigate('/login');
        } catch (error) {
            let msg = 'Erro ao registrar usuário. Tente novamente.';
            if (error?.response?.data?.message) msg = error.response.data.message;
            else if (error?.request?.response) {
                try { 
                    msg = JSON.parse(error.request.response).message; 
                } catch(error) {
                    console.error('Error parsing error response:', error);
                }
            }
            alert(msg);
        } finally {
            setLoading(false);
        }
    };

    const fields = [
        { name: 'name', label: 'Nome', type: 'text', placeholder: 'Seu nome' },
        { name: 'lastname', label: 'Sobrenome', type: 'text', placeholder: 'Seu sobrenome' },
        { name: 'email', label: 'Email', type: 'email', placeholder: 'seu@email.com' },
        { name: 'cpf', label: 'CPF', type: 'text', placeholder: '000.000.000-00', title: 'Formato: 000.000.000-00' },
        { name: 'birthDate', label: 'Data de Nascimento', type: 'date' },
        { name: 'password', label: 'Senha', type: 'password', placeholder: 'Crie uma senha forte' },
        { name: 'confirmPassword', label: 'Confirme a Senha', type: 'password', placeholder: 'Repita a senha' },
    ];

    const onRegister = async (formData) => {
        await handleRegister({...formData, role: 'PASSENGER' });
    };

    return (
        <AuthForm
            title="Cadastro de Passageiro"
            fields={fields}
            buttonText="Cadastrar"
            onSubmit={onRegister}
        >
            <div className="auth-links">
                <Link to="/login" className="link-button">Já tem uma conta? Faça login</Link>
            </div>
        </AuthForm>
    );
}

export default RegisterPassengerPage;
