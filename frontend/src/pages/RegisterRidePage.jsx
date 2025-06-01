import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getDriverCars } from '../services/carService';
import { registerRide } from '../services/rideService';

const RegisterRidePage = () => {
    const { userToken } = useAuth();
    const navigate = useNavigate();
    const [startAddress, setStartAddress] = useState('');
    const [endAddress, setEndAddress] = useState('');
    const [departureTime, setDepartureTime] = useState('');
    const [cars, setCars] = useState([]);
    const [selectedCar, setSelectedCar] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        getDriverCars(userToken)
            .then(response => {
                setCars(response.data);
            })
            .catch(error => {
                setError('Error fetching cars');
            });
    }, [userToken]);

    const validate = () => {
        if (!startAddress.trim() || !endAddress.trim() || !departureTime || !selectedCar) {
            setError('Please fill in all fields.');
            return false;
        }
        if (new Date(departureTime) < new Date()) {
            setError('Departure time must be in the future.');
            return false;
        }
        setError('');
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSuccess(false);
        if (!validate()) return;
        setLoading(true);
        try {
            await registerRide({
                startAddress,
                endAddress,
                departureTime,
                carId: selectedCar
            }, userToken);
            setSuccess(true);
        } catch (err) {
            setError('Erro ao registrar uma carona');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-container">
            {cars.length === 0 ? (
                <div className='auth-form' style={{ textAlign: 'center', padding: '32px 16px' }}>
                    <div style={{ marginBottom: 16, fontSize: 17, fontWeight: 500 }}>
                        <p>É necessário ter ao menos um carro cadastrado para registrar uma nova carona</p>
                    </div>
                    <button
                        className="auth-button"
                        style={{ padding: '10px 24px', fontSize: 16 }}
                        onClick={() => navigate('/dashboard/driver/cars/register')}
                        type="button"
                    >
                        Registrar Carro
                    </button>
                </div> 
            ) : (
                <form className="auth-form" onSubmit={handleSubmit}>
                    <h2>Register Ride</h2>
                    <div className="form-group">
                        <p style={{ marginBottom: 4, fontWeight: 500 }}>Start Address</p>
                        <input
                            type="text"
                            id="startAddress"
                            value={startAddress}
                            onChange={e => setStartAddress(e.target.value)}
                            placeholder="Street, Number, Neighborhood, City"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <p style={{ marginBottom: 4, fontWeight: 500 }}>End Address</p>
                        <input
                            type="text"
                            id="endAddress"
                            value={endAddress}
                            onChange={e => setEndAddress(e.target.value)}
                            placeholder="Street, Number, Neighborhood, City"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <p style={{ marginBottom: 4, fontWeight: 500 }}>Departure Date and Time</p>
                        <input
                            type="datetime-local"
                            id="departureTime"
                            value={departureTime}
                            onChange={e => setDepartureTime(e.target.value)}
                            required
                            min={new Date().toISOString().slice(0, 16)}
                        />
                    </div>
                    <div className="form-group">
                        <p style={{ marginBottom: 4, fontWeight: 500 }}>Select a Car</p>
                        <select
                            id="carSelect"
                            value={selectedCar}
                            onChange={e => setSelectedCar(e.target.value)}
                            required
                        >
                            <option value="" disabled>--------</option>
                            {cars.map(car => (
                                <option key={car.id} value={car.id}>
                                    {car.brand} {car.model} ({car.licensePlate})
                                </option>
                            ))}
                        </select>
                    </div>
                    {error && (
                        <div className="form-error" style={{ marginTop: 8, borderRadius: 6, fontSize: 15, color: '#c53030', background: '#fff5f5', padding: '8px 12px' }}>
                            ❌ {error}
                        </div>
                    )}
                    {success && (
                        <div className="form-success" style={{ marginTop: 8, borderRadius: 6, fontSize: 15, color: '#276749', background: '#f0fff4', padding: '8px 12px' }}>
                            ✅ Ride registered successfully!
                        </div>
                    )}
                    <button type="submit" className="auth-button" disabled={loading}>
                        {loading ? 'Registering...' : 'Register Ride'}
                    </button>
                </form>
            )}
        </div>
    );
};

export default RegisterRidePage;
