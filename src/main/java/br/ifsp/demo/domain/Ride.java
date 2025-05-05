package br.ifsp.demo.domain;

import br.ifsp.demo.utils.RideStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
public class Ride {

    @Id
    private UUID id;

    @Setter
    private String startAddress;

    @Setter
    private String endAddress;

    @Setter
    private LocalDateTime departureTime;

    @Setter
    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Setter
    @OneToMany(mappedBy = "ride")
    private List<Passenger> passengers;

    @Setter
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    protected Ride() {
    }

    public Ride(String startAddress, String endAddress, LocalDateTime departureTime, Driver driver, Car car) {
        this.id = UUID.randomUUID();
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.departureTime = departureTime;
        this.driver = driver;
        this.rideStatus = RideStatus.WAITING;
        this.car = car;
        this.passengers = new ArrayList<>();
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public void removePassenger(UUID passengerId) {
        Optional<Passenger> passenger = passengers.stream()
                .filter(p -> p.getId().equals(passengerId))
                .findFirst();
        if (passenger.isEmpty()) throw new EntityNotFoundException("Passenger not found in this ride");
        passengers.remove(passenger.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return Objects.equals(id, ride.id) && Objects.equals(startAddress, ride.startAddress)
               && Objects.equals(endAddress, ride.endAddress) && Objects.equals(departureTime, ride.departureTime)
               && rideStatus == ride.rideStatus && Objects.equals(driver, ride.driver) && Objects.equals(passengers, ride.passengers);
    }
}
