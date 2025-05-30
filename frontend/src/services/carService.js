import api from './api';

export async function getDriverCars(token) {
    return api.get('/api/v1/drivers/cars', {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

export async function registerCar(fields, token) {
    return api.post('/api/v1/drivers/cars', fields, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
    });
}

export async function deleteCar(carId, token) {
    return api.delete(`/api/v1/drivers/cars/${carId}`, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    });
}