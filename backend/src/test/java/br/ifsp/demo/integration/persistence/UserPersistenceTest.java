package br.ifsp.demo.integration.persistence;

import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.security.user.JpaUserRepository;
import br.ifsp.demo.security.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(br.ifsp.demo.config.TestConfig.class)
@ActiveProfiles("test")
@DisplayName("User Persistence Tests")
class UserPersistenceTest {
    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Driver driver;
    private Passenger passenger;
    private Driver anotherDriver;
    private Passenger anotherPassenger;

    @BeforeEach
    void setUp() {
        entityManager.clear();

        driver = createDriver("Fulano", "Silva", "fulano.silva@gmail.com", "005.046.860-03");
        anotherDriver = createDriver("Ciclano", "Souza", "ciclano.souza@outlook.com", "355.553.060-75");
        passenger = createPassenger("Maria", "Oliveira", "maria.oliveira@hotmail.com", "561.506.860-43");
        anotherPassenger = createPassenger("Ana", "Souza", "ana.souza@yahoo.com", "936.138.620-42");
    }

    @Nested
    @DisplayName("Driver Persistence Tests")
    class DriverPersistenceTests {

        @Test
        @DisplayName("Should persist driver with all valid data")
        void shouldPersistDriverWithValidData() {
            Driver savedDriver = userRepository.save(driver);
            entityManager.flush();
            entityManager.clear();

            assertThat(savedDriver.getId()).isNotNull();
            assertDriverProperties(savedDriver, "Fulano", "Silva", "fulano.silva@gmail.com", "005.046.860-03");
            assertThat(savedDriver.getRole()).isEqualTo(Role.DRIVER);
            assertThat(savedDriver.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));

            Optional<Driver> foundDriver = userRepository.findById(savedDriver.getId())
                    .map(user -> (Driver) user);
            assertThat(foundDriver).isPresent();
            assertDriverProperties(foundDriver.get(), "Fulano", "Silva", "fulano.silva@gmail.com", "005.046.860-03");
        }

        @Test
        @DisplayName("Should persist driver with minimum age")
        void shouldPersistDriverWithMinimumAge() {
            Driver youngDriver = new Driver(
                    "Menor",
                    "Idade",
                    "menor.idade@gmail.com",
                    "password123",
                    Cpf.of("864.216.480-88"),
                    LocalDate.now().minusYears(18)
            );

            Driver savedDriver = userRepository.save(youngDriver);

            assertThat(savedDriver.getId()).isNotNull();
            assertThat(savedDriver.getBirthDate()).isEqualTo(LocalDate.now().minusYears(18));
        }

        @Test
        @DisplayName("Should persist driver with maximum age")
        void shouldPersistDriverWithMaximumAge() {
            Driver oldDriver = new Driver(
                    "Old",
                    "Driver",
                    "old.driver@gmail.com",
                    "password123",
                    Cpf.of("046.052.980-37"),
                    LocalDate.of(1950, 1, 1)
            );

            Driver savedDriver = userRepository.save(oldDriver);

            assertThat(savedDriver.getId()).isNotNull();
            assertThat(savedDriver.getBirthDate()).isEqualTo(LocalDate.of(1950, 1, 1));
        }

