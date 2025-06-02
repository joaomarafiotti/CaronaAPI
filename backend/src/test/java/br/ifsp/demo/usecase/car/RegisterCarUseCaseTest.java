package br.ifsp.demo.usecase.car;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.LicensePlate;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.exception.LicensePlateAlreadyRegisteredException;
import br.ifsp.demo.models.request.CarRequestModel;
import br.ifsp.demo.models.response.CreateCarResponseModel;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.usecase.car.RegisterCarUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterCarUseCaseTest {

    @Mock
    CarRepository carRepository;

    @Mock
    DriverRepository driverRepository;

    @InjectMocks
    RegisterCarUseCase sut;

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @DisplayName("Should register car successfully and return car ID")
    void shouldRegisterCarSuccessfully() {
        Driver driver =  new Driver("Jose", "Alfredo", "joao@example.com","123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3,20));
        UUID driverId = driver.getId();

        CarRequestModel carRequest = new CarRequestModel(
                "Toyota", "Corolla", "Azul", 5, "ABC1D23"
        );

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        CreateCarResponseModel result = sut.execute(carRequest, driverId);

        assertNotNull(result);
        assertNotNull(result.id());
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @DisplayName("Should throw exception when driver not found")
    void shouldThrowExceptionWhenDriverNotFound() {
        UUID driverId = UUID.randomUUID();
        CarRequestModel carRequest = new CarRequestModel("Toyota", "Corolla", "Azul", 5, "ABC1D23");

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> sut.execute(carRequest, driverId));

        verify(driverRepository).findById(driverId);
        verify(carRepository, never()).save(Mockito.<Car>any());
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @DisplayName("Should throw exception when CarRequestModel is null")
    void shouldThrowExceptionWhenCarRequestModelIsNull() {
        assertThrows(IllegalArgumentException.class, () -> sut.execute(null, null));
    }

    @Test
    @Tag("Mutant")
    @Tag("UnitTest")
    @DisplayName("Should register car successfully into the driver car list")
    void shouldRegisterCarSuccessfullyIntoTheDriverCarList() {
        Driver driver =  new Driver("Jose", "Alfredo", "joao@example.com","123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3,20));
        UUID driverId = driver.getId();

        CarRequestModel carRequest = new CarRequestModel(
                "Toyota", "Corolla", "Azul", 5, "ABC1D23"
        );

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        sut.execute(carRequest, driverId);

        assertThat(driver.getCars()).hasSize(1);
    }

    @Test
    @Tag("Functional")
    @Tag("UnitTest")
    @DisplayName("Should throw exception when license plate is not valid")
    void shouldThrowExceptionWhenLicensePlateIsNotValid() {
        Driver driver =  new Driver("Jose", "Alfredo", "joao@example.com","123123BBdjk", Cpf.of("529.982.247-25"), LocalDate.of(2003, 3,20));
        UUID driverId = driver.getId();

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        CarRequestModel carRequest = new CarRequestModel(
                "Toyota", "Corolla", "Azul", 5, "ABC1PD23"
        );

        assertThrows(IllegalArgumentException.class, () -> sut.execute(carRequest, driverId));
    }

    @Test
    @Tag("Functional")
    @Tag("UnitTest")
    @DisplayName("Should throw exception when license plate is already registered")
    void shouldThrowExceptionWhenLicensePlateIsAlreadyRegistered() {
        Driver driver = new Driver("Jose", "Alfredo", "joao@example.com", "123123BBdjk",
                Cpf.of("529.982.247-25"), LocalDate.of(2003, 3, 20));
        UUID driverId = driver.getId();

        String plateString = "ABC-1234";
        LicensePlate plate = LicensePlate.parse(plateString);

        Car existingCar = new Car("Fiat", "Uno", "Preto", 5, plate);

        CarRequestModel carRequest = new CarRequestModel(
                "Toyota", "Corolla", "Azul", 5, plateString
        );

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(carRepository.findByLicensePlate(plate.toString())).thenReturn(Optional.of(existingCar));

        assertThrows(LicensePlateAlreadyRegisteredException.class, () -> sut.execute(carRequest, driverId));
    }

}
