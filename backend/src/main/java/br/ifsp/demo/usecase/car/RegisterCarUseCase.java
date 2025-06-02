package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.LicensePlate;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.exception.LicensePlateAlreadyRegisteredException;
import br.ifsp.demo.models.request.CarRequestModel;
import br.ifsp.demo.models.response.CreateCarResponseModel;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterCarUseCase {

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    public CreateCarResponseModel execute(CarRequestModel request, UUID driverId){

        if (request == null) {
            throw new IllegalArgumentException();
        }

        Driver driver = isDriverRegistered(driverId);

        String plate = request.licensePlate().trim().toUpperCase();

        carRepository.findByLicensePlate(plate).ifPresent(car -> {
            throw new LicensePlateAlreadyRegisteredException(plate);
        });

        Car car = new Car(request.brand(),
                request.model(),
                request.color(),
                request.seats(),
                LicensePlate.parse(request.licensePlate())
        );

        driver.addCar(car);
        driverRepository.save(driver);

        return new CreateCarResponseModel(car.getId());
    }

    private Driver isDriverRegistered(UUID driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));
    }
}
