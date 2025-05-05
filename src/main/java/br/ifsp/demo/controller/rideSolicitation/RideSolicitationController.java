package br.ifsp.demo.controller.ride_solicitation;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.usecase.ride_solicitation.CreateRideSolicitationUseCase;
import br.ifsp.demo.usecase.ride_solicitation.GetRideSolicitationUseCase;
import br.ifsp.demo.usecase.ride_solicitation.ManageRideSolicitationUseCase;
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

    @PostMapping
    public ResponseEntity<RideSolicitation> createSolicitation(@RequestParam UUID passengerId,
                                                               @RequestParam UUID rideId) {
        RideSolicitation solicitation = createRideSolicitationUseCase.createAndRegisterRideSolicitationFor(passenger, ride);
        return ResponseEntity.ok(solicitation);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RideSolicitation>> getPendingSolicitations(@RequestParam UUID driverId) {
        List<RideSolicitation> pendingSolicitations = getRideSolicitationUseCase.getPendingSolicitationsFrom(driver);
        return ResponseEntity.ok(pendingSolicitations);
    }

    @PostMapping("/{solicitationId}/accept")
    public ResponseEntity<RideSolicitation> acceptSolicitation(@PathVariable UUID solicitationId,
                                                               @RequestParam UUID driverId) {
        RideSolicitation accepted = manageRideSolicitationUseCase.acceptSolicitationFor(solicitationId, driver);
        return ResponseEntity.ok(accepted);
    }

    @PostMapping("/{solicitationId}/reject")
    public ResponseEntity<RideSolicitation> rejectSolicitation(@PathVariable UUID solicitationId,
                                                               @RequestParam UUID driverId) {
        RideSolicitation rejected = manageRideSolicitationUseCase.rejectSolicitationFor(solicitationId, driver);
        return ResponseEntity.ok(rejected);
    }
}
