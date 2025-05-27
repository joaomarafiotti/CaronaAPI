package br.ifsp.demo.usecase.passenger;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.RideRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AbandonRideUseCaseTest {
    @Mock
    private Driver driver;
    @Mock
    private Car car;
    @Mock
    private RideRepository rideRepository;
    @InjectMocks
    private AbandonRideUseCase sut;

    private Address address0;
    private Address address1;

    private Ride ride;
    private Passenger p1;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

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

        ride = new Ride(
                address0,
                address1,
                now,
                driver,
                car
        );
        p1 = new Passenger("João", "Matias", "joao@example.com", "31234BBds#", Cpf.of("390.533.447-05"), LocalDate.of(2005, 03, 03));
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should abandon ride if passenger is in it")
    public void shouldAbandonRideIfPassengerIsInIt() {
        ride.addPassenger(p1);
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.of(ride));
        sut.abandonFor(p1.getId(), ride.getId());
        assertThat(ride.getPassengers()).isEmpty();
    }

    @Test
    @Tag("UnitTest")
    @Tag("TDD")
    @DisplayName("Should throws if passenger is not in the ride")
    public void shouldThrowIfPassengerIsNotInRide() {
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.of(ride));
        assertThrows(EntityNotFoundException.class, () -> sut.abandonFor(p1.getId(), ride.getId()));
    }
}
