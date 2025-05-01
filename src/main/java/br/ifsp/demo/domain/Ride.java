package br.ifsp.demo.domain;

import br.ifsp.demo.utils.RideStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Ride {

    @Id
    @GeneratedValue
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

    public Ride() {
    }

    public Ride(String startAddress, String endAddress, LocalDateTime departureTime, Driver driver) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.departureTime = departureTime;
        this.driver = driver;
        this.rideStatus = RideStatus.WAITING;
    }
}
