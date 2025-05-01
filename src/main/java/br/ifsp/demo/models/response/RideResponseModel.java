package br.ifsp.demo.models.response;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.utils.RideStatus;

import java.time.LocalDateTime;

public record RideResponseModel(LocalDateTime startTime, RideStatus status, Driver driver) {
}
