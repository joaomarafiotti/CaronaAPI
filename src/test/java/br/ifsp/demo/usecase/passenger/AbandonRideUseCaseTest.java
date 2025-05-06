package br.ifsp.demo.usecase.passenger;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.repositories.RideRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AbandonRideUseCaseTest {
    @Mock
    private Driver driver;
    @Mock
    private Car car;
    @Mock
    private RideRepository rideRepository;
    @InjectMocks
    private AbandonRideUseCase sut;
    private Ride ride;
    private Passenger p1;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        ride = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                now,
                driver,
                car
        );
        p1 = new Passenger("Gustavo", "endereco@gmail.com","123.456.789-55");
    }


    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should abandon ride if passenger is in it")
    public void shouldAbandonRideIfPassengerIsInIt() {
        ride.addPassenger(p1);
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.of(ride));
        sut.abandonFor(p1.getId(), ride.getId());
        assertThat(ride.getPassengers()).isEmpty();
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should throws if passenger is not in the ride")
    public void shouldThrowIfPassengerIsNotInRide() {
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.of(ride));
        assertThrows(EntityNotFoundException.class, () -> sut.abandonFor(p1.getId(), ride.getId()));
    }

}
