import { useState, useEffect } from 'react';
import api from '../services/api';
import { useAuth } from '../context/AuthContext';
import Car from '../components/Car';
import { Button, Spinner } from "@chakra-ui/react"

const ViewCarPage = () => {
    const { userToken } = useAuth();
    const [cars, setCars] = useState(null);
    
    useEffect(() => {
        const fetchCars = async () => {
            try {
                const response = await api.get('/api/v1/drivers/cars', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${userToken}`,
                    }
                });
                setCars(response.data);
                console.log('Cars fetched successfully:', response.data);
            } catch (error) {
                console.error('Error fetching car details:', error);
            }
        };
        fetchCars();
    }, []);

    return (
        <div className="view-car-page">
            { cars ? (
                cars.map((car, index) => (
                    <div key={index} className="car-item">
                        <Car stats={{...car, index}} cardButton={
                            <Button variant="outline" colorScheme="blue">
                                Excluir
                            </Button>
                        }/>
                    </div>
                ))
            ) : (
                <Spinner
                    color="red.500"
                    css={{ "--spinner-track-color": "colors.gray.200" }}
                />
            )}
        </div>
    );
}
export default ViewCarPage;