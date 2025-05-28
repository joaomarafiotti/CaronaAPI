package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import br.ifsp.demo.utils.RideSolicitationStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetRideSolicitationUseCase {
    private final RideRepository rideRepository;
    private final RideSolicitationRepository solicitationRepository;

    GetRideSolicitationUseCase(RideRepository rideRepository, RideSolicitationRepository solicitationRepository) {
        this.rideRepository = rideRepository;
        this.solicitationRepository = solicitationRepository;
    }

    public List<RideSolicitation> getPendingSolicitationsFrom(UUID driverId) {
        List<Ride> driverRides = rideRepository.findRideByDriver_Id(driverId);

        return driverRides.stream()
                .map(r -> solicitationRepository.findRideSolicitationByRide_Id(r.getId()))
                .flatMap(List::stream)
                .filter(solicitation -> solicitation.getStatus().equals(RideSolicitationStatus.WAITING))
                .toList();
    }
}
