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
    private final RideRepository rideRepository;
    private final NotificationService notificationService;

    public void execute(UUID rideId, UUID driverId) {
        if (rideId == null || driverId == null) {
            throw new IllegalArgumentException("Ride ID and Driver ID must not be null");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException(rideId));

        if(!ride.getDriver().getId().equals(driverId)) {
            throw new IllegalArgumentException("Only the assigned driver can cancel this ride");
        }

        if (ride.getRideStatus() != RideStatus.WAITING) {
            throw new IllegalStateException("Ride cannot be cancelled with status " + ride.getRideStatus());
        }

        if (ride.getDepartureTime().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot cancel ride less than 1 hour before departure");
        }

        ride.setRideStatus(RideStatus.CANCELLED);

        rideRepository.save(ride);

        notificationService.notifyPassengers(
                ride.getPassengers(),
                "Ride cancelled",
                "The ride from " + ride.getStartAddress() + " to " + ride.getEndAddress()
                        + " has been cancelled."
        );
    }
}
