package br.ifsp.demo.models.response;

import java.util.UUID;

public record RideSolicitationResponseModel(
        UUID rideSolicitationId,
        RideResponseModel ride,
        PassengerResponseModel passenger
) {
}
