package br.ifsp.demo.domain;

import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.utils.RideStatus;
import com.thoughtworks.qdox.model.expression.Add;
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
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "start_street")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "start_neighborhood")),
            @AttributeOverride(name = "number", column = @Column(name = "start_number")),
            @AttributeOverride(name = "city", column = @Column(name = "start_city"))
    })
    private Address startAddress;

    @Setter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "end_street")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "end_neighborhood")),
            @AttributeOverride(name = "number", column = @Column(name = "end_number")),
            @AttributeOverride(name = "city", column = @Column(name = "end_city"))
    })
    private Address endAddress;

    @Setter
    private LocalDateTime departureTime;

    @Setter
    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "ride_passenger",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    private List<Passenger> passengers;

    @Setter
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    protected Ride() {
    }

    public Ride(Address startAddress, Address endAddress, LocalDateTime departureTime, Driver driver, Car car) {
        this.id = UUID.randomUUID();
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.departureTime = departureTime;
        this.driver = driver;
        this.rideStatus = RideStatus.WAITING;
        this.car = car;
        this.passengers = new ArrayList<>();
    }

    public int getAvailableSeats() {
        int availableSeats = this.car.getSeats() - this.passengers.size() - 1;
        if (availableSeats < 0) availableSeats = 0;
        return availableSeats;
    }

    public void addPassenger(Passenger passenger) {
        if(getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("No available seats.");
        }
        this.passengers.add(passenger);
    }

    public void removePassenger(UUID passengerId) {
        Optional<Passenger> passenger = passengers.stream()
                .filter(p -> p.getId().equals(passengerId))
                .findFirst();
        if (passenger.isEmpty()) throw new EntityNotFoundException("Passenger not found in this ride");
        passengers.remove(passenger.get());
    }

    public RideResponseModel toResponseModel() {
        return new RideResponseModel(
                this.getId(),
                this.getDepartureTime(),
                this.getStartAddress(),
                this.getEndAddress(),
                this.getAvailableSeats(),
                this.getRideStatus(),
                this.getDriver().toResponseModel(),
                this.getCar().toResponseModel()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return Objects.equals(id, ride.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
