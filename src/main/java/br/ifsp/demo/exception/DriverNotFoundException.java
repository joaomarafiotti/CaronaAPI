package br.ifsp.demo.exception;

import java.util.UUID;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(UUID driverId) {
        super("Driver not found with ID: " + driverId);
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}
