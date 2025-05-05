package br.ifsp.demo.domain;

import br.ifsp.demo.utils.RideSolicitationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @NonNull
    @Setter
    @Column(nullable = false)
    private String cpf;

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

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<RideSolicitation> rideSolicitations = new ArrayList<>();

    public Driver() {
    }

    public Driver(String name, String cpf, String email, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.birthDate = birthDate;
    }

    public void addCar(Car car) {
        if (!this.cars.contains(car)) {
            car.setDriver(this);
            this.cars.add(car);
        }
    }

    public void addSolicitations(RideSolicitation s) {
        if (!this.rideSolicitations.contains(s)) {
            this.rideSolicitations.add(s);
        }
    }

    public RideSolicitation accept(UUID solicitationId) {
        Optional<RideSolicitation> s = rideSolicitations.stream()
                .filter(rideSolicitation -> rideSolicitation.getId().equals(solicitationId))
                .findFirst();

        if (s.isPresent()) {
            s.get().setStatus(RideSolicitationStatus.ACCEPTED);
            return s.get();
        }

        throw new EntityNotFoundException("Ride solicitation with id " + solicitationId + " not found");
    }

    public RideSolicitation reject(UUID solicitationId) {
        Optional<RideSolicitation> s = rideSolicitations.stream()
                .filter(rideSolicitation -> rideSolicitation.getId().equals(solicitationId))
                .findFirst();

        if (s.isPresent()) {
            s.get().setStatus(RideSolicitationStatus.REJECTED);
            return s.get();
        }

        throw new EntityNotFoundException("Ride solicitation with id " + solicitationId + " not found");
    }
}
