package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.exception.RideNotFoundException;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.service.NotificationService;
import br.ifsp.demo.usecase.ride.CancelRideUseCase;
import br.ifsp.demo.utils.RideStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("TDD")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class CancelRideUseCaseTest {

    @Mock
    RideRepository rideRepository;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    CancelRideUseCase cancelRideUseCase;

    Address address0;
    Address address1;

    Ride ride;
    UUID rideId;
    UUID driverId;
    LocalDateTime futureTime;
    List<Passenger> passengers;

    @BeforeEach
    void setUp() {
        futureTime = LocalDateTime.now().plusHours(2);
        Driver driver = new Driver("Jose", "Alfredo", "joao@example.com", "123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3, 20));
        Car car = new Car("Fiat", "Uno", "Red", 5, "12345");

        passengers = List.of(
                new Passenger("João", "Matias", "joao@example.com", "31234BBds#", Cpf.of("123.456.789-09"), LocalDate.of(2000, 3, 12)),
                new Passenger("Maria", "Souza", "maria@example.com", "132BBj#da", Cpf.of("111.444.777-35"), LocalDate.of(1999, 1, 12))
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

        ride = new Ride(address0, address1, futureTime, driver, car);
        ride.setRideStatus(RideStatus.WAITING);
        ride.setPassengers(passengers);
        rideId = ride.getId();
        driverId = driver.getId();
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @DisplayName("Should cancel ride and notify passengers when ride is valid")
    void shouldCancelRideAndNotifyPassengers() {
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        cancelRideUseCase.execute(rideId, driverId);

        assertEquals(RideStatus.CANCELED, ride.getRideStatus());
        verify(rideRepository).save(ride);
        verify(notificationService).notifyPassengers(eq(passengers), anyString(), anyString());
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @DisplayName("Should throw RideNotFoundException when ride does not exist")
    void shouldThrowRideNotFoundExceptionWhenRideDoesNotExist() {
        when(rideRepository.findById(rideId)).thenReturn(Optional.empty());

        assertThrows(RideNotFoundException.class, () -> cancelRideUseCase.execute(rideId, driverId));
        verify(rideRepository, never()).save(any());
        verify(notificationService, never()).notifyPassengers(any(), any(), any());
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @DisplayName("Should throw IllegalStateException when ride is less than 1 hour away")
    void shouldThrowIllegalStateExceptionWhenRideIsLessThan1HourAway() {
        ride.setDepartureTime(LocalDateTime.now().plusMinutes(30));
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        assertThrows(IllegalStateException.class, () -> cancelRideUseCase.execute(rideId, driverId));

        verify(rideRepository, never()).save(any());
        verify(notificationService, never()).notifyPassengers(any(), any(), any());
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @ParameterizedTest
    @EnumSource(value = RideStatus.class, names = {"STARTED", "FULL", "CANCELED", "FINISHED"})
    @DisplayName("Should throw IllegalStateException when ride status is not WAITING")
    void shouldThrowExceptionWhenRideStatusIsNotWaiting(RideStatus invalidStatus) {
        ride.setRideStatus(invalidStatus);
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        assertThrows(IllegalStateException.class, () -> cancelRideUseCase.execute(rideId, driverId));

        verify(rideRepository, never()).save(any());
        verify(notificationService, never()).notifyPassengers(any(), any(), any());
    }

    @Tag("UnitTest")
    @Test
    @DisplayName("Should throw IllegalArgumentException when driver is not assigned driver")
    void shouldThrowExceptionWhenDriverIsNotAssigned() {
        UUID invalidDriverId = UUID.randomUUID();
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        assertThrows(IllegalArgumentException.class, () -> cancelRideUseCase.execute(rideId, invalidDriverId));

        verify(rideRepository, never()).save(any());
        verify(notificationService, never()).notifyPassengers(any(), any(), any());
    }

    @Tag("UnitTest")
    @Test
    @DisplayName("Should throw IllegalArgumentException when rideId is null")
    void shouldThrowExceptionWhenRideIdIsNull() {
        UUID driverId = ride.getDriver().getId();
        assertThrows(IllegalArgumentException.class, () -> cancelRideUseCase.execute(null, driverId));
    }

    @Tag("UnitTest")
    @Test
    @DisplayName("Should throw IllegalArgumentException when driverId is null")
    void shouldThrowExceptionWhenDriverIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> cancelRideUseCase.execute(rideId, null));
    }

}