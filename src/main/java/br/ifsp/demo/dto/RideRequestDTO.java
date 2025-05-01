package br.ifsp.demo.dto;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record RideRequestDTO(
        @NotBlank String startAddress,
        @NotBlank String endAddress,
        @NotNull LocalDateTime departureTime,
        @NotNull UUID driverId
) {
    public Ride toRide(Driver driver){
        return new Ride(startAddress, endAddress, departureTime, driver);
    }
}
