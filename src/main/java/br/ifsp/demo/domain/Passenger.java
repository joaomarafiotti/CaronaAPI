package br.ifsp.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
}
