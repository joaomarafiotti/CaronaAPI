package br.ifsp.demo.models.request;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record RideRequestModel(
        @NotBlank String startAddress,
        @NotBlank String endAddress,
        @NotNull LocalDateTime departureTime,
        @NotNull UUID driverId,
        @NotNull UUID carId
) {
}
