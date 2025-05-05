package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.exception.RideNotFoundException;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.service.NotificationService;
import br.ifsp.demo.utils.RideStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CancelRideUseCase {
    private RideRepository rideRepository;
    private NotificationService notificationService;

    public void execute(UUID rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException(rideId));

        if (ride.getDepartureTime().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot cancel ride in current state");
        }

        if (ride.getRideStatus() != RideStatus.WAITING) {
            throw new IllegalStateException("Ride cannot be cancelled with status " + ride.getRideStatus());
        }

        ride.setRideStatus(RideStatus.CANCELED);
        rideRepository.save(ride);

        notificationService.notifyPassengers(
                ride.getPassengers(),
                "Ride cancelled",
                "The ride from " + ride.getStartAddress() + " to " + ride.getEndAddress()
                        + "has been cancelled"
        );
    }
}
