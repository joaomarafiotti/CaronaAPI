package br.ifsp.demo.service;

import br.ifsp.demo.domain.Passenger;

import java.util.List;

public interface NotificationService {
    void notifyPassengers(List<Passenger> passengers, String title, String message);
}
