package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.utils.RideSolicitationStatus;

import java.util.List;

public class GetRideSolicitationUseCase {

    public List<RideSolicitation> getPendingSolicitationsFrom(Driver driver) {
        System.out.println(driver.getRideSolicitations());
        return driver
                .getRideSolicitations()
                .stream()
                .filter(s -> s.getStatus().equals(RideSolicitationStatus.WAITING))
                .toList();
    }
}
