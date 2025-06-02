import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerCar } from '../services/carService';
import { useAuth } from '../context/AuthContext';

const initialState = {
    brand: '',
    model: '',
    color: '',
    seats: '',
    licensePlate: '',
};

const validate = (fields) => {
    const errors = {};
    if (!fields.brand.trim()) errors.brand = 'Brand is required.';
    else if (fields.brand.length > 16) errors.brand = 'Brand must be at most 16 characters.';
    if (!fields.model.trim()) errors.model = 'Model is required.';
    else if (fields.model.length > 16) errors.model = 'Model must be at most 16 characters.';
    if (!fields.color.trim()) errors.color = 'Color is required.';
    else if (fields.color.length > 16) errors.color = 'Color must be at most 16 characters.';
    if (!fields.seats || isNaN(fields.seats) || Number(fields.seats) < 1) errors.seats = 'Seats must be a positive number.';
    else if (Number(fields.seats) > 7) errors.seats = 'Seats must be at most 7.';
    if (!fields.licensePlate.trim()) errors.licensePlate = 'License plate is required.';
    // Simple license plate validation (Brazilian format: ABC-1234 or AAA1A11)
    if (fields.licensePlate && !/^([A-Z]{3}-?\d{4}|[A-Z]{3}\d[A-Z]\d{2})$/i.test(fields.licensePlate)) {
        errors.licensePlate = 'Invalid license plate format. (Brazilian format: ABC-1234 or AAA1A11)';
    }
    return errors;
};

const RegisterCarPage = () => {
    const navigate = useNavigate();
    const [fields, setFields] = useState(initialState);
    const [errors, setErrors] = useState({});
    const [submitted, setSubmitted] = useState(false);
    const [responseError, setResponseError] = useState('');
    const { userToken } = useAuth();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFields((prev) => ({ ...prev, [name]: value }));
    };

    useEffect(() => {
        if (fields.seats < 2) setFields((prev) => ({ ...prev, seats: 2 }));
        if (fields.seats > 7) setFields((prev) => ({ ...prev, seats: 7 }));
    }, [fields.seats]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const validation = validate(fields);
        setErrors(validation);
        setSubmitted(true);

        if (Object.keys(validation).length === 0) {
            try {
                let response = await registerCar(fields, userToken);
                navigate('/dashboard/driver/cars/view');
            } catch (error) {
                console.log('Error registering car:', error);
                setResponseError('Failed to register car.', error.message);
            }
            setFields(initialState);
            setSubmitted(false);
        }
    };

    return (
        <div className="auth-container">
            <form className="auth-form" onSubmit={handleSubmit} noValidate>
                <h2>Register Car</h2>
                <div className="form-group">
                    <input
                        type="text"
                        name="brand"
                        id="brand"
                        value={fields.brand}
                        onChange={handleChange}
                        placeholder=" "
                        required
                        maxLength={16}
                    />
                    <label htmlFor="brand">Brand</label>
                    {submitted && errors.brand && <span className="form-error">{errors.brand}</span>}
                </div>
                <div className="form-group">
                    <input
                        type="text"
                        name="model"
                        id="model"
                        value={fields.model}
                        onChange={handleChange}
                        placeholder=" "
                        required
                        maxLength={16}
                    />
                    <label htmlFor="model">Model</label>
                    {submitted && errors.model && <span className="form-error">{errors.model}</span>}
                </div>
                <div className="form-group">
                    <input
                        type="text"
                        name="color"
                        id="color"
                        value={fields.color}
                        onChange={handleChange}
                        placeholder=" "
                        required
                        maxLength={16}
                    />
                    <label htmlFor="color">Color</label>
                    {submitted && errors.color && <span className="form-error">{errors.color}</span>}
                </div>
                <div className="form-group">
                    <input
                        type="number"
                        name="seats"
                        id="seats"
                        value={fields.seats}
                        onChange={handleChange}
                        placeholder=" "
                        min="2"
                        max="7"
                        required
                    />
                    <label htmlFor="seats">Seats</label>
                    {submitted && errors.seats && <span className="form-error">{errors.seats}</span>}
                </div>
                <div className="form-group">
                    <input
                        type="text"
                        name="licensePlate"
                        id="licensePlate"
                        value={fields.licensePlate}
                        onChange={handleChange}
                        placeholder=" "
                        required
                        maxLength={8}
                    />
                    <label htmlFor="licensePlate">License Plate</label>
                    {submitted && errors.licensePlate && <span className="form-error">{errors.licensePlate}</span>}
                </div>
                <button type="submit" className="auth-button">Register</button>
                {responseError && (
                    <div className="form-error" style={{ marginTop: 8, borderRadius: 6, fontSize: 15, color: '#c53030', background: '#fff5f5', padding: '8px 12px' }}>
                        ❌ {responseError}
                    </div>
                )}
            </form>
        </div>
    );
};

export default RegisterCarPage;
