package br.ifsp.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CpfTest {

    @Test
    @DisplayName("Should create CPF when valid")
    void shouldCreateValidCpf() {
        Cpf cpf = Cpf.of("529.982.247-25");
        assertNotNull(cpf);
        assertEquals("529.982.247-25", cpf.toString());
    }

    @Test
    @DisplayName("Should throw when CPF is null")
    void shouldThrowWhenCpfNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> Cpf.of(null));
        assertEquals("Cpf não pode ser nulo.", ex.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Should throw when CPF format is invalid")
    @ValueSource(strings = {
            "52998224725",
            "529982247256",
            "529.982.24725",
            "529.982.247-2",
            "529.982.247-255",
            "abc.def.ghi-jk",
            "123.456.789/00"
    })
    void shouldThrowForInvalidFormat(String input) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> Cpf.of(input));
        assertEquals("CPF is invalid. Expected format: XXX.XXX.XXX-XX", ex.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Should throw when CPF check digits are invalid")
    @MethodSource("invalidCheckDigitCpfs")
    void shouldThrowForInvalidCheckDigits(String input) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> Cpf.of(input));
        assertEquals("CPF is invalid. Check digits do not match.", ex.getMessage());
    }

    private static Stream<String> invalidCheckDigitCpfs() {
        return Stream.of(
                "529.982.247-35",
                "529.982.247-24",
                "111.444.777-34",
                "123.456.789-08",
                "000.000.000-00"
        );
    }

    @ParameterizedTest
    @DisplayName("Should consider equality and hash code")
    @MethodSource("equalCpfs")
    void shouldBeEqualAndHashCode(Cpf cpf1, Cpf cpf2) {
        assertEquals(cpf1, cpf2);
        assertEquals(cpf1.hashCode(), cpf2.hashCode());
    }

    private static Stream<Arguments> equalCpfs() {
        Cpf cpf = Cpf.of("529.982.247-25");
        return Stream.of(
                Arguments.of(Cpf.of("529.982.247-25"), Cpf.of("529.982.247-25")),
                Arguments.of(cpf, cpf)
        );
    }

    @Test
    @DisplayName("Should consider inequality")
    void shouldNotBeEqual() {
        assertThat(Cpf.of("529.982.247-25").equals(new Object())).isFalse();
    }
}
