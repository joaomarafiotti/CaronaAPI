package br.ifsp.demo.controller.ride;

import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.models.response.CreateRideResponseModel;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.security.auth.UserAuthorizationVerifier;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.usecase.ride.CancelRideUseCase;
import br.ifsp.demo.usecase.ride.GetRideUseCase;
import br.ifsp.demo.usecase.ride.RegisterRideUseCase;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ride")
@RequiredArgsConstructor
public class RideController {
    private final RegisterRideUseCase registerRideUseCase;
    private final CancelRideUseCase cancelRideUseCase;
    private final GetRideUseCase getRideUseCase;
    private final UserAuthorizationVerifier verifier;

    @PostMapping
    public ResponseEntity<CreateRideResponseModel> createRide(@RequestBody @Valid RideRequestModel request) {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);
        CreateRideResponseModel response = registerRideUseCase.execute(request, driverId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> cancelRide(@PathVariable @NonNull UUID rideId) {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);
        cancelRideUseCase.execute(rideId, driverId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RideResponseModel>> getAvailableRides() {
        UUID passengerId = verifier.verifyAndReturnUuidOf(Role.PASSENGER);
        List<RideResponseModel> rides = getRideUseCase.availableOnes(passengerId);
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideResponseModel> getRideById(@PathVariable @NonNull UUID rideId) {
        RideResponseModel ride = getRideUseCase.byId(rideId);
        return ResponseEntity.ok(ride);
    }

    @GetMapping("/passenger")
    public ResponseEntity<List<RideResponseModel>> getRidesByPassengerId() {
        UUID passengerId = verifier.verifyAndReturnUuidOf(Role.PASSENGER);
        List<RideResponseModel> rides = getRideUseCase.byPassengerId(passengerId);
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/driver")
    public ResponseEntity<List<RideResponseModel>> getRidesByDriverId() {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);
        List<RideResponseModel> rides = getRideUseCase.byDriverId(driverId);
        return ResponseEntity.ok(rides);
    }
}
