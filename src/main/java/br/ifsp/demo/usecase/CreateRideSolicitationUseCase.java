package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.exception.EntityAlreadyExistsException;
import br.ifsp.demo.repositories.RideSolicitationRepository;

import java.util.List;
import java.util.Optional;

public class CreateRideSolicitationUseCase {
    private final RideSolicitationRepository repository;

    public CreateRideSolicitationUseCase(RideSolicitationRepository repository) {
        this.repository = repository;
    }

    public RideSolicitation createAndRegisterRideSolicitationFor(Passenger passenger, Ride ride) {
        RideSolicitation solicitation = new RideSolicitation(ride, passenger);
        List<RideSolicitation> solicitations = repository.findAll();
        Optional<RideSolicitation> equalSolicitationAlreadyPersisted = solicitations
                .stream()
                .filter(s -> s.equals(solicitation))
                .findAny();

        if (equalSolicitationAlreadyPersisted.isPresent())
            throw new EntityAlreadyExistsException("Ride solicitation with the same passenger and ride already exists");

        Driver driver = ride.getDriver();
        driver.addSolicitations(solicitation);
        repository.save(solicitation);
        return solicitation;
    }
}
