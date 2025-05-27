package br.ifsp.demo.models.request;

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
        @Pattern(
                regexp = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$|^[A-Z]{3}[0-9]{4}$",
                message = "Invalid license plate format"
        )
        String licensePlate
) {}
