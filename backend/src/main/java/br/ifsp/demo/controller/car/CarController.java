package br.ifsp.demo.controller.car;

import br.ifsp.demo.models.request.CarRequestModel;
import br.ifsp.demo.models.response.CarResponseModel;
import br.ifsp.demo.models.response.CreateCarResponseModel;
import br.ifsp.demo.security.auth.AuthenticationInfoService;
import br.ifsp.demo.security.auth.UserAuthorizationVerifier;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.usecase.car.GetCarUseCase;
import br.ifsp.demo.usecase.car.RegisterCarUseCase;
import br.ifsp.demo.usecase.car.RemoveCarUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/drivers/cars")
@RequiredArgsConstructor
public class CarController {
    private final AuthenticationInfoService authenticationInfoService;
    private final GetCarUseCase getCarUseCase;
    private final RegisterCarUseCase registerCarUseCase;
    private final RemoveCarUseCase removeCarUseCase;
    private final UserAuthorizationVerifier verifier;

    @GetMapping
    public ResponseEntity<List<CarResponseModel>> getAllCars() {
        UUID driverId = authenticationInfoService.getAuthenticatedUserId();
        List<CarResponseModel> cars = getCarUseCase.allCars(driverId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseModel> getCarById(
            @PathVariable UUID carId
    ) {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);
        CarResponseModel car = getCarUseCase.byId(driverId, carId);
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<CreateCarResponseModel> registerCar(
            @RequestBody @Valid CarRequestModel request
    ) {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);
        CreateCarResponseModel response = registerCarUseCase.execute(request, driverId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<String> deleteCar(
            @PathVariable UUID carId
    ) {
        UUID driverId = verifier.verifyAndReturnUuidOf(Role.DRIVER);
        removeCarUseCase.execute(driverId, carId);
        return ResponseEntity.ok("Car deleted successfully");
    }
}
