package br.ifsp.demo.integration.persistence;

import br.ifsp.demo.domain.*;
import br.ifsp.demo.repositories.*;
import br.ifsp.demo.utils.RideSolicitationStatus;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PassengerRepository passengerRepository;
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
