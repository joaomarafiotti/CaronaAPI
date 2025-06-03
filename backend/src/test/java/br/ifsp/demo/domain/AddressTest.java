package br.ifsp.demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressTest {


    @ParameterizedTest
    @ValueSource(strings = {
            "Rua das Flores, 123, Centro, São Paulo",
            "Av. Paulista, 1000A, Bela Vista, São Paulo",
            "Rua XV de Novembro, 250, Centro, Curitiba",
            "Alameda Santos, 501B, Jardim Paulista, São Paulo",
            "Rua Sete de Setembro, 77C, Centro, Porto Alegre",
            "Av. Getúlio Vargas, 88, Funcionários, Belo Horizonte",
            "Rua da Praia, 321, Praia Grande, Santos",
            "Rua das Acácias, 45B, Jardim América, Goiânia",
            "Av. Brasil, 2020, Copacabana, Rio de Janeiro",
            "Rua Dom Pedro II, 333A, Centro, Recife"
    })
    @DisplayName("Should parse valid addresses")
    void testValidAddressParsing(String input) {
        Address address = Address.parse(input);
        assertNotNull(address);
    }

    @ParameterizedTest
    @MethodSource("invalidStringsToParse")
    @DisplayName("Should throw if tries to parse an invalid address")
    void shouldThrowIfTriesToParseAnInvalidAddress(String input) {
        assertThrows(IllegalArgumentException.class, () -> Address.parse(input));
    }

    static Stream<String> invalidStringsToParse() {
        return Stream.of(
                null,
                "",
                ",,,",
                "Rua das Flores,,Centro, São Paulo",
                "Rua das Flores, 123, , São Paulo",
                "Rua das Flores, 123, Centro,,",
                "Rua das Flores, 123, Centro",
                "Rua das Flores 123, Centro, , São Paulo",
                "Rua das Flores,123,Centro",
                "Rua das Flores ; 123, Centro, São Paulo",
                "Rua das Flores, 123; Centro, São Paulo",
                " ,123,Centro,São Paulo",
                "Rua, das, Flores, 123, Centro, São Paulo",
                "R!ua das Flores, 123, Centro, São Paulo",
                "Rua das Flores, 1@23, Centro, São Paulo",
                "Rua das Flores, 123, C#ntro, São Paulo",
                "Rua das Flores, 123, Centro, S%o Paulo",
                "Rua das *Açaís*, 45, Jardim, Goiânia",
                "Av. Brasil, 2020, Copacabana&, Rio de Janeiro",
                "Rua das Flores, 123, Centro?, Recife",
                "Rua (Nova), 77, Centro, Porto Alegre",
                "Rua das Flores, 123, Centro|Bela, Vista",
                "Rua das Flores, 123, Centro<Centro>, São Paulo"
        );
    }


    @ParameterizedTest
    @MethodSource("invalidAddressToBuild")
    @DisplayName("Should throw if tries to build an invalid address")
    void shouldThrowIfTriesToBuildAnInvalidAddress(String street, String number, String neighborhood, String city) {
        assertThrows(IllegalArgumentException.class, () -> new Address.AddressBuilder()
                .street(street)
                .number(number)
                .neighborhood(neighborhood)
                .city(city)
                .build());
    }

    static Stream<Arguments> invalidAddressToBuild() {
        return Stream.of(
                Arguments.of(null, "123", "Centro", "São Paulo"),
                Arguments.of("   ", "123", "Centro", "São Paulo"),
                Arguments.of("Rua X", null, "Centro", "São Paulo"),
                Arguments.of("Rua X", "", "Centro", "São Paulo"),
                Arguments.of("Rua X", "12@3", "Centro", "São Paulo"),
                Arguments.of("Rua X", "123AB", "Centro", "São Paulo"),
                Arguments.of("Rua X", "123", null, "São Paulo"),
                Arguments.of("Rua X", "123", "   ", "São Paulo"),
                Arguments.of("Rua X", "123", "Centro", null),
                Arguments.of("Rua X", "123", "Centro", "   ")
        );
    }

}
