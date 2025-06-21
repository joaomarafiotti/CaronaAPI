package br.ifsp.demo.integration.api.utils;

import br.ifsp.demo.domain.Cpf;
import br.ifsp.demo.security.user.Role;
import br.ifsp.demo.security.user.User;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class DriverEntityBuilder {

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
                .role(Role.DRIVER)
                .cpf(Cpf.of(getRandomCPF()))
                .birthDate(LocalDate.ofInstant(faker.date().birthday(20, 80).toInstant(), ZoneId.systemDefault()))
                .build();
    }

    private static String getRandomCPF(){
        Random rand = new Random();
        return cpfs.get(rand.nextInt(cpfs.size()));
    }

}
