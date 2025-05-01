package br.ifsp.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Driver {

    @Id
    @GeneratedValue
    @Column(name = "driver_id", updatable = false, nullable = false)
    private UUID id;

    @NonNull @Setter
    @Column(nullable = false)
    private String name;

    @NonNull @Setter
    @Column(nullable = false)
    private String cpf;

    @NonNull @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull @Setter
    @Column(nullable = false)
    private LocalDate birthDate;

    @Setter
    @NonNull
    @Column(nullable = false)
    private boolean hasCar;

    @Setter @OneToMany(mappedBy = "driver")
    private List<Ride> rides;

    public Driver() {
    }

    public Driver(String name, String cpf, String email, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.birthDate = birthDate;
        this.hasCar = true;
    }
}
