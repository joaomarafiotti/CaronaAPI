package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.exception.EntityAlreadyExistsException;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreateRideSolicitationUseCase {
    private final RideSolicitationRepository solicitationRepository;

    public CreateRideSolicitationUseCase(RideSolicitationRepository solicitationRepository) {
        this.solicitationRepository = solicitationRepository;
    }

    public RideSolicitation createAndRegisterRideSolicitationFor(Passenger passenger, Ride ride) {
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
}
