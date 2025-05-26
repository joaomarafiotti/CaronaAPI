package br.ifsp.demo.domain;

import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Data
@Entity
@AllArgsConstructor
public class Passenger extends User {

    @NonNull @Column(nullable = false)
    private Cpf cpf;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    protected Passenger(){
    }

    public Passenger(String name, String lastname, String email, String password, String cpf) {
        super(UUID.randomUUID(), name, lastname, email, password, Role.PASSENGER);
        this.cpf = new Cpf(cpf);
    }
}
