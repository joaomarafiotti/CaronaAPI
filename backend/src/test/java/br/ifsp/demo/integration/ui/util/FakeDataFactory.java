package br.ifsp.demo.integration.ui.util;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeDataFactory {

    private static final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

    private static final List<String> cpfList = List.of(
        "529.982.247-25",
        "390.533.447-05",
        "215.365.987-43",
        "746.821.360-20",
        "123.456.789-09",
        "654.321.987-00",
        "321.654.987-66",
        "222.333.444-55",
        "111.222.333-96",
        "147.258.369-00"
    );

    private static final AtomicInteger cpfIndex = new AtomicInteger(0);

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
        return "1990-01-01"; // tlvz gerar dinamicamente depois?
    }

    public static String strongPassword() {
        return "SenhaForte123!";
    }

    public static String generateValidCpfFromList() {
        int index = cpfIndex.getAndIncrement() % cpfList.size();
        return cpfList.get(index);
    }
}
