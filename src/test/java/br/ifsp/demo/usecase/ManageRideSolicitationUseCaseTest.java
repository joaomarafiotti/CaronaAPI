package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.utils.RideSolicitationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManageRideSolicitationUseCaseTest {
    @Mock
    private Car car;
    private Ride r1;
    private Ride r2;
    private Passenger p1;
    private Passenger p2;
    private Driver driver;
    private RideSolicitation s1;
    private RideSolicitation s2;
    private RideSolicitation s3;
    private RideSolicitation s4;

    private ManageRideSolicitationUseCase sut;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        sut = new ManageRideSolicitationUseCase();
        driver = new Driver(
                "Gustavo",
                "123.456.789-X",
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
        p1 = new Passenger("Gustavo", "passageiro@gmail.com");
        p2 = new Passenger("Bruno", "passenger@gmail.com");

        s1 = new RideSolicitation(r1, p1);
        s2 = new RideSolicitation(r1, p2);
        s3 = new RideSolicitation(r2, p1);
        s4 = new RideSolicitation(r2, p2);
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should the driver accept the ride solicitation if he is the owner of the Ride")
    public void shouldAcceptRideSolicitationIfTheOwnerIsTheOwnerOfTheRide() {
        driver.addSolicitations(s1);
        driver.addSolicitations(s3);

        sut.acceptSolicitationFor(s1.getId(), driver);

        RideSolicitation acceptedS1 = driver.getRideSolicitations()
                .stream()
                .filter(s -> s.getId() == s1.getId())
                .findFirst()
                .orElseThrow();

        assertThat(acceptedS1.getStatus()).isEqualTo(RideSolicitationStatus.ACCEPTED);
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should the driver reject the ride solicitation if he is the owner of the Ride")
    public void shouldRejectRideSolicitationIfTheOwnerIsTheOwnerOfTheRide() {
        driver.addSolicitations(s1);
        driver.addSolicitations(s3);

        sut.rejectSolicitationFor(s1.getId(), driver);

        RideSolicitation rejectedS1 = driver.getRideSolicitations()
                .stream()
                .filter(s -> s.getId() == s1.getId())
                .findFirst()
                .orElseThrow();

        assertThat(rejectedS1.getStatus()).isEqualTo(RideSolicitationStatus.REJECTED);
    }

}
