package br.ifsp.demo.domain;

import br.ifsp.demo.models.response.RideSolicitationResponseModel;
import br.ifsp.demo.utils.RideSolicitationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RideSolicitation {
    @Id
    private UUID Id = UUID.randomUUID();

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @OneToOne
    @NotNull
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @Column
    private RideSolicitationStatus status = RideSolicitationStatus.WAITING;

    public RideSolicitation(Ride ride, Passenger passenger) {
        if (ride == null || passenger == null) {
            throw new IllegalArgumentException("Ride and passenger must not be null");
        }

        this.ride = ride;
        this.passenger = passenger;
    }

    public RideSolicitationResponseModel toResponseModel() {
        return new RideSolicitationResponseModel(
                this.getId(),
                this.getRide().toResponseModel(),
                this.getPassenger().toResponseModel()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        RideSolicitation that = (RideSolicitation) o;
        return ride.equals(that.ride) && passenger.equals(that.passenger);
    }

    @Override
    public int hashCode() {
        int result = ride.hashCode();
        result = 31 * result + passenger.hashCode();
        return result;
    }
}
