package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.models.response.RideSolicitationResponseModel;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageRideSolicitationUseCase {
    private final RideSolicitationRepository solicitationRepository;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    public RideSolicitationResponseModel acceptSolicitationFor(UUID solicitationId, UUID driverId) {
        RideSolicitation rideSolicitation = solicitationRepository
                .findById(solicitationId)
                .orElseThrow(() -> new EntityNotFoundException("Ride solicitation with id " + solicitationId + " not found"));

        Driver driver = driverRepository
                .findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id:" + driverId + " not found"));

        RideSolicitation acceptedSolicitation = driver.acceptIfIsTheOwner(rideSolicitation);

        Passenger passenger = acceptedSolicitation.getPassenger();
        Ride ride = acceptedSolicitation.getRide();

        ride.addPassenger(passenger);

        rideRepository.save(ride);
        solicitationRepository.save(acceptedSolicitation);

        return acceptedSolicitation.toResponseModel();
    }

    public RideSolicitationResponseModel rejectSolicitationFor(UUID solicitationId, UUID driverId) {
        RideSolicitation rideSolicitation = solicitationRepository
                .findById(solicitationId)
                .orElseThrow(() -> new EntityNotFoundException("Ride solicitation with id " + solicitationId + " not found"));
        Driver driver = driverRepository
                .findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id:" + driverId + " not found"));

        RideSolicitation rejectedSolicitation = driver.rejectIfIsTheOwner(rideSolicitation);

        solicitationRepository.save(rejectedSolicitation);

        return rejectedSolicitation.toResponseModel();
    }

    public RideSolicitationResponseModel cancelSolicitationFor(UUID solicitationId, UUID passengerId) {
        RideSolicitation rideSolicitation = solicitationRepository
                .findById(solicitationId)
                .orElseThrow(() -> new EntityNotFoundException("Ride solicitation with id " + solicitationId + " not found"));
        Passenger passenger = passengerRepository
                .findById(passengerId)
                .orElseThrow(() -> new EntityNotFoundException("Passenger with id:" + passengerId + " not found"));

        RideSolicitation cancelled = passenger.cancelSolicitation(rideSolicitation);

        solicitationRepository.save(cancelled);

        return cancelled.toResponseModel();
    }
}
