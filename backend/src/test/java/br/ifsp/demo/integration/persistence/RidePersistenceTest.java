package br.ifsp.demo.integration.persistence;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.CarRepository;
import br.ifsp.demo.repositories.DriverRepository;
import br.ifsp.demo.repositories.PassengerRepository;
import br.ifsp.demo.repositories.RideRepository;
import br.ifsp.demo.utils.RideStatus;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(br.ifsp.demo.config.TestConfig.class)
@ActiveProfiles("test")
@DisplayName("Ride Persistence Tests")
class RidePersistenceTest {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Driver driver;
    private Driver anotherDriver;
    private Car car;
    private Car anotherCar;
    private Passenger passenger;
    private Passenger anotherPassenger;
    private Address startAddress;
    private Address endAddress;
    private Address alternativeAddress;

    @BeforeEach
    void setUp() {
        entityManager.clear();

        driver = createDriver("Fulano", "Silva", "fulano.silva@gmail.com", "005.046.860-03");
        anotherDriver = createDriver("Ciclano", "Souza", "ciclano.souza@outlook.com", "355.553.060-75");

        car = createCar("Volkswagen", "Passat", "ABC-1234", driver);
        anotherCar = createCar("Chevrolet", "Malibu", "XYZ-5678", anotherDriver);

        passenger = createPassenger("Maria", "Oliveira", "maria.oliveira@hotmail.com", "561.506.860-43");
        anotherPassenger = createPassenger("Ana", "Souza", "ana.souza@yahoo.com", "936.138.620-42");

        startAddress = createAddress("Alameda das Hortências", "Centro", "123", "São Carlos");
        endAddress = createAddress("Rua das Orquídeas", "Jardins", "456", "São Carlos");
        alternativeAddress = createAddress("Alameda dos crisântemos", "Jardins", "789", "São Carlos");
    }

    @Nested
    @DisplayName("Ride Creation Tests")
    class RideCreationTests {

        @Test
        @DisplayName("Should persist ride with all valid data")
        void shouldPersistRideWithValidData() {
            LocalDateTime departureTime = LocalDateTime.now().plusHours(1);
            Ride ride = new Ride(startAddress, endAddress, departureTime, driver, car);

            Ride savedRide = rideRepository.save(ride);
            entityManager.flush();
            entityManager.clear();

            assertThat(savedRide.getId()).isNotNull();
            assertRideProperties(savedRide, startAddress, endAddress, driver, car);
            assertThat(savedRide.getRideStatus()).isEqualTo(RideStatus.WAITING);
            assertThat(savedRide.getDepartureTime()).isEqualTo(departureTime);

            Optional<Ride> foundRide = rideRepository.findById(savedRide.getId());
            assertThat(foundRide).isPresent();
            assertRideProperties(foundRide.get(), startAddress, endAddress, driver, car);
        }
    }

    private Driver createDriver(String firstName, String lastName, String email, String cpf) {
        Driver driver = new Driver(firstName, lastName, email, "password123",
                Cpf.of(cpf), LocalDate.of(1990, 1, 1));
        return driverRepository.save(driver);
    }

    private Car createCar(String brand, String model, String licensePlate, Driver driver) {
        Car car = new Car(brand, model, "Black", 5, LicensePlate.parse(licensePlate));
        car.setDriver(driver);
        return carRepository.save(car);
    }

    private Passenger createPassenger(String firstName, String lastName, String email, String cpf) {
        Passenger passenger = new Passenger(firstName, lastName, email, "password123",
                Cpf.of(cpf), LocalDate.of(1992, 2, 2));
        return passengerRepository.save(passenger);
    }

    private Address createAddress(String street, String neighborhood, String number, String city) {
        return Address.builder()
                .street(street)
                .neighborhood(neighborhood)
                .number(number)
                .city(city)
                .build();
    }

    private void assertRideProperties(Ride ride, Address startAddr, Address endAddr, Driver driver, Car car) {
        assertThat(ride.getStartAddress()).isEqualTo(startAddr);
        assertThat(ride.getEndAddress()).isEqualTo(endAddr);
        assertThat(ride.getDriver()).isEqualTo(driver);
        assertThat(ride.getCar()).isEqualTo(car);
    }
}
