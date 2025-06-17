package br.ifsp.demo.integration.persistence;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.Driver;
import br.ifsp.demo.domain.LicensePlate;
import br.ifsp.demo.repositories.CarRepository;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(br.ifsp.demo.config.TestConfig.class)
@ActiveProfiles("test")
@DisplayName("Car Persistence Tests")
class CarPersistenceTest {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Driver driver;
    private Driver anotherDriver;

    @BeforeEach
    void setUp() {
        entityManager.clear();

        driver = createDriver("Fulano", "Silva", "fulano.silva@gmail.com", "005.046.860-03");
        anotherDriver = createDriver("Ciclano", "Souza", "ciclano.souza@outlook.com", "355.553.060-75");
    }

    @Nested
    @DisplayName("Car Creation Tests")
    class CarCreationTests {

        @Test
        @DisplayName("Should persist car with all valid data")
        void shouldPersistCarWithValidData() {
            Car car = createValidCar("Volkswagen", "Passat", "ABC-1234");
            car.setDriver(driver);

            Car savedCar = carRepository.save(car);
            entityManager.flush();
            entityManager.clear();

            assertThat(savedCar.getId()).isNotNull();
            assertCarProperties(savedCar, "Volkswagen", "Passat", "Black", 5, "ABC-1234");
            assertThat(savedCar.getDriver()).isEqualTo(driver);
            assertThat(savedCar.getIsActive()).isTrue();

            Optional<Car> foundCar = carRepository.findById(savedCar.getId());
            assertThat(foundCar).isPresent();
            assertCarProperties(foundCar.get(), "Volkswagen", "Passat", "Black", 5, "ABC-1234");
        }

        @Test
        @DisplayName("Should persist car with minimum required data")
        void shouldPersistCarWithMinimumData() {
            Car car = new Car("Chevrolet", "Malibu", "White", 4, LicensePlate.parse("XYZ-9876"));
            car.setDriver(driver);
            Car savedCar = carRepository.save(car);

            assertThat(savedCar.getId()).isNotNull();
            assertThat(savedCar.getIsActive()).isTrue();
            assertThat(savedCar.getDriver()).isEqualTo(driver);
        }

        @Test
        @DisplayName("Should fail when trying to save car without driver")
        void shouldFailWhenSavingCarWithoutDriver() {
            Car car = createValidCar("Volkswagen", "Passat", "ABC-1234");

            assertThatThrownBy(() -> {
                carRepository.save(car);
                entityManager.flush();
            }).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        @DisplayName("Should fail when trying to save car with duplicate license plate")
        void shouldFailWhenSavingCarWithDuplicateLicensePlate() {
            Car firstCar = createValidCar("Volkswagen", "Passat", "ABC-1234");
            firstCar.setDriver(driver);
            carRepository.save(firstCar);
            entityManager.flush();

            Car secondCar = createValidCar("Chevrolet", "Malibu", "ABC-1234");
            secondCar.setDriver(anotherDriver);

            assertThatThrownBy(() -> {
                carRepository.save(secondCar);
                entityManager.flush();
            }).isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    @Nested
    @DisplayName("Car Query Tests")
    class CarQueryTests {

        @Test
        @DisplayName("Should find car by license plate")
        void shouldFindCarByLicensePlate() {
            Car car = createValidCar("Volkswagen", "Passat", "ABC-1234");
            car.setDriver(driver);
            carRepository.save(car);
            entityManager.flush();
            entityManager.clear();

            Optional<Car> foundCar = carRepository.findByLicensePlate(LicensePlate.parse("ABC-1234"));

            assertThat(foundCar).isPresent();
            assertCarProperties(foundCar.get(), "Volkswagen", "Passat", "Black", 5, "ABC-1234");
            assertThat(foundCar.get().getDriver()).isEqualTo(driver);
        }
    }
}
