package br.ifsp.demo.integration.persistence;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.*;
import br.ifsp.demo.utils.RideSolicitationStatus;
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

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(br.ifsp.demo.config.TestConfig.class)
@ActiveProfiles("test")
@DisplayName("RideSolicitation Persistence Tests")
class RideSolicitationPersistenceTest {

    @Autowired
    private RideSolicitationRepository solicitationRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

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
    private Ride ride;
    private Ride anotherRide;
    private Address startAddress;
    private Address endAddress;

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

        ride = createRide(startAddress, endAddress, driver, car);
        anotherRide = createRide(endAddress, startAddress, anotherDriver, anotherCar);
    }

    @Nested
    @DisplayName("RideSolicitation Creation Tests")
    class RideSolicitationCreationTests {

        @Test
        @DisplayName("Should persist ride solicitation with valid data")
        void shouldPersistRideSolicitationWithValidData() {
            RideSolicitation solicitation = new RideSolicitation(ride, passenger);

            RideSolicitation savedSolicitation = solicitationRepository.save(solicitation);
            entityManager.flush();
            entityManager.clear();

            assertThat(savedSolicitation.getId()).isNotNull();
            assertThat(savedSolicitation.getStatus()).isEqualTo(RideSolicitationStatus.WAITING);

            Optional<RideSolicitation> foundSolicitation = solicitationRepository.findById(savedSolicitation.getId());
            assertThat(foundSolicitation).isPresent();

            Optional<Ride> reloadedRide = rideRepository.findById(ride.getId());
            Optional<Passenger> reloadedPassenger = passengerRepository.findById(passenger.getId());

            assertThat(reloadedRide).isPresent();
            assertThat(reloadedPassenger).isPresent();

            assertThat(foundSolicitation.get().getRide()).isEqualTo(reloadedRide.get());
            assertThat(foundSolicitation.get().getPassenger()).isEqualTo(reloadedPassenger.get());
            assertThat(foundSolicitation.get().getStatus()).isEqualTo(RideSolicitationStatus.WAITING);
        }

        @Test
        @DisplayName("Should create multiple solicitations for same ride")
        void shouldCreateMultipleSolicitationsForSameRide() {
            RideSolicitation solicitation1 = new RideSolicitation(ride, passenger);
            RideSolicitation solicitation2 = new RideSolicitation(ride, anotherPassenger);

            solicitationRepository.save(solicitation1);
            solicitationRepository.save(solicitation2);
            entityManager.flush();

            List<RideSolicitation> rideSolicitations = solicitationRepository.findRideSolicitationByRide_Id(ride.getId());
            assertThat(rideSolicitations).hasSize(2);
            assertThat(rideSolicitations).extracting(RideSolicitation::getPassenger)
                    .containsExactlyInAnyOrder(passenger, anotherPassenger);
        }

        @Test
        @DisplayName("Should create multiple solicitations for same passenger")
        void shouldCreateMultipleSolicitationsForSamePassenger() {
            RideSolicitation solicitation1 = new RideSolicitation(ride, passenger);
            RideSolicitation solicitation2 = new RideSolicitation(anotherRide, passenger);

            solicitationRepository.save(solicitation1);
            solicitationRepository.save(solicitation2);
            entityManager.flush();

            List<RideSolicitation> passengerSolicitations = solicitationRepository.findRideSolicitationsByPassenger_Id(passenger.getId());
            assertThat(passengerSolicitations).hasSize(2);
            assertThat(passengerSolicitations).extracting(RideSolicitation::getRide)
                    .containsExactlyInAnyOrder(ride, anotherRide);
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
    
    private Ride createRide(Address startAddress, Address endAddress, Driver driver, Car car) {
        LocalDateTime departureTime = LocalDateTime.now().plusHours(1);
        Ride ride = new Ride(startAddress, endAddress, departureTime, driver, car);
        return rideRepository.save(ride);
    }
    
    private void assertSolicitationProperties(RideSolicitation solicitation, Ride expectedRide,
                                              Passenger expectedPassenger, RideSolicitationStatus expectedStatus) {
        assertThat(solicitation.getRide()).isEqualTo(expectedRide);
        assertThat(solicitation.getPassenger()).isEqualTo(expectedPassenger);
        assertThat(solicitation.getStatus()).isEqualTo(expectedStatus);
    }
}
