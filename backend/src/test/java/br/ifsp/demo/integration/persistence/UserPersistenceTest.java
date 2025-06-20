package br.ifsp.demo.integration.persistence;

import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.security.user.Role;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(br.ifsp.demo.config.TestConfig.class)
@ActiveProfiles("test")
@DisplayName("User Persistence Tests")
class UserPersistenceTest {
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
