package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.exception.EntityAlreadyExistsException;
import br.ifsp.demo.exception.RideSolicitationForInvalidRideException;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateRideSolicitationUseCase {
    private final RideSolicitationRepository solicitationRepository;
    private final PassengerRepository passengerRepository;
    private final RideRepository rideRepository;

    public RideSolicitation createAndRegisterRideSolicitationFor(UUID passengerId, UUID rideId) {
        if (rideId == null || passengerId == null)
            throw new IllegalArgumentException("Ride and passenger must not be null");

        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new EntityNotFoundException("Passenger with id:" + passengerId + " not found"));
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new EntityNotFoundException("Ride with id:" + rideId + " not found"));

        if (rideIsAlreadyFull(ride)) throw new RideSolicitationForInvalidRideException("Ride is already full");

        RideSolicitation solicitation = new RideSolicitation(ride, passenger);
        List<RideSolicitation> solicitations = solicitationRepository.findRideSolicitationByRide_Id(ride.getId());
        Optional<RideSolicitation> equalSolicitationAlreadyPersisted = solicitations
                .stream()
                .filter(s -> s.equals(solicitation))
                .findAny();

        if (equalSolicitationAlreadyPersisted.isPresent())
            throw new EntityAlreadyExistsException("Ride solicitation with the same passenger and ride already exists");

        solicitationRepository.save(solicitation);
        return solicitation;
    }

    private boolean rideIsAlreadyFull(Ride ride) {
        return ride.getPassengers().size() + 1 >= ride.getCar().getSeats();
    }
}
