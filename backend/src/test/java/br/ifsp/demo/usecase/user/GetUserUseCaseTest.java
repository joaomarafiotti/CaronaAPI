package br.ifsp.demo.usecase.user;

import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.security.user.JpaUserRepository;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserUseCaseTest {
    @Mock
    private JpaUserRepository userRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private DriverRepository driverRepository;

    private GetUserUseCase sut;

    @BeforeEach
    void setUp() {
        sut = new GetUserUseCase(passengerRepository, driverRepository, userRepository);
    }

    @Test
    @Tag("Functional")
    @Tag("UnitTest")
    @DisplayName("Should return user if is present in db")
    void shouldReturnUserIfIsPresentInDb() {
        User u1 = new User(
                UUID.randomUUID(),
                "José",
                "Alves",
                "user@gmail.com",
                Cpf.of("123.456.789-09"),
                LocalDate.of(2004, 5, 6),
                "password", Role.PASSENGER
        );

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(u1));
        assertThat(sut.getUserById(u1.getId())).isEqualTo(u1.toResponseModel());
    }

    @Test
    @Tag("Functional")
    @Tag("UnitTest")
    @DisplayName("Should throw entity not found exception if user is not present in db")
    void shouldThrowEntityNotFoundExceptionIfUserIsNotPresentInDb() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sut.getUserById(UUID.randomUUID()));
    }
}
