package br.ifsp.demo;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.dto.RideDTO;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.usecase.GetRideUseCase;
import br.ifsp.demo.utils.RideStatus;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetRideUseCaseTest {
    @Mock
    Driver driver;

    @Mock
    RideRepository rideRepository;

    @InjectMocks
    GetRideUseCase sut;

    @Test
    void shouldReturnARideListWithRidesWithWaitingAndFullStatus() {

        Ride r1 = new Ride(
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                LocalDateTime.now(),
                driver
        );
        Ride r2 = new Ride(
                "Rua XV de Novembro, 500",
                "Rua das Laranjeiras, 200",
                LocalDateTime.now().plusMinutes(30),
                driver
        );
        Ride r3 = new Ride(
                "Av. Paulista, 1000",
                "Rua Augusta, 1500",
                LocalDateTime.now().plusHours(1),
                driver
        );
        Ride r4 = new Ride(
                "Praça da Sé, 50",
                "Rua Direita, 75",
                LocalDateTime.now().plusHours(2).plusMinutes(45),
                driver
        );
        Ride r5 = new Ride(
                "Rua das Flores, 77",
                "Alameda Santos, 1500",
                LocalDateTime.now().plusDays(1).plusHours(3),
                driver
        );
        r1.setRideStatus(RideStatus.WAITING);
        r2.setRideStatus(RideStatus.FULL);
        r3.setRideStatus(RideStatus.CANCELED);
        r4.setRideStatus(RideStatus.FINISHED);
        r5.setRideStatus(RideStatus.STARTED);

        RideResponseModel r1Resp = new RideResponseModel(LocalDateTime.now(), RideStatus.WAITING, driver);
        RideResponseModel r2Resp = new RideResponseModel(LocalDateTime.now(), RideStatus.FULL, driver);

        when(rideRepository.findAll()).thenReturn(List.of(r1, r2, r3, r4, r5));

        assertThat(sut.availableOnes()).isEqualTo(List.of(r1Resp, r2Resp));
    }

    @Test
    void shouldReturnEmptyListWhenSystemHasNoAvailableOnes() {
        when(rideRepository.findAll()).thenReturn(List.of());

        assertThat(sut.availableOnes()).isEmpty();
    }

    @Test
    void shouldReturnARideByUUID() {
        UUID uuid = UUID.randomUUID();
        RideDTO r1 = new RideDTO(
                UUID.randomUUID(),
                "Rua São João Bosco, 1324",
                "Av. Miguel Petroni, 321",
                LocalDateTime.now(),
                RideStatus.WAITING,
                driver.getId(),
                List.of(UUID.randomUUID())
        );
        RideResponseModel r1Resp = new RideResponseModel(RideStatus.WAITING, driver, LocalDateTime.now());

        when(rideRepository.findById(uuid)).thenReturn(Optional.of(r1));

        assertThat(sut.byId(uuid)).isEqualTo(r1Resp);
    }

    @Test
    void shouldThrowExceptionWhenRideDoesNotExist() {
        UUID uuid = UUID.randomUUID();

        when(rideRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatThrownBy(sut.getById(uuid)).isInstanceOf(Exception.class);
    }
}
