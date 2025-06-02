package br.ifsp.demo.usecase.user;

import br.ifsp.demo.models.response.DriverResponseModel;
import br.ifsp.demo.models.response.PassengerResponseModel;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserUseCase {
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    public PassengerResponseModel getPassengerById(UUID passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new EntityNotFoundException("Passenger with id: " + passengerId + "was not found"))
                .toResponseModel();
    }

    public DriverResponseModel getDriverById(UUID driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id: " + driverId + "was not found"))
                .toResponseModel();
    }
}
