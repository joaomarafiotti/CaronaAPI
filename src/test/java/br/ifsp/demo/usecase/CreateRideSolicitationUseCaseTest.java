package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CreateRideSolicitationUseCaseTest {
    @Mock
    private RideSolicitationRepository solicitationRepo;
    private LocalDateTime now;
    private Driver driver;
    private Car car;
    private Passenger passenger;
    private Ride ride;
    private CreateRideSolicitationUseCase sut;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        sut = new CreateRideSolicitationUseCase(solicitationRepo);
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
    }


    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should create and register ride solicitation")
    public void shouldCreateAndRegisterRideSolicitation() {
        RideSolicitation rideSolicitation = sut.createAndRegisterRideSolicitationFor(passenger, ride);

        assertThat(driver.getRideSolicitations()).isEqualTo(List.of(rideSolicitation));
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should not create two equals solicitations")
    public void shouldNotCreateTwoEqualsSolicitations() {
        RideSolicitation r1 = sut.createAndRegisterRideSolicitationFor(passenger, ride);
        assertThrows(Exception.class, () -> sut.createAndRegisterRideSolicitationFor(passenger, ride));
    }


    @ParameterizedTest
    @Tag("UnitTest")
    @Tag("TDD")
    @MethodSource("nullArgsSource")
    @DisplayName("Should Throw IllegalArgumentException when receiving wrong args")
    public void shouldThrowIllegalArgumentExceptionWhenReceivingWrongArgs(Passenger passenger, Ride ride) {
        assertThrows(IllegalArgumentException.class, () -> sut.createAndRegisterRideSolicitationFor(passenger, ride));
    }

    private static Stream<Arguments> nullArgsSource() {
        LocalDateTime now = LocalDateTime.now();
        Driver driver = new Driver(
                "Gustavo",
                "123.456.789-X",
                "motorista@gmail.com",
                LocalDate.of(2004, 5, 6)
        );
        Car car = new Car(
                "Fiat",
                "Palio",
                "Prata",
                4,
                "DQC1-ADQ"
        );
        Passenger passenger = new Passenger(
                "Pedro",
                "passageiro@gmail.com"
        );
        Ride ride = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                now,
                driver,
                car
        );

        return Stream.of(
                Arguments.of(null, ride),
                Arguments.of(passenger, null)
        );
    }
}
