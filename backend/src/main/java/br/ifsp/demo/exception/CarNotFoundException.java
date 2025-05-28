package br.ifsp.demo.exception;

import java.util.UUID;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(UUID carId) {
        super("Car not found with ID: " + carId);
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
