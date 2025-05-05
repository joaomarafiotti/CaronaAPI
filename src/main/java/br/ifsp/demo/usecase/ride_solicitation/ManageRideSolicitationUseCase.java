package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.RideSolicitation;

import java.util.UUID;

public class ManageRideSolicitationUseCase {

    public RideSolicitation acceptSolicitationFor(UUID solicitationId, Driver driver) {
        return driver.accept(solicitationId);
    }

    public RideSolicitation rejectSolicitationFor(UUID solicitationId, Driver driver) {
        return driver.reject(solicitationId);
    }
}
