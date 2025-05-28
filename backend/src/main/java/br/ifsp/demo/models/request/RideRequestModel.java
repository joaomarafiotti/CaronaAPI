package br.ifsp.demo.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record RideRequestModel(
        @NotBlank @Schema(example = "Rua das Palmeiras, 250B, Jardim América, São Paulo")
        String startAddress,
        @NotBlank @Schema(example = "Av. Brasil, 1020, Centro, Rio de Janeiro")
        String endAddress,
        @NotNull @Schema(example = "2025-05-28T14:30:00")
        LocalDateTime departureTime,
        @NotNull
        UUID carId
) {
}
