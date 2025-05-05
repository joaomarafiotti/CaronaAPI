package br.ifsp.demo.exception;

import java.util.UUID;

public class CarInUseException extends RuntimeException{
    public CarInUseException(UUID carId) {
        super("Car with id '" + carId + " is linked to a ride. Cannot be removed.");
    }
}
