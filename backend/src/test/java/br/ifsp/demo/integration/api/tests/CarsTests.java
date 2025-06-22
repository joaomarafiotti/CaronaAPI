package br.ifsp.demo.integration.api.tests;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.integration.api.utils.CarEntityBuilder;
import br.ifsp.demo.integration.api.utils.DriverEntityBuilder;
import br.ifsp.demo.integration.api.utils.PassengerEntityBuilder;
import br.ifsp.demo.models.request.CarRequestModel;
import br.ifsp.demo.security.auth.AuthRequest;
import br.ifsp.demo.security.user.User;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class CarsTests extends BaseApiIntegrationTest{

    private String authenticatedToken;

    @BeforeEach
    void setAuthenticatedToken() {
        final User user = DriverEntityBuilder.createDriverByEmail("password", "email@email.com");
        given().contentType("application/json").port(port).body(user)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(201);

        final AuthRequest authRequest = new AuthRequest("email@email.com","password");
        Response loginResponse = given().contentType("application/json").port(port).body(authRequest)
                .when().post("/api/v1/authenticate")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(200).extract().response();
        this.authenticatedToken = loginResponse.jsonPath().getString("token");
    }
    
    @Test
    @Tag("ApiTest")
    @DisplayName("Should return 201 and id of created car")
    void shouldReturn201AndIdOfCreatedCar(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(201).body("id", notNullValue());
    }
    
    @Test
    @Tag("ApiTest")
    @DisplayName("Should return 409 if creating two equal cars")
    void shouldReturn409IfCreatingTwoEqualCars(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).body("id", notNullValue());
        given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(409);
    }

    @Test
    @Tag("ApiTest")
    @DisplayName("Should return 200 with all cars in body")
    void shouldReturn200WithAllCarsInBody(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).body("id", notNullValue());

        given().header("Authorization", "Bearer " + authenticatedToken)
                .when().get("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(200)
                    .body("$", hasSize(1))
                    .body("[0]", notNullValue());

    }

    @Test
    @Tag("ApiTest")
    @DisplayName("Should return 200 when get one car by id")
    void shouldReturn200WhenGetOneCarById(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        Response response = given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        String id = response.jsonPath().getString("id");
        given().header("Authorization", "Bearer " + authenticatedToken)
                .when().get("/api/v1/drivers/cars/"+id)
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(200).body("id", equalTo(id));
    }

    @Test
    @Tag("ApiTest")
    @DisplayName("Should remove a car and give 204 status code")
    void shouldRemoveACarAndGive204StatusCode(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        Response response = given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        String id = response.jsonPath().getString("id");
        given().header("Authorization", "Bearer " + authenticatedToken)
                .when().delete("/api/v1/drivers/cars/"+id)
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(204);
    }

    @Test
    @Tag("ApiTest")
    @DisplayName("Should not let passenger get car information")
    void shouldNotLetPassengerGetCarInformation(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();

        final User user = PassengerEntityBuilder.createPassengerByEmail("password", "email@email.com");
        given().contentType("application/json").port(port).body(user)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY);

        final AuthRequest authRequest = new AuthRequest("email@email.com","password");
        Response loginResponse = given().contentType("application/json").port(port).body(authRequest)
                .when().post("/api/v1/authenticate")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        String token = loginResponse.jsonPath().getString("token");

        given().header("Authorization", "Bearer " + token)
                .when().get("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(401);
    }

    @Test
    @Tag("ApiTest")
    @DisplayName("Should not let passenger delete car information")
    void shouldNotLetPassengerDeleteCarInformation(){
        final CarRequestModel car = CarEntityBuilder.createRandomCar();
        Response response = given().header("Authorization", "Bearer " + authenticatedToken)
                .contentType("application/json").port(port).body(car)
                .when().post("/api/v1/drivers/cars")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        String id = response.jsonPath().getString("id");

        final User user = PassengerEntityBuilder.createPassengerByEmail("password", "email@email.com");
        given().contentType("application/json").port(port).body(user)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY);

        final AuthRequest authRequest = new AuthRequest("email@email.com","password");
        Response loginResponse = given().contentType("application/json").port(port).body(authRequest)
                .when().post("/api/v1/authenticate")
                .then().log().ifValidationFails(LogDetail.BODY).extract().response();
        String token = loginResponse.jsonPath().getString("token");

        given().header("Authorization", "Bearer " + token)
                .when().delete("/api/v1/drivers/cars/"+id)
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(401);
    }
}
