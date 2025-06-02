package br.ifsp.demo.models.response;

import br.ifsp.demo.domain.Address;
import br.ifsp.demo.security.user.User;
import br.ifsp.demo.utils.RideStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record RideResponseModel(
        UUID uuid,
        LocalDateTime startTime,
        Address pickupLocation,
        Address dropOffLocation,
        Integer availableSeats,
        RideStatus status,
        UserResponseModel driver,
        CarResponseModel car
) {
}
