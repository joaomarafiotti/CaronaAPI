package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
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

    Ride ride;
    UUID rideId;
    LocalDateTime futureTime;
    List<Passenger> passengers;

    @BeforeEach
    void setUp() {
        futureTime = LocalDateTime.now().plusHours(2);
        Driver driver = new Driver("Ana", "222.333.444-55", "ana@gmail.com", LocalDate.of(1990, 1, 1));
        Car car = new Car("Fiat", "Uno", "Red", 5, "12345");

        passengers = List.of(
                new Passenger("João", "joao@example.com", "456.111.333-45"),
                new Passenger("Maria",  "maria@example.com","456.111.333-34")
        );

        ride = new Ride("São Paulo", "Campinas", futureTime, driver, car);
        ride.setRideStatus(RideStatus.WAITING);
        ride.setPassengers(passengers);
        rideId = ride.getId();
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @DisplayName("Should cancel ride and notify passengers when ride is valid")
    void shouldCancelRideAndNotifyPassengers() {
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        cancelRideUseCase.execute(rideId);

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

        assertThrows(RideNotFoundException.class, () -> cancelRideUseCase.execute(rideId));
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

        assertThrows(IllegalStateException.class, () -> cancelRideUseCase.execute(rideId));

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

        assertThrows(IllegalStateException.class, () -> cancelRideUseCase.execute(rideId));

        verify(rideRepository, never()).save(any());
        verify(notificationService, never()).notifyPassengers(any(), any(), any());
    }
}