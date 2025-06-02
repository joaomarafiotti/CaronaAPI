package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.exception.CarInUseException;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.utils.RideStatus;
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

        Car car = driver.getCars().stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new CarNotFoundException("driver is not the owner of car with id: " + carId));

        boolean carIsInUse = rideRepository.findRideByDriver_Id(driverId).stream()
                .filter(ride ->
                        ride.getRideStatus().equals(RideStatus.WAITING) ||
                        ride.getRideStatus().equals(RideStatus.FULL) ||
                        ride.getRideStatus().equals(RideStatus.STARTED))
                .filter(ride -> ride.getCar() != null)
                .anyMatch(ride ->
                        ride.getCar().getIsActive() && ride.getCar().getId().equals(carId)
                );

        if (carIsInUse) {
            throw new CarInUseException(carId);
        }

        car.deactivate();
        carRepository.save(car);
    }


}
