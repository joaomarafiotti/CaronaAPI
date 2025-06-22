package br.ifsp.demo.integration.api.tests;

import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.integration.api.utils.DriverEntityBuilder;
import br.ifsp.demo.integration.api.utils.PassengerEntityBuilder;
import br.ifsp.demo.security.user.User;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class AuthTests extends BaseApiIntegrationTest {

    @Test
    @Tag("ApiTest")
    @DisplayName("Should register passenger and return 201 with id as payload")
    void shouldRegisterUserAndReturn201WithIdAsPayload(){
        final User user = PassengerEntityBuilder.createRandomPassengerUser("password");
        given().contentType("application/json").port(port).body(user)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(201).body("id", notNullValue());
    }

    @Test
    @Tag("ApiTest")
    @DisplayName("Should register driver and return 201 with id as payload")
    void shouldRegisterDriverAndReturn201WithIdAsPayload(){
        final User user = DriverEntityBuilder.createRandomDriverUser("password");
        given().contentType("application/json").port(port).body(user)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(201).body("id", notNullValue());
    }

    @Test
    @DisplayName("Should return code 409 if email is already used")
    void shouldReturnCode409IfEmailIsAlreadyUsed(){
        final User user1 = DriverEntityBuilder.createDriverByEmail("password", "email@email.com");
        final User user2 = DriverEntityBuilder.createDriverByEmail("password", "email@email.com");
        given().contentType("application/json").port(port).body(user1)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(201).body("id", notNullValue());
        given().contentType("application/json").port(port).body(user2)
                .when().post("/api/v1/register")
                .then().log().ifValidationFails(LogDetail.BODY).statusCode(409);
    }
}
