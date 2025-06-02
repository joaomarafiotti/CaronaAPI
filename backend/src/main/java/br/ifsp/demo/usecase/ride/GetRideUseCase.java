package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import br.ifsp.demo.utils.RideSolicitationStatus;
import br.ifsp.demo.utils.RideStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetRideUseCase {
    private final RideRepository rideRepository;
    private final RideSolicitationRepository rideSolicitationRepository;

    public List<RideResponseModel> availableOnes(UUID passengerId) {
        List<Ride> rides = rideRepository.findAll();
        List<RideSolicitation> passengerSolicitations =
                rideSolicitationRepository.findRideSolicitationsByPassenger_Id(passengerId);


        if (rides.isEmpty()) return List.of();

        return rides.stream()
                .filter(r -> r.getRideStatus().equals(RideStatus.WAITING) || r.getRideStatus().equals(RideStatus.FULL))
                .filter(r -> r.getPassengers().stream().noneMatch(p -> p.getId().equals(passengerId)))
                .filter(r -> passengerSolicitations
                        .stream()
                        .filter(s -> s.getStatus().equals(RideSolicitationStatus.WAITING))
                        .noneMatch(s -> s.getRide().getId().equals(r.getId())))
                .map(Ride::toResponseModel)
                .toList();
    }

    public RideResponseModel byId(UUID rideUUID) {
        Ride r = rideRepository
                .findById(rideUUID)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        return r.toResponseModel();
    }

    public List<RideResponseModel> byPassengerId(UUID passengerId) {
        List<Ride> rides = rideRepository.findRideByPassengers_Id(passengerId);
        return rides.stream().map(Ride::toResponseModel).toList();
    }

    public List<RideResponseModel> byDriverId(UUID driverId) {
        List<Ride> rides = rideRepository.findRideByDriver_Id(driverId);
        return rides.stream().map(Ride::toResponseModel).toList();
    }
}
