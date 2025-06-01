package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import br.ifsp.demo.utils.RideSolicitationStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ManageRideSolicitationUseCaseTest {
    @Mock
    RideSolicitationRepository solicitationRepository;
    @Mock
    DriverRepository driverRepository;
    @Mock
    RideRepository rideRepository;
    @Mock
    private Car car;
    @InjectMocks
    private ManageRideSolicitationUseCase sut;

    private Address address0;
    private Address address1;
    private Address address2;
    private Address address3;

    private Ride r1;
    private Ride r2;
    private Passenger p1;
    private Passenger p2;
    private Driver driver;
    private RideSolicitation s1;
    private RideSolicitation s2;
    private RideSolicitation s3;
    private RideSolicitation s4;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDateTime now = LocalDateTime.now();
        driver = new Driver(
                "Gustavo",
                "Contiero",
                "motorista@gmail.com",
                "1323nnks;",
                Cpf.of("529.982.247-25"),
                LocalDate.of(2004, 5, 6)
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

        r1 = new Ride(
                address0,
                address1,
                now,
                driver,
                car
        );
        r2 = new Ride(
                address2,
                address3,
                now.plusDays(1),
                driver,
                car
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

        s1 = new RideSolicitation(r1, p1);
        s2 = new RideSolicitation(r1, p2);
        s3 = new RideSolicitation(r2, p1);
        s4 = new RideSolicitation(r2, p2);
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should the driver accept the ride solicitation if he is the owner of the Ride")
    public void shouldAcceptRideSolicitationIfTheDriverIsTheOwnerOfTheRide() {
        when(solicitationRepository.findById(any(UUID.class))).thenReturn(Optional.of(s1));
        when(driverRepository.findById(any(UUID.class))).thenReturn(Optional.of(driver));
        when(car.getSeats()).thenReturn(5);

        RideSolicitation acceptedS1 = sut.acceptSolicitationFor(s1.getId(), driver.getId());

        assertThat(acceptedS1.getStatus()).isEqualTo(RideSolicitationStatus.ACCEPTED);
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should the driver reject the ride solicitation if he is the owner of the Ride")
    public void shouldRejectRideSolicitationIfTheDriverIsTheOwnerOfTheRide() {
        when(solicitationRepository.findById(any(UUID.class))).thenReturn(Optional.of(s1));
        when(driverRepository.findById(any(UUID.class))).thenReturn(Optional.of(driver));

        RideSolicitation rejectedS1 = sut.rejectSolicitationFor(s1.getId(), driver.getId());

        assertThat(rejectedS1.getStatus()).isEqualTo(RideSolicitationStatus.REJECTED);
    }

    @Test
    @DisplayName("Should throws EntityNotFoundException if the driver isn't the owner of the Ride")
    public void shouldThrowsEntityNotFoundExceptionIfTheDriverIsNotTheOwnerOfTheRide() {
        assertThrows(EntityNotFoundException.class, () -> sut.rejectSolicitationFor(s2.getId(), driver.getId()));
        assertThrows(EntityNotFoundException.class, () -> sut.acceptSolicitationFor(s4.getId(), driver.getId()));
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should passenger be added into ride after the solicitation is accepted")
    public void shouldPassengerBeAddedIntoRideAfterTheSolicitationIsAccepted() {
        when(solicitationRepository.findById(any(UUID.class))).thenReturn(Optional.of(s1));
        when(driverRepository.findById(any(UUID.class))).thenReturn(Optional.of(driver));
        when(car.getSeats()).thenReturn(5);

        sut.acceptSolicitationFor(s1.getId(), driver.getId());

        assertThat(r1.getPassengers()).contains(p1);
    }

}
