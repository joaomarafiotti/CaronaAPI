package br.ifsp.demo.exception;

import java.util.UUID;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(UUID rideID){
        super("Ride with id: '" + rideID + "' not found");
    }
}
