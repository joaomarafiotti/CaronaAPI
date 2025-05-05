package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.exception.DriverNotFoundException;
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

    private DriverRepository driverRepository;
    private CarRepository carRepository;

    public CreateCarResponseModel execute(@Valid CarRequestModel request){

        if (request == null) {
            throw new IllegalArgumentException();
        }

        Driver driver = driverRepository.findById(request.driverId())
                .orElseThrow(() -> new DriverNotFoundException(request.driverId()));


        Car car = new Car(request.brand(),
                request.model(),
                request.color(),
                request.seats(),
                request.licensePlate()
        );

        driver.addCar(car);
        driverRepository.save(driver);

        carRepository.save(car);

        return new CreateCarResponseModel(car.getId());
    }

    private Driver isDriverRegistered(UUID driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));
    }
}
