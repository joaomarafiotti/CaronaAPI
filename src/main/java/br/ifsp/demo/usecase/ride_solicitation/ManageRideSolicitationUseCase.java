package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManageRideSolicitationUseCase {
    private final RideSolicitationRepository solicitationRepository;

    public ManageRideSolicitationUseCase(RideSolicitationRepository solicitationRepository) {
        this.solicitationRepository = solicitationRepository;
    }

    public RideSolicitation acceptSolicitationFor(UUID solicitationId, Driver driver) {
        RideSolicitation rideSolicitation = solicitationRepository
                .findById(solicitationId)
                .orElseThrow(() -> new EntityNotFoundException("Ride solicitation with id " + solicitationId + " not found"));

        RideSolicitation acceptedSolicitation = driver.acceptIfIsTheOwner(rideSolicitation);

        Passenger passenger = acceptedSolicitation.getPassenger();
        Ride ride = acceptedSolicitation.getRide();

        ride.addPassenger(passenger);

        return acceptedSolicitation;
    }

    public RideSolicitation rejectSolicitationFor(UUID solicitationId, Driver driver) {
        RideSolicitation rideSolicitation = solicitationRepository
                .findById(solicitationId)
                .orElseThrow(() -> new EntityNotFoundException("Ride solicitation with id " + solicitationId + " not found"));

        return driver.rejectIfIsTheOwner(rideSolicitation);
    }
}
