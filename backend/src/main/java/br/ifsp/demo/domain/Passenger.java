package br.ifsp.demo.domain;

import br.ifsp.demo.models.response.PassengerResponseModel;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
public class Passenger extends User {
    @ManyToMany(mappedBy = "passengers")
    private List<Ride> ride;

    protected Passenger() {
    }

    public Passenger(String name, String lastname, String email, String password, Cpf cpf, LocalDate birthDate) {
        super(UUID.randomUUID(), name, lastname, email, cpf, birthDate, password, Role.PASSENGER);
    }

    public PassengerResponseModel toResponseModel() {
        return new PassengerResponseModel(this.getName(), this.getCpf(), this.getBirthDate());
    }
}
