package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import br.ifsp.demo.utils.RideSolicitationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GetRideSolicitationUseCaseTest {
    private LocalDateTime now;
    private Driver driver;
    private Car car;
    private Passenger passenger;
    private Ride ride;
    private GetRideSolicitationUseCase sut;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        driver = new Driver(
                "Gustavo",
                "123.456.789-X",
                "motorista@gmail.com",
                LocalDate.of(2004, 5, 6)
        );
        car = new Car(
                "Fiat",
                "Palio",
                "Prata",
                4,
                "DQC1-ADQ"
        );
        ride = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                now,
                driver,
                car
        );
        passenger = new Passenger(
                "Pedro",
                "passageiro@gmail.com"
        );
        sut = new GetRideSolicitationUseCase();
    }


    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should get the driver pending ride solicitations")
    public void shouldGetTheDriverPendingRideSolicitations() {
        RideSolicitation s1 = new RideSolicitation(ride, passenger);
        RideSolicitation s2 = new RideSolicitation(ride, passenger);
        s1.setStatus(RideSolicitationStatus.REJECTED);

        when(driver.getRideSolicitations()).thenReturn(List.of(s1, s2));

        assertThat(sut.getPendingSolicitationsFrom(driver)).isEqualTo(List.of(s2));
    }

}
