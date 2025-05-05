package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.utils.RideStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetRideUseCase {
    private final RideRepository rideRepository;

    public List<RideResponseModel> availableOnes() {
        List<Ride> rides = rideRepository.findAll();

        if (rides.isEmpty()) return List.of();

        return rides.stream()
                .filter(r -> r.getRideStatus().equals(RideStatus.WAITING) || r.getRideStatus().equals(RideStatus.FULL))
                .map(r -> new RideResponseModel(r.getDepartureTime(), r.getRideStatus(), r.getDriver(), r.getCar()))
                .toList();
    }

    public RideResponseModel byId(UUID rideUUID) {
        Ride ride = rideRepository
                .findById(rideUUID)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        return new RideResponseModel(ride.getDepartureTime(), ride.getRideStatus(), ride.getDriver(), ride.getCar());
    }
}
