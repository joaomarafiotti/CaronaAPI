package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.Address;
import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.models.response.RideResponseModel;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.utils.RideStatus;
import org.junit.jupiter.api.BeforeEach;
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

    Address address0;
    Address address1;
    Address address2;
    Address address3;
    Address address4;
    Address address5;
    Address address6;
    Address address7;
    Address address8;
    Address address9;

    @BeforeEach
    void setUp() {
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
        address4 = new Address.AddressBuilder()
                .street("Rua Padre Anchieta")
                .number("456")
                .neighborhood("Centro")
                .city("Curitiba")
                .build();
        address5 = new Address.AddressBuilder()
                .street("Av. Paulista")
                .number("1578")
                .neighborhood("Bela Vista")
                .city("São Paulo")
                .build();
        address6 = new Address.AddressBuilder()
                .street("Rua XV de Novembro")
                .number("100")
                .neighborhood("Centro Histórico")
                .city("Joinville")
                .build();
        address7 = new Address.AddressBuilder()
                .street("Av. Brasil")
                .number("900C")
                .neighborhood("Floresta")
                .city("Porto Alegre")
                .build();
        address8 = new Address.AddressBuilder()
                .street("Rua do Sol")
                .number("11")
                .neighborhood("Barra")
                .city("Salvador")
                .build();
        address9 = new Address.AddressBuilder()
                .street("Av. Amazonas")
                .number("2305")
                .neighborhood("Aleixo")
                .city("Manaus")
                .build();
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should return a ride list with rides with waiting and full status")
    void shouldReturnARideListWithRidesWithWaitingAndFullStatus() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime r2Start = now.plusMinutes(90);
        LocalDateTime r3Start = now.plusHours(1);
        LocalDateTime r4Start = now.plusHours(2).plusMinutes(45);
        LocalDateTime r5Start = now.plusDays(1).plusHours(3);

        Ride r1 = new Ride(
                address0,
                address1,
                now,
                driver,
                car
        );
        Ride r2 = new Ride(
                address2,
                address3,
                r2Start,
                driver,
                car
        );
        Ride r3 = new Ride(
                address4,
                address5,
                r3Start,
                driver,
                car
        );
        Ride r4 = new Ride(
                address6,
                address7,
                r4Start,
                driver,
                car
        );
        Ride r5 = new Ride(
                address8,
                address9,
                r5Start,
                driver,
                car
        );
        r1.setRideStatus(RideStatus.WAITING);
        r2.setRideStatus(RideStatus.FULL);
        r3.setRideStatus(RideStatus.CANCELLED);
        r4.setRideStatus(RideStatus.FINISHED);
        r5.setRideStatus(RideStatus.STARTED);

        RideResponseModel r1Resp = r1.toResponseModel();
        RideResponseModel r2Resp = r2.toResponseModel();

        when(rideRepository.findAll()).thenReturn(List.of(r1, r2, r3, r4, r5));

        assertThat(sut.availableOnes(UUID.randomUUID())).isEqualTo(List.of(r1Resp, r2Resp));
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should return empty list when system has no available ones")
    void shouldReturnEmptyListWhenSystemHasNoAvailableOnes() {
        when(rideRepository.findAll()).thenReturn(List.of());

        assertThat(sut.availableOnes(UUID.randomUUID())).isEmpty();
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should return a ride by UUID")
    void shouldReturnARideByUUID() {
        LocalDateTime now = LocalDateTime.now();
        Ride r1 = new Ride(
                address0,
                address1,
                now,
                driver,
                car
        );

        r1.setRideStatus(RideStatus.WAITING);
        RideResponseModel r1Resp = r1.toResponseModel();

        when(rideRepository.findById(r1.getId())).thenReturn(Optional.of(r1));

        assertThat(sut.byId(r1.getId())).isEqualTo(r1Resp);
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should throw exception when ride does not exist")
    void shouldThrowExceptionWhenRideDoesNotExist() {
        UUID uuid = UUID.randomUUID();

        when(rideRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> sut.byId(uuid));
    }

    @Test
    @Tag("UnitTest")
    @Tag("Functional")
    @DisplayName("Should return a list of rides by driver ID")
    void shouldReturnAListOfRidesByDriverId() {
        UUID driverId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Ride r1 = new Ride(address0, address1, now, driver, car);

        Ride r2 = new Ride(address2, address3, now.plusHours(1), driver, car);

        RideResponseModel r1Resp = r1.toResponseModel();

        RideResponseModel r2Resp = r2.toResponseModel();

        when(rideRepository.findRideByDriver_Id(driverId)).thenReturn(List.of(r1, r2));

        List<RideResponseModel> result = sut.byDriverId(driverId);

        assertThat(result).isEqualTo(List.of(r1Resp, r2Resp));
    }

    @Test
    @Tag("UnitTest")
    @Tag("Functional")
    @DisplayName("Should return a list of rides by passenger ID")
    void shouldReturnAListOfRidesByPassengerId() {
        UUID passengerId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Ride r1 = new Ride(address4, address5, now, driver, car);

        Ride r2 = new Ride(address6, address7, now.plusHours(2), driver, car);

        RideResponseModel r1Resp = r1.toResponseModel();

        RideResponseModel r2Resp = r2.toResponseModel();

        when(rideRepository.findRideByPassengers_Id(passengerId)).thenReturn(List.of(r1, r2));

        List<RideResponseModel> result = sut.byPassengerId(passengerId);

        assertThat(result).isEqualTo(List.of(r1Resp, r2Resp));
    }
}