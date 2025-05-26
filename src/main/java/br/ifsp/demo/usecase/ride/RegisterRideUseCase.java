package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.models.response.CreateRideResponseModel;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterRideUseCase {
    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;
    private final CarRepository carRepository;

    public CreateRideResponseModel execute(RideRequestModel rideRequestModel,UUID driverId) {
        if (rideRequestModel == null) {
            throw new IllegalArgumentException("Ride request must not be null");
        }

        validateRideRequest(rideRequestModel);

        Driver driver = findDriverOrThrow(driverId);

        Car car = findCarOrThrow(rideRequestModel.carId());

        Ride ride = new Ride(
                rideRequestModel.startAddress(),
                rideRequestModel.endAddress(),
                rideRequestModel.departureTime(),
                driver,
                car
        );

        rideRepository.save(ride);

        return new CreateRideResponseModel(ride.getId());
    }

    private Car findCarOrThrow(UUID carId){
        return carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
    }

    private Driver findDriverOrThrow(UUID driverId){
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found: " + driverId));
    }

    private void validateRideRequest(RideRequestModel request) {
        if (request.startAddress().equals(request.endAddress())) {
            throw new IllegalArgumentException("Start and end locations must be different");
        }

        if (request.departureTime().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new IllegalArgumentException("Ride must be scheduled at least 1 hour in advance");
        }
    }
}
