import { useState, useEffect } from 'react';
import { Button, Spinner } from "@chakra-ui/react";

import { getDriverCars, deleteCar } from '../services/carService';
import { useAuth } from '../context/AuthContext';
import Car from '../components/Car';

const ViewCarPage = () => {
    const { userToken } = useAuth();
    const [cars, setCars] = useState(null);

    const deleteCarHandler = async (carId) => {
        try {
            await deleteCar(carId, userToken);
            setCars(prevCars => prevCars.filter(c => c.id !== carId));
            alert('Carro excluído com sucesso!');
        } catch (error) {
            console.error('Erro ao excluir carro:', error);
            alert('Erro ao excluir carro. Tente novamente.');
        }
    };

    useEffect(() => {
        const fetchCars = async () => {
            try {
                const response = await getDriverCars(userToken);
                setCars(response.data);
                console.log('Cars fetched successfully:', response.data);
            } catch (error) {
                console.error('Erro ao buscar carros:', error);
            }
        };
        if (userToken) {
            fetchCars();
        }
    }, [userToken]);

    return (
        <div className="view-car-page">
            {cars ? (
                cars.map((car, index) => (
                    <div key={car.id} className="car-item">
                        <Car
                            stats={{...car, index}}
                            cardButton={
                                <Button
                                    variant="outline"
                                    colorScheme="red"
                                    onClick={() => deleteCarHandler(car.id)}
                                >
                                    Excluir
                                </Button>
                            }
                        />
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
};

export default ViewCarPage;