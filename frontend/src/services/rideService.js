import api from './api';

export async function registerRide(ride, token) {
    return api.post('/api/v1/ride', ride, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
    });
}

export async function getDriverRides(token) {
    return api.get('/api/v1/rides', {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
    });
}
