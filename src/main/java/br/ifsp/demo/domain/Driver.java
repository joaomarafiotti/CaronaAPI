package br.ifsp.demo.domain;

import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.models.response.DriverResponseModel;
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

    @Embedded
    @Setter
    private Cpf cpf;

    @NonNull
    @Setter
    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Car> cars = new ArrayList<>();

    protected Driver() {
        super();
    }

    public Driver(String name, String lastname, String email, String password, String cpf, LocalDate birthDate) {
        super(UUID.randomUUID(), name, lastname, email, password, Role.DRIVER);
        this.cpf = new Cpf(cpf);
        this.birthDate = birthDate;
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

        if (solicitation.getRide().getDriver().equals(this)) {
            solicitation.setStatus(RideSolicitationStatus.ACCEPTED);
            return solicitation;
        }

        throw new EntityNotFoundException("Ride solicitation with id " + solicitation.getId() + " not found");
    }

    public RideSolicitation rejectIfIsTheOwner(RideSolicitation solicitation) {
        if (solicitation == null) throw new IllegalArgumentException("Ride solicitation is null");

        if (solicitation.getRide().getDriver().equals(this)) {
            solicitation.setStatus(RideSolicitationStatus.REJECTED);
            return solicitation;
        }

        throw new EntityNotFoundException("Ride solicitation with id " + solicitation.getId() + " not found");
    }

    public DriverResponseModel toResponseModel() {
        return new DriverResponseModel(this.getName(), this.getBirthDate());
    }
}
