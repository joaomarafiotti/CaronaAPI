package br.ifsp.demo.usecase.passenger;

import br.ifsp.demo.repositories.RideRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AbandonRideUseCase {
    private final RideRepository rideRepository;


    public AbandonRideUseCase(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void abandonFor(UUID passengerId, UUID rideId) {
        rideRepository.findById(rideId).ifPresent(ride -> ride.removePassenger(passengerId));
    }
}
