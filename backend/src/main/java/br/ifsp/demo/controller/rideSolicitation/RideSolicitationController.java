package br.ifsp.demo.controller.rideSolicitation;

import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.models.response.RideSolicitationResponseModel;
import br.ifsp.demo.security.auth.UserAuthorizationVerifier;
import br.ifsp.demo.security.user.Role;
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
    private final UserAuthorizationVerifier verifier;

    @PostMapping
    public ResponseEntity<RideSolicitationResponseModel> createSolicitation(@RequestParam UUID rideId) {
        UUID passengerId = verifier.verifyAndReturnUuidOf(Role.PASSENGER);

        RideSolicitationResponseModel response = createRideSolicitationUseCase.createAndRegisterRideSolicitationFor(passengerId, rideId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("driver/pending")
    public ResponseEntity<List<RideSolicitationResponseModel>> getPendingSolicitationsOfDriver() {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);

        List<RideSolicitationResponseModel> pendingSolicitations = getRideSolicitationUseCase.getPendingSolicitationsFromDriver(driverId);
        return ResponseEntity.ok(pendingSolicitations);
    }

    @GetMapping("passenger/pending")
    public ResponseEntity<List<RideSolicitationResponseModel>> getPendingSolicitationsOfPassenger() {
        UUID passengerId = verifier.verifyAndReturnUuidOf(Role.PASSENGER);

        List<RideSolicitationResponseModel> pendingSolicitations = getRideSolicitationUseCase.getPendingSolicitationsFromPassenger(passengerId);
        return ResponseEntity.ok(pendingSolicitations);
    }

    @PostMapping("{solicitationId}/accept")
    public ResponseEntity<RideSolicitationResponseModel> acceptSolicitation(@PathVariable UUID solicitationId) {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);

        RideSolicitationResponseModel accepted = manageRideSolicitationUseCase.acceptSolicitationFor(solicitationId, driverId);
        return ResponseEntity.ok(accepted);
    }

    @PostMapping("{solicitationId}/reject")
    public ResponseEntity<RideSolicitationResponseModel> rejectSolicitation(@PathVariable UUID solicitationId) {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);

        RideSolicitationResponseModel rejected = manageRideSolicitationUseCase.rejectSolicitationFor(solicitationId, driverId);
        return ResponseEntity.ok(rejected);
    }
}
