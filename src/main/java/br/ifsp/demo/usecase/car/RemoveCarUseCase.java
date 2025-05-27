package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.exception.CarInUseException;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RemoveCarUseCase {

    private DriverRepository driverRepository;
    private CarRepository carRepository;
    private RideRepository rideRepository;

    public void execute(UUID driverId, UUID carId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));

        boolean carExists = driver.getCars().stream()
                .anyMatch(car -> car.getId().equals(carId));

        if (!carExists) {
            throw new CarNotFoundException("driver is not the owner of car with id: " + carId);
        }

        boolean carIsInUse = rideRepository.findRideByDriver_Id(driverId).stream()
                .anyMatch(ride -> {
                            if (ride.getCar() == null)
                                throw new IllegalStateException("ride with a null car");
                            return ride.getCar().getId().equals(carId);
                        }
                );

        if (carIsInUse) {
            throw new CarInUseException(carId);
        }

        driver.removeCarById(carId);
        driverRepository.save(driver);
        carRepository.deleteById(carId);
    }


}
