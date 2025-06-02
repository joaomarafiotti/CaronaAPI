package br.ifsp.demo.domain;

import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import br.ifsp.demo.utils.RideSolicitationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
public class Driver extends User {

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Car> cars = new ArrayList<>();

    protected Driver() {
        super();
    }

    public Driver(String name, String lastname, String email, String password, Cpf cpf, LocalDate birthDate) {
        super(UUID.randomUUID(), name, lastname, email, cpf, birthDate, password, Role.DRIVER);
    }

    public void removeCarById(UUID carId) {
        boolean removed = cars.removeIf(car -> car.getId().equals(carId));
        if (!removed) {
            throw new CarNotFoundException(carId);
        }
    }

    public void addCar(Car car) {
        if (!this.cars.contains(car)) {
            car.setDriver(this);
            this.cars.add(car);
        }
    }

    public RideSolicitation acceptIfIsTheOwner(RideSolicitation solicitation) {
        if (solicitation == null) throw new IllegalArgumentException("Ride solicitation is null");
        if (isInValidSolicitationState(solicitation))
            throw new IllegalStateException("Ride solicitation status is not pending");
        if (solicitation.getRide().getDriver().equals(this)) {
            solicitation.setStatus(RideSolicitationStatus.ACCEPTED);
            return solicitation;
        }

        throw new EntityNotFoundException("Ride solicitation with id " + solicitation.getId() + " not found");
    }

    private static boolean isInValidSolicitationState(RideSolicitation solicitation) {
        return solicitation.getStatus().equals(RideSolicitationStatus.ACCEPTED) || solicitation.getStatus().equals(RideSolicitationStatus.REJECTED) || solicitation.getStatus().equals(RideSolicitationStatus.CANCELLED);
    }

    public RideSolicitation rejectIfIsTheOwner(RideSolicitation solicitation) {
        if (solicitation == null) throw new IllegalArgumentException("Ride solicitation is null");

        if (solicitation.getRide().getDriver().equals(this)) {
            solicitation.setStatus(RideSolicitationStatus.REJECTED);
            return solicitation;
        }

        throw new EntityNotFoundException("Ride solicitation with id " + solicitation.getId() + " not found");
    }
}
