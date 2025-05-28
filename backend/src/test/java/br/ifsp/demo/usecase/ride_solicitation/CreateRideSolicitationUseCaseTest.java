package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.exception.EntityAlreadyExistsException;
import br.ifsp.demo.exception.RideSolicitationForInvalidRideException;
import br.ifsp.demo.repositories.RideSolicitationRepository;
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
    private Address address0;
    private Address address1;
    private Address address2;
    private Address address3;
    private Ride ride0;
    private Ride ride1;
    private Passenger p0;
    private Passenger p1;
    private Passenger p2;
    private Passenger p3;
    private Passenger p4;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();

        driver = new Driver(
                "Gustavo",
                "Silva",
                "motorista@gmail.com",
                "senha123",
                Cpf.of("529.982.247-25"),
                LocalDate.of(2004, 5, 6)
        );

        car = new Car(
                "Fiat",
                "Palio",
                "Prata",
                5,
                LicensePlate.parse("DQC1A18")
        );

        address0 = new Address.AddressBuilder()
                .street("Rua São João Bosco")
                .number("1324")
                .neighborhood("Planalto Paraíso")
                .city("São Carlos")
                .build();
        address1 = new Address.AddressBuilder()
                .street("Av. Miguel Petroni")
                .number("321")
                .neighborhood("Planalto Paraíso")
                .city("São Carlos")
                .build();
        address2 = new Address.AddressBuilder()
                .street("Rua das Flores")
                .number("215A")
                .neighborhood("Vila Nova")
                .city("Campinas")
                .build();
        address3 = new Address.AddressBuilder()
                .street("Av. das Amoreiras")
                .number("78B")
                .neighborhood("Jardim Botânico")
                .city("Rio de Janeiro")
                .build();

        ride0 = new Ride(
                address0,
                address1,
                now,
                driver,
                car
        );

        ride1 = new Ride(
                address2,
                address3,
                now,
                driver,
                car
        );

        p0 = new Passenger(
                "Gustavo",
                "Silva",
                "passageiro@gmail.com",
                "senha123",
                Cpf.of("123.456.789-09"),
                LocalDate.of(2003, 5, 12)
        );

        p1 = new Passenger(
                "Pedro",
                "Santos",
                "passageiro@gmail.com",
                "senha123",
                Cpf.of("111.444.777-35"),
                LocalDate.of(1999, 5, 12)
        );

        p2 = new Passenger(
                "Giovanna",
                "Costa",
                "passageira@gmail.com",
                "senha123",
                Cpf.of("390.533.447-05"),
                LocalDate.of(1999, 12, 21)
        );

        p3 = new Passenger(
                "Rodrigo",
                "Almeida",
                "rodrigo123@gmail.com",
                "senha123",
                Cpf.of("145.382.206-20"),
                LocalDate.of(2010, 12, 21)

        );

        p4 = new Passenger(
                "Pedro",
                "Oliveira",
                "pedro@gmail.com",
                "senha123",
                Cpf.of("145.382.206-20"),
                LocalDate.of(2010, 12, 21)
        );
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should create and register ride solicitation")
    public void shouldCreateAndRegisterRideSolicitation() {
        when(solicitationRepo.findRideSolicitationByRide_Id(any(UUID.class)))
                .thenReturn(List.of(new RideSolicitation(ride1, p0)));

        RideSolicitation rideSolicitation = sut.createAndRegisterRideSolicitationFor(p0, ride0);

        assertThat(rideSolicitation).isNotNull();
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should not create two equals solicitations")
    public void shouldNotCreateTwoEqualsSolicitations() {
        RideSolicitation r1 = sut.createAndRegisterRideSolicitationFor(p0, ride0);

        when(solicitationRepo.findRideSolicitationByRide_Id(any(UUID.class))).thenReturn(List.of(r1));

        assertThrows(EntityAlreadyExistsException.class, () -> sut.createAndRegisterRideSolicitationFor(p0, ride0));
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
                Cpf.of("529.982.247-25"),
                LocalDate.of(2004, 5, 6)
        );
        Car car = new Car(
                "Fiat",
                "Palio",
                "Prata",
                4,
                LicensePlate.parse("KEG9C54")
        );
        Passenger passenger = new Passenger(
                "Pedro",
                "Castro",
                "F312hhnd;",
                "passageiro@gmail.com",
                Cpf.of("529.982.247-25"),
                LocalDate.of(2004, 12, 6)
        );

        Ride ride = new Ride(
                new Address.AddressBuilder()
                        .street("Rua São João Bosco")
                        .number("1324")
                        .neighborhood("Planalto Paraíso")
                        .city("São Carlos")
                        .build(),
                new Address.AddressBuilder()
                        .street("Av. Miguel Petroni")
                        .number("321")
                        .neighborhood("Planalto Paraíso")
                        .city("São Carlos")
                        .build(),
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
        ride0.addPassenger(p1);
        ride0.addPassenger(p2);
        ride0.addPassenger(p3);
        ride0.addPassenger(p4);

        assertThrows(RideSolicitationForInvalidRideException.class, () -> sut.createAndRegisterRideSolicitationFor(p0, ride0));
    }
}
