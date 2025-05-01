package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.dto.RideRequestDTO;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterRideUseCase {
    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;

    @Autowired
    public RegisterRideUseCase(DriverRepository driverRepository, RideRepository rideRepository) {
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
    }

    public boolean execute(RideRequestDTO rideRequestDTO) {
        Optional<Driver> driver = driverRepository.findById(rideRequestDTO.driverId());

        if (driver.isEmpty()) {
            throw new RuntimeException("Driver not found");
        }

        Ride ride = rideRequestDTO.toRide(driver.get());

        rideRepository.save(ride);

        return true;
    }
}
