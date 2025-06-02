package br.ifsp.demo.usecase.passenger;

import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.repositories.RideRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AbandonRideUseCase {
    private final RideRepository rideRepository;


    public AbandonRideUseCase(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public RideResponseModel abandonFor(UUID passengerId, UUID rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new EntityNotFoundException("Ride with id " + rideId + " not found"));
        ride.removePassenger(passengerId);
        rideRepository.save(ride);
        return ride.toResponseModel();
    }
}
