package br.ifsp.demo.service;

import br.ifsp.demo.domain.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceFake implements NotificationService{
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceFake.class);
    @Override
    public void notifyPassengers(List<Passenger> passengers, String title, String message) {
        if (passengers == null || passengers.isEmpty()) {
            LOGGER.info("No passengers to notify.");
            return;
        }

        for (Passenger p : passengers) {
            LOGGER.info("Notifying passenger [{} - {}]: {} - {}",
                    p.getName(),
                    p.getEmail(),
                    title,
                    message
            );
        }

    }
}
