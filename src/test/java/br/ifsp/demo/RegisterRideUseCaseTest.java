package br.ifsp.demo;

import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Ride;
import br.ifsp.demo.dto.RideRequestDTO;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.usecase.RegisterRideUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Driver driver = new Driver("John", "111.222.333-45", "john@gmail.com", LocalDate.of(2004, 5,6));
        UUID driverId = UUID.randomUUID();

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        LocalDateTime time = LocalDateTime.of(2025, 4, 30, 10, 0);
        RideRequestDTO rideDTO = new RideRequestDTO("São Paulo", "Campinas", time, driverId);

        boolean response = registerRideUseCase.execute(rideDTO);

        assertThat(response).isEqualTo(true);

        Ride expectedRide = new Ride("São Paulo", "Campinas", time, driver);

        verify(rideRepository).save(ArgumentMatchers.refEq(expectedRide));
    }
}
