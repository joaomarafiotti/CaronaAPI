package br.ifsp.demo;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.usecase.RegisterRideUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterRideUseCaseTest {
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private RideRepository rideRepository;
    @InjectMocks
    private RegisterRideUseCase registerRideUseCase;

    @Test
    public void testRegisterRideSuccessfully() {
        Driver driver = new Driver("John", "111.222.333-45", "john@gmail.com", LocalDate.of(2004, 5, 6));
        UUID driverId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now().plusDays(3).plusHours(10);
        RideRequestModel rideDTO = new RideRequestModel("São Paulo", "Campinas", time, driverId);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Retorna o próprio objeto salvo
        Ride response = registerRideUseCase.execute(rideDTO);

        Ride expectedRide = new Ride("São Paulo", "Campinas", time, driver);
        assertThat(response).isEqualTo(expectedRide);

        verify(rideRepository).save(eq(expectedRide));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidRideScenarios")
    public void testRegisterWithInvalidOptions(String scenario, RideRequestModel rideDTO, Class<? extends Exception> expectedException) {
        assertThrows(expectedException, () -> registerRideUseCase.execute(rideDTO));
        verify(rideRepository, never()).save(any());
    }

    static Stream<Arguments> invalidRideScenarios() {
        UUID validDriverId = UUID.randomUUID();
        LocalDateTime validTime = LocalDateTime.now().plusHours(1);

        return Stream.of(
                Arguments.of(
                        "Should fail when departure time is in past",
                        new RideRequestModel("São Paulo", "Campinas", LocalDateTime.now().minusHours(1), validDriverId),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        "Should fail when start address is equal to end address",
                        new RideRequestModel("São Paulo", "São Paulo", LocalDateTime.now().plusHours(1), validDriverId),
                        IllegalArgumentException.class
                )
        );
    }
}
