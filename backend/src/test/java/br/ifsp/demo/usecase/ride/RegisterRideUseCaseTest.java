package br.ifsp.demo.usecase.ride;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.usecase.ride.RegisterRideUseCase;
import br.ifsp.demo.utils.RideStatus;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterRideUseCaseTest {
    @Mock
    DriverRepository driverRepository;
    @Mock
    CarRepository carRepository;
    @Mock
    RideRepository rideRepository;

    @InjectMocks
    RegisterRideUseCase registerRideUseCase;

    @Test
    @Tag("UnitTest")
    @Description("Should successfully register a ride when the driver and car are valid and found in the repository.")
    void testRegisterRideSuccessfully() {
        UUID driverId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        LocalDateTime departureTime = LocalDateTime.now().plusDays(3).plusHours(10);

        Address address0 = new Address.AddressBuilder()
                .street("Rua das Palmeiras")
                .number("250B")
                .neighborhood("Jardim América")
                .city("São Paulo")
                .build();

        Address address1 = new Address.AddressBuilder()
                .street("Av. Brasil")
                .number("1020")
                .neighborhood("Centro")
                .city("Rio de Janeiro")
                .build();

        Driver driver = new Driver("Jose", "Alfredo", "joao@example.com", "123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3, 20));
        Car car = new Car("Fiat", "Uno", "Red", 5, LicensePlate.parse("ABC3X12"));

        var rideDTO = new RideRequestModel(
                "Rua das Palmeiras, 250B, Jardim América, São Paulo",
                "Av. Brasil, 1020, Centro, Rio de Janeiro",
                departureTime,
                carId
        );

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = registerRideUseCase.execute(rideDTO, driverId);

        assertThat(response).isNotNull();
        assertThat(response.rideId()).isNotNull();

        ArgumentCaptor<Ride> captor = ArgumentCaptor.forClass(Ride.class);
        verify(rideRepository).save(captor.capture());
        Ride savedRide = captor.getValue();

        assertThat(savedRide.getStartAddress()).isEqualTo(address0);
        assertThat(savedRide.getEndAddress()).isEqualTo(address1);
        assertThat(savedRide.getDepartureTime()).isEqualTo(departureTime);
        assertThat(savedRide.getDriver()).isEqualTo(driver);
        assertThat(savedRide.getCar()).isEqualTo(car);
        assertThat(savedRide.getRideStatus()).isEqualTo(RideStatus.WAITING);
    }

    @Test
    @Tag("UnitTest")
    @Description("Should result in NoCarFoundException when driver has no car")
    void shouldFailWhenDriverHasNoCar() {
        UUID driverId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        LocalDateTime departureTime = LocalDateTime.now().plusDays(1);

        Driver driver = new Driver("Jose", "Alfredo", "joao@example.com", "123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3, 20));

        var rideDTO = new RideRequestModel("São Paulo", "Santos", departureTime, carId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> registerRideUseCase.execute(rideDTO, driverId));

        verify(rideRepository, never()).save(any());
    }


    @Test
    @Tag("UnitTest")
    @Description("Should result in DriverNotFoundException when driver not found")
    void shouldFailWhenDriverNotFound() {
        UUID driverId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        LocalDateTime departureTime = LocalDateTime.now().plusDays(1);

        var rideDTO = new RideRequestModel("São Paulo", "Santos", departureTime, carId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> registerRideUseCase.execute(rideDTO, driverId));

        verify(carRepository, never()).findById(any());
        verify(rideRepository, never()).save(any());
    }

    @Test
    @Tag("UnitTest")
    @Description("Should fail when departure time is less than one hour")
    void shouldFailWhenDepartureTimeIsLessThanOneHour() {
        UUID driverId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        LocalDateTime departureTime = LocalDateTime.now().plusMinutes(30); // Less than 1 hour

        var rideDTO = new RideRequestModel("São Paulo", "Santos", departureTime, carId);

        assertThrows(IllegalArgumentException.class, () -> registerRideUseCase.execute(rideDTO, driverId));

        verify(driverRepository, never()).findById(any());
        verify(carRepository, never()).findById(any());
        verify(rideRepository, never()).save(any());
    }

    @Test
    @Tag("Structural")
    @Tag("UnitTest")
    @DisplayName("Should Throw IllegalArgumentException when ride request is null")
    void shouldThrowIllegalArgumentExceptionWhenRideRequestIsNull() {
        UUID driverId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> registerRideUseCase.execute(null, driverId));
    }

    @Test
    @Tag("Structural")
    @Tag("UnitTest")
    @DisplayName("Should Throw IllegalArgumentException when starting and arrival points are equals")
    void shouldThrowIllegalArgumentExceptionWhenStartingAndArrivalPointsAreEquals() {
        UUID driverId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        LocalDateTime departureTime = LocalDateTime.now().plusMinutes(30);
        var rideDTO = new RideRequestModel("Santos", "Santos", departureTime, carId);
        assertThrows(IllegalArgumentException.class, () -> registerRideUseCase.execute(rideDTO, driverId));
    }
}
