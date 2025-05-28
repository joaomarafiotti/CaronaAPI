package br.ifsp.demo.models.request;

import br.ifsp.demo.domain.LicensePlate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CarRequestModel(
        @NotBlank String brand,
        @NotBlank String model,
        @NotBlank String color,

        @NotNull
        @Positive(message = "Seats must be a positive number")
        Integer seats,

        @NotBlank
        String licensePlate
) {}
