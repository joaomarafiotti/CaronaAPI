package br.ifsp.demo.exception;

public class RideSolicitationForInvalidRideException extends RuntimeException {
    public RideSolicitationForInvalidRideException(String message) {
        super(message);
    }
}
