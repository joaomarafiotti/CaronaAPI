package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.*;
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
    private Car car;
    @InjectMocks
    private ManageRideSolicitationUseCase sut;
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
                "123.456.789-55",
                "motorista@gmail.com",
                LocalDate.of(2004, 5, 6)
        );
        r1 = new Ride(
                "Av. Paulista, 1000",
                "Rua Augusta, 1500",
                now,
                driver,
                car
        );
        r2 = new Ride(
                "Rua XV de Novembro, 500",
                "Rua das Laranjeiras, 200",
                now.plusDays(1),
                driver,
                car
        );
        p1 = new Passenger("Gustavo", "passageiro@gmail.com", "123.456.789-55");
        p2 = new Passenger("Bruno", "passenger@gmail.com", "123.423.712-22");

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

        RideSolicitation acceptedS1 = sut.acceptSolicitationFor(s1.getId(), driver);

        assertThat(acceptedS1.getStatus()).isEqualTo(RideSolicitationStatus.ACCEPTED);
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should the driver reject the ride solicitation if he is the owner of the Ride")
    public void shouldRejectRideSolicitationIfTheDriverIsTheOwnerOfTheRide() {
        when(solicitationRepository.findById(any(UUID.class))).thenReturn(Optional.of(s1));

        RideSolicitation rejectedS1 = sut.rejectSolicitationFor(s1.getId(), driver);

        assertThat(rejectedS1.getStatus()).isEqualTo(RideSolicitationStatus.REJECTED);
    }

    @Test
    @DisplayName("Should throws EntityNotFoundException if the driver isn't the owner of the Ride")
    public void shouldThrowsEntityNotFoundExceptionIfTheDriverIsNotTheOwnerOfTheRide() {
        assertThrows(EntityNotFoundException.class, () -> sut.rejectSolicitationFor(s2.getId(), driver));
        assertThrows(EntityNotFoundException.class, () -> sut.acceptSolicitationFor(s4.getId(), driver));
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should passenger be added into ride after the solicitation is accepted")
    public void shouldPassengerBeAddedIntoRideAfterTheSolicitationIsAccepted() {
        when(solicitationRepository.findById(any(UUID.class))).thenReturn(Optional.of(s1));

        sut.acceptSolicitationFor(s1.getId(), driver);

        assertThat(r1.getPassengers()).contains(p1);
    }

}
