package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CreateRideSolicitationUseCaseTest {
    private LocalDateTime now;
    private Driver driver;
    private Car car;
    private Passenger passenger;
    private Ride ride;
    private CreateRideSolicitationUseCase sut;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        driver = new Driver("Gustavo", "123.456.789-X", "motorista@gmail.com", LocalDate.of(2004, 5, 6));
        car = new Car("Fiat", "Palio", "Prata", 4, "DQC1-ADQ");
        ride = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                now,
                driver,
                car
        );
        passenger = new Passenger("Pedro", "passageiro@gmail.com");

    }


    @Test
    @DisplayName("Should create and register ride solicitation")
    public void shouldCreateAndRegisterRideSolicitation() {
        RideSolicitation rideSolicitation = sut.createAndRegisterRideSolicitationFor(passenger, ride);

        assertThat(driver.getRideSolicitations()).isEqualTo(List.of(rideSolicitation));
    }
}
