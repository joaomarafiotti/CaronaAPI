package br.ifsp.demo.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Cpf implements Serializable {

    private final String cpf;

    protected Cpf() {
        this.cpf = null;
    }

    public Cpf(String cpf) {
        if (cpf == null || !cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            throw new IllegalArgumentException("Cpf is invalid. Expected format: XXX.XXX.XXX-XX");
        }
        this.cpf = cpf;
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
