package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;

public class CreateRideSolicitationUseCase {

    public RideSolicitation createAndRegisterRideSolicitationFor(Passenger passenger, Ride ride) {
        RideSolicitation solicitation = new RideSolicitation(ride, passenger);
        Driver driver = ride.getDriver();
        driver.addSolicitations(solicitation);
        return solicitation;
    }
}
