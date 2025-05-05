package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.repositories.RideRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManageRideSolicitationUseCase {
    RideRepository rideRepository;


    public RideSolicitation acceptSolicitationFor(UUID solicitationId, Driver driver) {
        RideSolicitation acceptedSolicitation = driver.accept(solicitationId);
        acceptedSolicitation.getRide().addPassenger(acceptedSolicitation.getPassenger());

        return acceptedSolicitation;
    }

    public RideSolicitation rejectSolicitationFor(UUID solicitationId, Driver driver) {
        return driver.reject(solicitationId);
    }
}
