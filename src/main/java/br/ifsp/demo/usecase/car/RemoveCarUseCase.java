package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RemoveCarUseCase {
    private DriverRepository driverRepository;
    private CarRepository carRepository;
    public void execute(UUID driverId, UUID carId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));

        boolean carExists = driver.getCars().stream()
                .anyMatch(car -> car.getId().equals(carId));

        if (!carExists) {
            throw new CarNotFoundException(carId);
        }

        driver.removeCarById(carId);
        driverRepository.save(driver);
        carRepository.deleteById(carId);
    }
}
