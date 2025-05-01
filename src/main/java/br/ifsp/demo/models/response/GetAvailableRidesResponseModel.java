package br.ifsp.demo.models.response;

import java.util.List;

public record GetAvailableRidesResponseModel(List<RideResponseModel> rides) {
}
