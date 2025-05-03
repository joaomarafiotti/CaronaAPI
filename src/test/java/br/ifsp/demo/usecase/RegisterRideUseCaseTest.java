package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.utils.RideStatus;
import jdk.jfr.Description;
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

        Driver driver = new Driver("John", "111.222.333-45", "john@gmail.com", LocalDate.of(2004, 5, 6));
        Car car = new Car("Fiat", "Uno", "Red", 5, "ABC3X12");

        var rideDTO = new RideRequestModel("São Paulo", "Campinas", departureTime, driverId, carId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = registerRideUseCase.execute(rideDTO);

        assertThat(response).isNotNull();
        assertThat(response.rideId()).isNotNull();

        ArgumentCaptor<Ride> captor = ArgumentCaptor.forClass(Ride.class);
        verify(rideRepository).save(captor.capture());
        Ride savedRide = captor.getValue();

        assertThat(savedRide.getStartAddress()).isEqualTo("São Paulo");
        assertThat(savedRide.getEndAddress()).isEqualTo("Campinas");
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

        Driver driver = new Driver("Ana", "222.333.444-55", "ana@gmail.com", LocalDate.of(1990, 1, 1));

        var rideDTO = new RideRequestModel("São Paulo", "Santos", departureTime, driverId, carId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> registerRideUseCase.execute(rideDTO));

        verify(rideRepository, never()).save(any());
    }


    @Test
    @Tag("UnitTest")
    @Description("Should result in DriverNotFoundException when driver not found")
    void shouldFailWhenDriverNotFound() {
        UUID driverId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        LocalDateTime departureTime = LocalDateTime.now().plusDays(1);

        var rideDTO = new RideRequestModel("São Paulo", "Santos", departureTime, driverId, carId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> registerRideUseCase.execute(rideDTO));

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

        var rideDTO = new RideRequestModel("São Paulo", "Santos", departureTime, driverId, carId);

        assertThrows(IllegalArgumentException.class, () -> registerRideUseCase.execute(rideDTO));

        verify(driverRepository, never()).findById(any());
        verify(carRepository, never()).findById(any());
        verify(rideRepository, never()).save(any());
    }
}
