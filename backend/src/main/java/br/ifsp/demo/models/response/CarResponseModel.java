package br.ifsp.demo.models.response;

import java.util.UUID;

public record CarResponseModel(UUID id, String brand, String model, String color, Integer seats, String licensePlate) {
}
