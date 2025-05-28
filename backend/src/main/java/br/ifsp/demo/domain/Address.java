package br.ifsp.demo.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String neighborhood;
    private String number;
    private String city;

    private Address(String street,
                    String neighborhood,
                    String number,
                    String city) {
        validate(street, neighborhood, number, city);
        this.street       = street;
        this.neighborhood = neighborhood;
        this.number       = number;
        this.city         = city;
    }

    public static AddressBuilder builder() {
        return new AddressBuilder();
    }

    public static Address parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        String[] parts = input.split("\\s*,\\s*");
        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    "Invalid format. Expected '<Street>, <Number>, <Neighborhood>, <City>'"
            );
        }
        return Address.builder()
                .street(parts[0])
                .number(parts[1])
                .neighborhood(parts[2])
                .city(parts[3])
                .build();
    }

    public static class AddressBuilder {
        private String street;
        private String neighborhood;
        private String number;
        private String city;

        public AddressBuilder street(String street) {
            this.street = street;
            return this;
        }
        public AddressBuilder neighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }
        public AddressBuilder number(String number) {
            this.number = number;
            return this;
        }
        public AddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        public Address build() {
            return new Address(street, neighborhood, number, city);
        }
    }

    private void validate(String street,
                          String neighborhood,
                          String number,
                          String city) {
        if (isNullOrBlank(street)) {
            throw new IllegalArgumentException("Street cannot be null or blank");
        }
        if (isNullOrBlank(neighborhood)) {
            throw new IllegalArgumentException("Neighborhood cannot be null or blank");
        }
        if (isNullOrBlank(city)) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (!isValidNumber(number)) {
            throw new IllegalArgumentException(
                    "Invalid number format. Must be digits optionally followed by a letter, e.g., '110B'."
            );
        }
    }

    private boolean isNullOrBlank(String val) {
        return val == null || val.isBlank();
    }

    private boolean isValidNumber(String num) {
        return num != null && num.matches("\\d+[A-Za-z]?$");
    }
}
