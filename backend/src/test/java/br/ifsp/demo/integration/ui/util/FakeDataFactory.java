package br.ifsp.demo.integration.ui.util;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakeDataFactory {
    private static final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

    public static String randomName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomEmail() {
        return faker.internet().emailAddress("testuser");
    }

    public static String randomBirthDate() {
        return "1990-01-01"; // pode ser adaptado depois
    }

    public static String strongPassword() {
        return "SenhaForte123!";
    }

    public static String generateValidCpfFromList() {
        return "390.533.447-05"; // por enquanto.........
    }
}
