package br.ifsp.demo.controller.car;

import br.ifsp.demo.models.request.CarRequestModel;
import br.ifsp.demo.models.response.CarResponseModel;
import br.ifsp.demo.models.response.CreateCarResponseModel;
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
@RequestMapping("/api/v1/drivers/{driverId}/cars")
@RequiredArgsConstructor
public class CarController {

    private final GetCarUseCase getCarUseCase;
    private final RegisterCarUseCase registerCarUseCase;
    private final RemoveCarUseCase removeCarUseCase;

    @GetMapping
    public ResponseEntity<List<CarResponseModel>> getAllCars(@PathVariable UUID driverId) {
        List<CarResponseModel> cars = getCarUseCase.allCars(driverId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseModel> getCarById(
            @PathVariable UUID driverId,
            @PathVariable UUID carId
    ) {
        CarResponseModel car = getCarUseCase.byId(driverId, carId);
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<CreateCarResponseModel> registerCar(
            @RequestBody @Valid CarRequestModel request
    ) {
        CreateCarResponseModel response = registerCarUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<String> deleteCar(
            @PathVariable UUID driverId,
            @PathVariable UUID carId
    ) {
        removeCarUseCase.execute(driverId, carId);
        return ResponseEntity.ok("Car deleted successfully");
    }
}
