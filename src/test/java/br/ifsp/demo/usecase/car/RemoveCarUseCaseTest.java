package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveCarUseCaseTest {

    @Mock
    CarRepository carRepository;
    @Mock
    DriverRepository driverRepository;

    @InjectMocks
    RemoveCarUseCase removeCarUseCase;

    UUID driverId;
    UUID carId;
    Driver driver;
    Car car;

    @BeforeEach
    void setUp() {
        car = new Car("Fiat", "Uno","Red",5, "ABC1234");
        driver = new Driver("João", "444.777.888-55", "joao@example.com", LocalDate.now());
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
}
