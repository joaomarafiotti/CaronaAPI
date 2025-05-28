package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.models.response.CarResponseModel;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetCarUseCase {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;

    public CarResponseModel byId(UUID driverId, UUID carId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));

        if (!driver.getCars().contains(car)) {
            throw new CarNotFoundException(carId);
        }

        return car.toResponseModel();
    }

    public List<CarResponseModel> allCars(UUID driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));

        return driver.getCars().stream()
                .map(Car::toResponseModel)
                .toList();
    }
}
