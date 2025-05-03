package br.ifsp.demo.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarRequestModel(
        @NotBlank String brand,
        @NotBlank String model,
        @NotBlank String color,
        @NotNull Integer seats,
        @NotBlank String licensePlate,
        @NotNull UUID driverId
) {}
