package br.ifsp.demo.controller.rideSolicitation;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.security.auth.AuthenticationInfoService;
import br.ifsp.demo.usecase.ride_solicitation.CreateRideSolicitationUseCase;
import br.ifsp.demo.usecase.ride_solicitation.GetRideSolicitationUseCase;
import br.ifsp.demo.usecase.ride_solicitation.ManageRideSolicitationUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ride-solicitations")
@RequiredArgsConstructor
public class RideSolicitationController {

    private final CreateRideSolicitationUseCase createRideSolicitationUseCase;
    private final GetRideSolicitationUseCase getRideSolicitationUseCase;
    private final ManageRideSolicitationUseCase manageRideSolicitationUseCase;
    private final PassengerRepository passengerRepository;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final AuthenticationInfoService authenticationInfoService;

    @PostMapping
    public ResponseEntity<RideSolicitation> createSolicitation(@RequestParam UUID rideId) {
        UUID passengerId = authenticationInfoService.getAuthenticatedUserId();

        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new EntityNotFoundException("Passenger with id:" + passengerId + " not found"));
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new EntityNotFoundException("Ride with id:" + rideId + " not found"));

        RideSolicitation solicitation = createRideSolicitationUseCase.createAndRegisterRideSolicitationFor(passenger, ride);
        return ResponseEntity.ok(solicitation);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RideSolicitation>> getPendingSolicitations() {
        UUID driverId = authenticationInfoService.getAuthenticatedUserId();
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Driver with id:" + driverId + " not found"));

        List<RideSolicitation> pendingSolicitations = getRideSolicitationUseCase.getPendingSolicitationsFrom(driver);
        return ResponseEntity.ok(pendingSolicitations);
    }

    @PostMapping("/accept")
    public ResponseEntity<RideSolicitation> acceptSolicitation(@PathVariable UUID solicitationId) {
        UUID driverId = authenticationInfoService.getAuthenticatedUserId();
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Driver with id:" + driverId + " not found"));

        RideSolicitation accepted = manageRideSolicitationUseCase.acceptSolicitationFor(solicitationId, driver);
        return ResponseEntity.ok(accepted);
    }

    @PostMapping("/reject")
    public ResponseEntity<RideSolicitation> rejectSolicitation(@PathVariable UUID solicitationId) {
        UUID driverId = authenticationInfoService.getAuthenticatedUserId();
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Driver with id:" + driverId + " not found"));

        RideSolicitation rejected = manageRideSolicitationUseCase.rejectSolicitationFor(solicitationId, driver);
        return ResponseEntity.ok(rejected);
    }
}
