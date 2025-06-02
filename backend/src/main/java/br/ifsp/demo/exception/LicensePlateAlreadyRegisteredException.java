package br.ifsp.demo.exception;

public class LicensePlateAlreadyRegisteredException extends RuntimeException {
    public LicensePlateAlreadyRegisteredException(String plate) {
        super("License plate already registered: " + plate);
    }
}
