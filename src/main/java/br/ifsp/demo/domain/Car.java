package br.ifsp.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer seats;

    @Column(unique = true, nullable = false)
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    public Car() {
    }

    public Car(String brand, String model, String color, Integer seats, String licensePlate) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.seats = seats;
        this.licensePlate = licensePlate;
    }
}