package br.ifsp.demo.domain;

import br.ifsp.demo.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;
@Data
@Entity
@AllArgsConstructor
public class Passenger {

    @Id
    @Getter
    @GeneratedValue
    private UUID id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String email;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    protected Passenger(){
    }

    public Passenger(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) && Objects.equals(name, passenger.name) && Objects.equals(email, passenger.email) && Objects.equals(ride, passenger.ride);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, ride);
    }
}
