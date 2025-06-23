package br.ifsp.demo.integration.api.tests;


import br.ifsp.demo.repositories.*;
import br.ifsp.demo.security.user.JpaUserRepository;
import br.ifsp.demo.utils.RideSolicitationStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseApiIntegrationTest {

    @LocalServerPort protected int port;
    @Autowired private JpaUserRepository userRepository;
    @Autowired private CarRepository carRepository;
    @Autowired private DriverRepository driverRepository;
    @Autowired private PassengerRepository passengerRepository;
    @Autowired private RideRepository rideRepository;
    @Autowired private RideSolicitationRepository rideSolicitationRepository;

    @BeforeEach
    public void generalSetup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @AfterEach void tearDown() {
        rideSolicitationRepository.deleteAllInBatch();
        rideRepository.deleteAllInBatch();
        passengerRepository.deleteAllInBatch();
        driverRepository.deleteAllInBatch();
        carRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

}
