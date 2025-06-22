package br.ifsp.demo.integration.api.tests;

import br.ifsp.demo.integration.api.utils.*;
import br.ifsp.demo.models.request.CarRequestModel;
import br.ifsp.demo.models.request.RideRequestModel;
import br.ifsp.demo.security.auth.AuthRequest;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class RideSolicitationTests extends BaseApiIntegrationTest{

    private String authenticationTokenDriver;
    private String authenticationTokenPassenger;
    private String carId;
    private String rideId;

    private void setAuthenticationTokenDriver(){
        final RegisterUserRequest user = DriverEntityBuilder.createDriverByEmail("password", "driver@email.com");
        given().contentType("application/json").port(port).body(user)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY);

        final AuthRequest authRequest = new AuthRequest("driver@email.com","password");
        Response loginResponse = given().contentType("application/json").port(port).body(authRequest)
                .when().post("/api/v1/authenticate")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        this.authenticationTokenDriver = loginResponse.jsonPath().getString("token");
    }

    private void setAuthenticationTokenPassenger(){
        final RegisterUserRequest user = PassengerEntityBuilder.createPassengerByEmail("password", "passenger@email.com");
        given().contentType("application/json").port(port).body(user)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY);

        final AuthRequest authRequest = new AuthRequest("passenger@email.com","password");
        Response loginResponse = given().contentType("application/json").port(port).body(authRequest)
                .when().post("/api/v1/authenticate")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        this.authenticationTokenPassenger = loginResponse.jsonPath().getString("token");
    }

    private void setCarId(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        Response response = given().header("Authorization", "Bearer " + authenticationTokenDriver)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        this.carId = response.jsonPath().getString("id");
    }

    private void setRideId(){
        final RideRequestModel ride = RideEntityBuilder.createRandomRide(carId);
        Response response = given().header("Authorization", "Bearer " + authenticationTokenDriver)
                .contentType("application/json").port(port).body(ride)
                .when().post("/api/v1/ride")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        this.rideId = response.jsonPath().getString("rideId");
    }

    @BeforeEach
    void setup(){
        setAuthenticationTokenDriver();
        setAuthenticationTokenPassenger();
        setCarId();
        setRideId();
    }

    @Test
    @Tag("ApiTest")
    @DisplayName("Should return 201 when creating a ride solicitation")
    void shouldReturn201WhenCreatingARideSolicitation(){
        given().header("Authorization", "Bearer " + authenticationTokenPassenger)
                .when().post("/api/v1/ride-solicitations?rideId="+rideId)
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(201).body("rideSolicitationId", notNullValue());
    }

}
