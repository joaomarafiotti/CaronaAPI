package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.domain.RideSolicitation;
import br.ifsp.demo.utils.RideSolicitationStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ManageRideSolicitationUseCaseTest {
    @Mock
    private Ride r1;
    @Mock
    private Ride r2;
    @Mock
    private Passenger p1;
    @Mock
    private Passenger p2;

    private Driver driver;
    private RideSolicitation s1;
    private RideSolicitation s2;
    private RideSolicitation s3;
    private RideSolicitation s4;

    private ManageRideSolicitationUseCase sut;

    @BeforeEach
    void setUp() {
        sut = new ManageRideSolicitationUseCase();
        driver = new Driver(
                "Gustavo",
                "123.456.789-X",
                "motorista@gmail.com",
                LocalDate.of(2004, 5, 6)
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
    public void shouldAcceptRideSolicitationIfTheOwnerIsTheOwnerOfTheRide() {
        driver.addSolicitations(s1);
        driver.addSolicitations(s3);

        sut.acceptSolicitation(s1, driver);

        RideSolicitation acceptedS1 = driver.getRideSolicitations()
                .stream()
                .filter(s -> s.getId() == s1.getId())
                .findFirst()
                .orElseThrow();

        assertThat(acceptedS1.getStatus()).isEqualTo(RideSolicitationStatus.ACCEPTED);
    }
}
