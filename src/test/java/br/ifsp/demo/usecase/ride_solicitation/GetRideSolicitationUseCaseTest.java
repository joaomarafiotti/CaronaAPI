package br.ifsp.demo.usecase.ride_solicitation;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.repositories.RideSolicitationRepository;
import br.ifsp.demo.utils.RideSolicitationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetRideSolicitationUseCaseTest {
    @Mock
    private RideRepository rideRepository;
    @Mock
    private RideSolicitationRepository solicitationRepository;
    @InjectMocks
    private GetRideSolicitationUseCase sut;

    private LocalDateTime now;
    private Driver driver;
    private Car car;
    private Passenger passenger1;
    private Passenger passenger2;
    private Ride ride;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        driver = new Driver(
                "Gustavo",
                "123.456.789-55",
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
        passenger1 = new Passenger(
                "Pedro",
                "passageiro@gmail.com",
                "123.456.789-55"
        );
        passenger2 = new Passenger(
                "Pedro",
                "passageiro@gmail.com",
                "123.456.789-55"
        );
    }


    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should get the driver pending ride solicitations")
    public void shouldGetTheDriverPendingRideSolicitations() {
        RideSolicitation s1 = new RideSolicitation(ride, passenger1);
        RideSolicitation s2 = new RideSolicitation(ride, passenger2);
        s1.setStatus(RideSolicitationStatus.REJECTED);

        when(rideRepository.findRideByDriver_Id(driver.getId())).thenReturn(List.of(ride));
        when(solicitationRepository.findRideSolicitationByRide_Id(ride.getId())).thenReturn(List.of(s1, s2));

        assertThat(sut.getPendingSolicitationsFrom(driver)).isEqualTo(List.of(s2));
    }

    @Test
    @DisplayName("Should return a empty list if there's no pending solicitations")
    public void shouldReturnEmptyListIfThereIsNoPendingSolicitations() {
        assertThat(sut.getPendingSolicitationsFrom(driver)).isEmpty();
    }
}
