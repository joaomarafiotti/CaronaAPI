package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.exception.EntityAlreadyExistsException;
import br.ifsp.demo.exception.RideSolicitationForInvalidRideException;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import br.ifsp.demo.utils.RideSolicitationStatus;
import br.ifsp.demo.utils.RideStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateRideSolicitationUseCaseTest {
    @Mock
    private RideSolicitationRepository solicitationRepo;
    @InjectMocks
    private CreateRideSolicitationUseCase sut;

    private LocalDateTime now;
    private Driver driver;
    private Car car;
    private Passenger passenger;
    private Passenger p1;
    private Passenger p2;
    private Passenger p3;
    private Passenger p4;
    private Ride ride;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();

        driver = new Driver(
                "Gustavo",
                "Silva",
                "motorista@gmail.com",
                "senha123",
                "123.456.789-55",
                LocalDate.of(2004, 5, 6)
        );

        car = new Car(
                "Fiat",
                "Palio",
                "Prata",
                5,
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
                "Gustavo",
                "Silva",
                "passageiro@gmail.com",
                "senha123",
                "123.456.789-55"
        );

        p1 = new Passenger(
                "Pedro",
                "Santos",
                "passageiro@gmail.com",
                "senha123",
                "321.654.789-55"
        );

        p2 = new Passenger(
                "Giovanna",
                "Costa",
                "passageira@gmail.com",
                "senha123",
                "123.456.789-77"
        );

        p3 = new Passenger(
                "Rodrigo",
                "Almeida",
                "rodrigo123@gmail.com",
                "senha123",
                "123.499.888-55"
        );

        p4 = new Passenger(
                "Pedro",
                "Oliveira",
                "pedro@gmail.com",
                "senha123",
                "111.222.335-55"
        );
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should create and register ride solicitation")
    public void shouldCreateAndRegisterRideSolicitation() {
        RideSolicitation rideSolicitation = sut.createAndRegisterRideSolicitationFor(passenger, ride);

        assertThat(rideSolicitation).isNotNull();
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should not create two equals solicitations")
    public void shouldNotCreateTwoEqualsSolicitations() {
        RideSolicitation r1 = sut.createAndRegisterRideSolicitationFor(passenger, ride);

        when(solicitationRepo.findRideSolicitationByRide_Id(any(UUID.class))).thenReturn(List.of(r1));

        assertThrows(EntityAlreadyExistsException.class, () -> sut.createAndRegisterRideSolicitationFor(passenger, ride));
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
                "Contiero",
                "motorista@gmail.com",
                "DSd3149l;",
                "123.458.719-55",
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
                "Castro",
                "F312hhnd;",
                "passageiro@gmail.com",
                "123.456.333-99"
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

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should throws exception if passenger tries to create a solicitation to a ride that is not with status Waiting")
    public void shouldThrowExceptionIfRideIsNotWithStatusWaiting() {
        ride.addPassenger(p1);
        ride.addPassenger(p2);
        ride.addPassenger(p3);
        ride.addPassenger(p4);

        assertThrows(RideSolicitationForInvalidRideException.class, () -> sut.createAndRegisterRideSolicitationFor(passenger, ride));
    }
}
