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
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;

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
