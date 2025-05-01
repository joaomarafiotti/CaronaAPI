package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.exception.DriverNotFoundException;
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

    public Ride execute(@Valid RideRequestModel rideRequestModel) {
        validateRideRequest(rideRequestModel);

        Driver driver = findDriveOrThrow(rideRequestModel.driverId());

        Ride ride = rideRequestModel.toRide(driver);

        return rideRepository.save(ride);
    }

    private Driver findDriveOrThrow(UUID driverId){
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

        if (request.departureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Departure time must be in the future");
        }
    }
}
