package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.exception.CarInUseException;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveCarUseCaseTest {

    @Mock
    CarRepository carRepository;
    @Mock
    DriverRepository driverRepository;

    @Mock
    RideRepository rideRepository;

    @InjectMocks
    RemoveCarUseCase removeCarUseCase;

    UUID driverId;
    UUID carId;
    Driver driver;
    Car car;

    @BeforeEach
    void setUp() {
        car = new Car("Fiat", "Uno","Red",5, "ABC1234");
        driver =  new Driver("Jose", "Alfredo", "joao@example.com","123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3,20));
        carId = car.getId();
        driverId = driver.getId();
        driver.addCar(car);
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @Description("Should Remove Car successfully when driverId and carId ar valid")
    void shouldRemoveCarSuccessfully() {
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        removeCarUseCase.execute(driverId, carId);

        verify(driverRepository).save(driver);
        verify(carRepository).deleteById(carId);
        assertFalse(driver.getCars().contains(car));
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @Description("Should throw driver not found exception when driver is not registered")
    void shouldThrowDriverNotFoundException() {
        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> removeCarUseCase.execute(driverId, carId));

        verify(driverRepository, never()).save(any());
        verify(carRepository, never()).deleteById(any());
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @Description("Should throw car not found exception when carId does not exists")
    void shouldThrowCarNotFoundException() {
        driver.getCars().clear();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        assertThrows(CarNotFoundException.class, () -> removeCarUseCase.execute(driverId, carId));

        verify(driverRepository, never()).save(any());
        verify(carRepository, never()).deleteById(any());
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @Description("Should not remove car if it belongs to another driver")
    void shouldNotRemoveCarIfItBelongsToAnotherDriver() {
        UUID otherCarId = UUID.randomUUID();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        assertThrows(CarNotFoundException.class, () -> removeCarUseCase.execute(driverId, otherCarId));

        verify(driverRepository, never()).save(any());
        verify(carRepository, never()).deleteById(any());
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @Description("Should only remove specified car when multiple cars exists")
    void shouldOnlyRemoveSpecifiedCarWhenMultipleCarsExist() {
        Car car2 = new Car("Ford", "Ka", "Blue", 5, "DEF5678");
        driver.addCar(car2);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        removeCarUseCase.execute(driverId, carId);

        assertFalse(driver.getCars().contains(car));
        assertTrue(driver.getCars().contains(car2));

        verify(driverRepository).save(driver);
        verify(carRepository).deleteById(carId);
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @Description("Should throw car not found exception when driver has no cars")
    void shouldThrowCarNotFoundExceptionWhenDriverHasNoCars() {
        driver.getCars().clear();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        assertThrows(CarNotFoundException.class, () -> removeCarUseCase.execute(driverId, carId));
    }

    @Test
    void throwsExceptionIfCarIsLinkedToRide() {
        Ride ride = new Ride("São Paulo", "Campinas", LocalDateTime.now().plusDays(2), driver, car);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(rideRepository.findRideByDriver_Id(driverId)).thenReturn(List.of(ride));

        assertThrows(CarInUseException.class, () -> {
            removeCarUseCase.execute(driverId, carId);
        });
    }
}
