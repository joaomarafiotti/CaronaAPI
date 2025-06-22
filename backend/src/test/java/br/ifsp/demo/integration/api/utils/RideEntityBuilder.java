package br.ifsp.demo.integration.api.utils;

import br.ifsp.demo.models.request.RideRequestModel;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideEntityBuilder {

    private static Faker faker = new Faker();

    public static RideRequestModel createRandomRide(String carId){
        return new RideRequestModel(
                faker.address().fullAddress(),
                faker.address().fullAddress(),
                LocalDateTime.from(faker.timeAndDate().future()),
                UUID.fromString(carId)
        );
    }
}
