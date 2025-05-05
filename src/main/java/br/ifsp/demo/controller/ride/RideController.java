package br.ifsp.demo.controller.ride;

import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.models.response.CreateRideResponseModel;
import br.ifsp.demo.models.response.RideResponseModel;
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

    @PostMapping
    public ResponseEntity<CreateRideResponseModel> createRide(@RequestBody @Valid RideRequestModel request) {
        CreateRideResponseModel response = registerRideUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> cancelRide(@PathVariable @NonNull UUID rideId) {
        cancelRideUseCase.execute(rideId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RideResponseModel>> getAvailableRides() {
        List<RideResponseModel> rides = getRideUseCase.availableOnes();
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideResponseModel> getRideById(@PathVariable @NonNull UUID rideId) {
        RideResponseModel ride = getRideUseCase.byId(rideId);
        return ResponseEntity.ok(ride);
    }
}
