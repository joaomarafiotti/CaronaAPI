package br.ifsp.demo.integration.api.utils;

import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

public class PassengerEntityBuilder {

    private static Faker faker = new Faker(new Locale("pt-BR"));

    public static User createRandomPassengerUser(String password){
        return User.builder().id(UUID.randomUUID())
                .name(faker.name().firstName())
                .lastname(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(password)
                .role(Role.PASSENGER)
                .cpf(Cpf.of("679.538.950-21"))
                .birthDate(LocalDate.parse(faker.date().birthday(20, 80).toString()))
                .build();
    }

}
