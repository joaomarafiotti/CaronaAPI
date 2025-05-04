package br.ifsp.demo.usecase;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.exception.DriverNotFoundException;
import br.ifsp.demo.models.request.CarRequestModel;
import br.ifsp.demo.models.response.CreateCarResponseModel;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
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
        Driver driver = new Driver("João", "111.222.333-44", "joao@gmail.com", LocalDate.now());
        UUID driverId = driver.getId();

        CarRequestModel carRequest = new CarRequestModel(
                "Toyota", "Corolla", "Azul", 5, "ABC1D23", driverId
        );

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> {
            Car car = invocation.getArgument(0);
            car.setId(UUID.randomUUID());
            return car;
        });

        CreateCarResponseModel result = sut.execute(carRequest);

        assertNotNull(result);
        assertNotNull(result.id());
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @DisplayName("Should throw exception when driver not found")
    void shouldThrowExceptionWhenDriverNotFound() {
        UUID driverId = UUID.randomUUID();
        CarRequestModel carRequest = new CarRequestModel("Toyota", "Corolla", "Azul", 5, "ABC1D23", driverId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> sut.execute(carRequest));

        verify(driverRepository).findById(driverId);
        verify(carRepository, never()).save(Mockito.<Car>any());
    }

    @Test
    @Tag("TDD")
    @Tag("UnitTest")
    @DisplayName("Should throw exception when CarRequestModel is null")
    void shouldThrowExceptionWhenCarRequestModelIsNull() {
        assertThrows(IllegalArgumentException.class, () -> sut.execute(null));
    }
}
