package br.ifsp.demo.models.response;

import br.ifsp.demo.utils.RideSolicitationStatus;

import java.util.UUID;

public record RideSolicitationResponseModel(
        UUID rideSolicitationId,
        RideSolicitationStatus status,
        RideResponseModel ride,
        PassengerResponseModel passenger
) {
}
