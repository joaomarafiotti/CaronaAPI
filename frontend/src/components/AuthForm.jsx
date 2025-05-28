import React from 'react';

function AuthForm({ title, fields, buttonText, onSubmit, children }) {
    const [formData, setFormData] = React.useState(() => {
        const initialData = {};
        fields.forEach(field => initialData[field.name] = field.initialValue || '');
        return initialData;
    });

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prev => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(formData);
    };

    return (
        <div className="auth-container">
            <form onSubmit={handleSubmit} className="auth-form">
                <h2>{title}</h2>
                {fields.map(field => (
                    <div key={field.name} className="form-group">
                        <label htmlFor={field.name}>{field.label}</label>
                        <input
                            type={field.type}
                            id={field.name}
                            name={field.name}
                            value={formData[field.name]}
                            onChange={handleChange}
                            placeholder={field.placeholder || ''}
                            required={field.required !== false}
                            pattern={field.pattern}
                            title={field.title}
                        />
                    </div>
                ))}
                <button type="submit" className="auth-button">{buttonText}</button>
                {children}
            </form>
        </div>
    );
}

export default AuthForm;
