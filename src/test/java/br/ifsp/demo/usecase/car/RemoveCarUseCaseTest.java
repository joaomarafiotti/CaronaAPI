package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.exception.CarInUseException;
import br.ifsp.demo.exception.CarNotFoundException;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
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
    RemoveCarUseCase sut;

    Address address0;
    Address address1;

    UUID driverId;
    UUID carId;
    Driver driver;
    Car car;

    @BeforeEach
    void setUp() {
        car = new Car("Fiat", "Uno", "Red", 5, LicensePlate.parse("ABC-1234"));
        driver = new Driver("Jose", "Alfredo", "joao@example.com", "123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3, 20));
        carId = car.getId();
        driverId = driver.getId();
        driver.addCar(car);
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
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @Description("Should Remove Car successfully when driverId and carId ar valid")
    void shouldRemoveCarSuccessfully() {
        Ride ride = new Ride(address0, address1, LocalDateTime.now().plusDays(2), driver, car);
        Car otherCar = new Car("Ford", "Ka", "Blue", 5, LicensePlate.parse("DEF5J78"));
        driver.addCar(otherCar);
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(rideRepository.findRideByDriver_Id(driverId)).thenReturn(List.of(ride));

        sut.execute(driverId, otherCar.getId());

        verify(driverRepository).save(driver);
        verify(carRepository).deleteById(otherCar.getId());
        assertFalse(driver.getCars().contains(otherCar));
    }

    @Tag("TDD")
    @Tag("UnitTest")
    @Test
    @Description("Should throw driver not found exception when driver is not registered")
    void shouldThrowDriverNotFoundException() {
        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> sut.execute(driverId, carId));

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

        assertThrows(CarNotFoundException.class, () -> sut.execute(driverId, carId));

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

        assertThrows(CarNotFoundException.class, () -> sut.execute(driverId, otherCarId));

        verify(driverRepository, never()).save(any());
        verify(carRepository, never()).deleteById(any());
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @Description("Should only remove specified car when multiple cars exists")
    void shouldOnlyRemoveSpecifiedCarWhenMultipleCarsExist() {
        Car car2 = new Car("Ford", "Ka", "Blue", 5, LicensePlate.parse("DEF5C78"));
        driver.addCar(car2);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        sut.execute(driverId, carId);

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

        assertThat(driver.getCars()).isEmpty();
        assertThrows(CarNotFoundException.class, () -> sut.execute(driverId, carId));
    }

    @Test
    void throwsExceptionIfCarIsLinkedToRide() {
        Ride ride = new Ride(address0, address1, LocalDateTime.now().plusDays(2), driver, car);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(rideRepository.findRideByDriver_Id(driverId)).thenReturn(List.of(ride));

        assertThrows(CarInUseException.class, () -> {
            sut.execute(driverId, carId);
        });
    }

    @Test
    @Tag("Structural")
    @Tag("UnitTest")
    @DisplayName("Should throw if ride car is null")
    void shouldThrowIfRideCarIsNull() {
        Ride ride = new Ride(address0, address1, LocalDateTime.now().plusDays(2), driver, null);
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(rideRepository.findRideByDriver_Id(driverId)).thenReturn(List.of(ride));
        assertThrows(IllegalStateException.class, () -> {
            sut.execute(driverId, carId);
        });
    }

    @Test
    @Tag("Mutant")
    @Tag("UnitTest")
    @DisplayName("should throws if the driver is not the owner of the car")
    void shouldThrowsIfTheDriverIsNotTheOwnerOfTheCar() {
        UUID otherCarId = UUID.randomUUID();

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        try {
            sut.execute(driverId, otherCarId);
        } catch (CarNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("driver is not the owner of car with id: " + otherCarId);
        }
    }
}
