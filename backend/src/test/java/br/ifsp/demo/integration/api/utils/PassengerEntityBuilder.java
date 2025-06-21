package br.ifsp.demo.integration.api.utils;

import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.*;

public class PassengerEntityBuilder {

    private static Faker faker = new Faker(new Locale("pt-BR"));
    private static List<String> cpfs = List.of(
            "679.538.950-21",
            "733.677.500-42",
            "804.530.890-70",
            "241.811.950-58",
            "877.321.140-00",
            "781.669.170-09"
    );

    public static User createRandomPassengerUser(String password){
        return User.builder().id(UUID.randomUUID())
                .name(faker.name().firstName())
                .lastname(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(password)
                .role(Role.PASSENGER)
                .cpf(Cpf.of(getRandomCPF()))
                .birthDate(LocalDate.parse(faker.date().birthday(20, 80).toString()))
                .build();
    }

    private static String getRandomCPF(){
        Random rand = new Random();
        return cpfs.get(rand.nextInt(cpfs.size()));
    }

}
