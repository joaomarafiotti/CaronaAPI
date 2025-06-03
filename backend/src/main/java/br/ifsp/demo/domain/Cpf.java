package br.ifsp.demo.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Cpf implements Serializable {

    private final String cpf;

    protected Cpf() {
        this.cpf = null;
    }

    private Cpf(String cpf) {
        String digits = onlyDigits(cpf);

        if (!cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            throw new IllegalArgumentException("CPF is invalid. Expected format: XXX.XXX.XXX-XX");
        }
        if (!isValidCheckDigits(digits)) {
            throw new IllegalArgumentException("CPF is invalid. Check digits do not match.");
        }

        this.cpf = cpf;
    }

    public static Cpf of(String cpf) {
        return new Cpf(cpf);
    }

    private static String onlyDigits(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("Cpf não pode ser nulo.");
        }
        return cpf.replaceAll("\\D+", "");
    }

    private static boolean isValidCheckDigits(String digits) {
        if (isAllSameCharacter(digits)) {
            return false;
        }
        String base = digits.substring(0, 9);
        int firstCheck  = calculateCheckDigit(base, 10);
        int secondCheck = calculateCheckDigit(base + firstCheck, 11);
        // 502.979.848-02
        return digits.charAt(9) - '0' == firstCheck
               && digits.charAt(10) - '0' == secondCheck;
    }

    private static boolean isAllSameCharacter(String digits) {
        char first = digits.charAt(0);
        for (int i = 1; i < digits.length(); i++) {
            if (digits.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates a CPF check digit using mod-11:
     * - multiply each numeric character by a descending weight
     * - sum the products, take mod 11, and apply: if <2 → 0, else 11 − remainder.
     *
     * @param numbers      the digit string to process
     * @param initialWeight the starting weight (then descends to 2)
     * @return the calculated check digit (0–9)
     */
    private static int calculateCheckDigit(String numbers, int initialWeight) {
        int sum = 0;
        for (int i = 0; i < numbers.length(); i++) {
            sum += (numbers.charAt(i) - '0') * (initialWeight - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    @Override
    public String toString() {
        return cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpf)) return false;
        Cpf other = (Cpf) o;
        return cpf.equals(other.cpf);
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }
}
