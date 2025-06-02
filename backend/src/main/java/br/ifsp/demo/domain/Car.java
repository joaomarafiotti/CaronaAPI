package br.ifsp.demo.domain;

import br.ifsp.demo.models.response.CarResponseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @Column(name = "car_id", updatable = false, nullable = false)
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
    private LicensePlate licensePlate;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private Boolean isActive;

    protected Car() {
    }

    public Car(String brand, String model, String color, Integer seats, LicensePlate licensePlate) {
        this.id = UUID.randomUUID();
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.isActive = true;
    }

    public CarResponseModel toResponseModel() {
        return new CarResponseModel(this.getId(), this.getBrand(), this.getModel(), this.getColor(), this.getSeats(), this.getLicensePlate().toString(), this.isActive);
    }

    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}