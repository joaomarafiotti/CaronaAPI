package br.ifsp.demo.usecase.passenger;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AbandonRideUseCaseTest {
    @Mock
    private Driver driver;
    @Mock
    private Car car;
    private AbandonRideUseCase sut;
    private Ride ride;
    private Passenger p1;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        sut = new AbandonRideUseCase();
        driver = new Driver();
        ride = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                now,
                driver,
                car
        );
        p1 = new Passenger("Gustavo", "endereco@gmail.com");
        ride.addPassenger(p1);
    }


    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should abandon ride if passenger is in it")
    public void shouldAbandonRideIfPassengerIsInIt() {
        sut.abandonFor(p1.getId(), ride.getId());
        assertThat(ride.getPassengers()).isEmpty();
    }
}
