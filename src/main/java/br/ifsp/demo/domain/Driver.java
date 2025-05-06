package br.ifsp.demo.domain;

import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.models.response.DriverResponseModel;
import br.ifsp.demo.utils.RideSolicitationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
public class Driver {

    @Id
    @GeneratedValue
    @Column(name = "driver_id", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    @Setter
    @Column(nullable = false)
    private String name;

    @Embedded
    @Unique
    private Cpf cpf;

    @NonNull
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Setter
    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Car> cars = new ArrayList<>();

    protected Driver() {
    }

    public Driver(String name, String cpf, String email, LocalDate birthDate) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cpf = new Cpf(cpf);
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id) && Objects.equals(name, driver.name) && Objects.equals(cpf, driver.cpf) && Objects.equals(email, driver.email) && Objects.equals(birthDate, driver.birthDate) && Objects.equals(cars, driver.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, email, birthDate, cars);
    }
}
