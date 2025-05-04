package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.utils.RideStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetRideUseCaseTest {
    @Mock
    Driver driver;

    @Mock
    Car car;

    @Mock
    RideRepository rideRepository;

    @InjectMocks
    GetRideUseCase sut;

    @Test
    @Tag("Unit Test")
    @Tag("TDD")
    @DisplayName("Should return a ride list with rides with waiting and full status")
    void shouldReturnARideListWithRidesWithWaitingAndFullStatus() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime r2Start = now.plusMinutes(90);
        LocalDateTime r3Start = now.plusHours(1);
        LocalDateTime r4Start = now.plusHours(2).plusMinutes(45);
        LocalDateTime r5Start = now.plusDays(1).plusHours(3);

        Ride r1 = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                now,
                driver,
                car
        );
        Ride r2 = new Ride(
                "Rua XV de Novembro, 500",
                "Rua das Laranjeiras, 200",
                r2Start,
                driver,
                car
        );
        Ride r3 = new Ride(
                "Av. Paulista, 1000",
                "Rua Augusta, 1500",
                r3Start,
                driver,
                car
        );
        Ride r4 = new Ride(
                "Praça da Sé, 50",
                "Rua Direita, 75",
                r4Start,
                driver,
                car
        );
        Ride r5 = new Ride(
                "Rua das Flores, 77",
                "Alameda Santos, 1500",
                r5Start,
                driver,
                car
        );
        r1.setRideStatus(RideStatus.WAITING);
        r2.setRideStatus(RideStatus.FULL);
        r3.setRideStatus(RideStatus.CANCELED);
        r4.setRideStatus(RideStatus.FINISHED);
        r5.setRideStatus(RideStatus.STARTED);

        RideResponseModel r1Resp = new RideResponseModel(now, RideStatus.WAITING, driver, car);
        RideResponseModel r2Resp = new RideResponseModel(r2Start, RideStatus.FULL, driver, car);

        when(rideRepository.findAll()).thenReturn(List.of(r1, r2, r3, r4, r5));

        assertThat(sut.availableOnes()).isEqualTo(List.of(r1Resp, r2Resp));
    }

    @Test
    @Tag("Unit Test")
    @Tag("TDD")
    @DisplayName("Should return empty list when system has no available ones")
    void shouldReturnEmptyListWhenSystemHasNoAvailableOnes() {
        when(rideRepository.findAll()).thenReturn(List.of());

        assertThat(sut.availableOnes()).isEmpty();
    }

    @Test
    @Tag("Unit Test")
    @Tag("TDD")
    @DisplayName("Should return a ride by UUID")
    void shouldReturnARideByUUID() {
        LocalDateTime now = LocalDateTime.now();

        Ride r1 = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                now,
                driver,
                car
        );

        r1.setRideStatus(RideStatus.WAITING);
        RideResponseModel r1Resp = new RideResponseModel(now, RideStatus.WAITING, driver, car);

        when(rideRepository.findById(r1.getId())).thenReturn(Optional.of(r1));

        assertThat(sut.byId(r1.getId())).isEqualTo(r1Resp);
    }

    @Test
    @Tag("Unit Test")
    @Tag("TDD")
    @DisplayName("Should throw exception when ride does not exist")
    void shouldThrowExceptionWhenRideDoesNotExist() {
        UUID uuid = UUID.randomUUID();

        when(rideRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> sut.byId(uuid));
    }
}