        @Test
        @DisplayName("Should fail when saving driver with duplicate email")
        void shouldFailWhenSavingDriverWithDuplicateEmail() {
            userRepository.save(driver);
            entityManager.flush();

            Driver duplicateEmailDriver = new Driver(
                    "Another",
                    "Driver",
                    "fulano.silva@gmail.com",
                    "password123",
                    Cpf.of("674.750.280-97"),
                    LocalDate.of(1985, 5, 5)
            );

            assertThatThrownBy(() -> {
                userRepository.save(duplicateEmailDriver);
                entityManager.flush();
            }).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        @DisplayName("Should fail when saving driver with duplicate CPF")
        void shouldFailWhenSavingDriverWithDuplicateCpf() {
            userRepository.save(driver);
            entityManager.flush();

            Driver duplicateCpfDriver = new Driver(
                    "Another",
                    "Driver",
                    "another.driver@gmail.com",
                    "password123",
                    Cpf.of("005.046.860-03"),
                    LocalDate.of(1985, 5, 5)
            );

            assertThatThrownBy(() -> {
                userRepository.save(duplicateCpfDriver);
                entityManager.flush();
            }).isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    @Nested
    @DisplayName("Passenger Persistence Tests")
    class PassengerPersistenceTests {

        @Test
        @DisplayName("Should persist passenger with all valid data")
        void shouldPersistPassengerWithValidData() {
            Passenger savedPassenger = userRepository.save(passenger);
            entityManager.flush();
            entityManager.clear();

            assertThat(savedPassenger.getId()).isNotNull();
            assertPassengerProperties(savedPassenger, "Maria", "Oliveira", "maria.oliveira@hotmail.com", "561.506.860-43");
            assertThat(savedPassenger.getRole()).isEqualTo(Role.PASSENGER);
            assertThat(savedPassenger.getBirthDate()).isEqualTo(LocalDate.of(1992, 2, 2));

            Optional<Passenger> foundPassenger = userRepository.findById(savedPassenger.getId())
                    .map(user -> (Passenger) user);
            assertThat(foundPassenger).isPresent();
            assertPassengerProperties(foundPassenger.get(), "Maria", "Oliveira", "maria.oliveira@hotmail.com", "561.506.860-43");
        }

        @Test
        @DisplayName("Should persist passenger with minimum age")
        void shouldPersistPassengerWithMinimumAge() {
            Passenger youngPassenger = new Passenger(
                    "Young",
                    "Passenger",
                    "young.passenger@gmail.com",
                    "password123",
                    Cpf.of("764.011.570-11"),
                    LocalDate.now().minusYears(16)
            );

            Passenger savedPassenger = userRepository.save(youngPassenger);

            assertThat(savedPassenger.getId()).isNotNull();
            assertThat(savedPassenger.getBirthDate()).isEqualTo(LocalDate.now().minusYears(16));
        }

        @Test
        @DisplayName("Should fail when saving passenger with duplicate email")
        void shouldFailWhenSavingPassengerWithDuplicateEmail() {
            userRepository.save(passenger);
            entityManager.flush();

            Passenger duplicateEmailPassenger = new Passenger(
                    "Another",
                    "Passenger",
                    "maria.oliveira@hotmail.com",
                    "password123",
                    Cpf.of("118.468.960-10"),
                    LocalDate.of(1990, 3, 3)
            );

            assertThatThrownBy(() -> {
                userRepository.save(duplicateEmailPassenger);
                entityManager.flush();
            }).isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    private Driver createDriver(String firstName, String lastName, String email, String cpf) {
        return new Driver(firstName, lastName, email, "password123",
                Cpf.of(cpf), LocalDate.of(1990, 1, 1));
    }

    private Passenger createPassenger(String firstName, String lastName, String email, String cpf) {
        return new Passenger(firstName, lastName, email, "password123",
                Cpf.of(cpf), LocalDate.of(1992, 2, 2));
    }

    private void assertDriverProperties(Driver driver, String name, String lastname, String email, String cpf) {
        assertThat(driver.getName()).isEqualTo(name);
        assertThat(driver.getLastname()).isEqualTo(lastname);
        assertThat(driver.getEmail()).isEqualTo(email);
        assertThat(driver.getCpf().toString()).isEqualTo(cpf);
        assertThat(driver.getRole()).isEqualTo(Role.DRIVER);
    }

    private void assertPassengerProperties(Passenger passenger, String name, String lastname, String email, String cpf) {
        assertThat(passenger.getName()).isEqualTo(name);
        assertThat(passenger.getLastname()).isEqualTo(lastname);
        assertThat(passenger.getEmail()).isEqualTo(email);
        assertThat(passenger.getCpf().toString()).isEqualTo(cpf);
        assertThat(passenger.getRole()).isEqualTo(Role.PASSENGER);
    }
}
