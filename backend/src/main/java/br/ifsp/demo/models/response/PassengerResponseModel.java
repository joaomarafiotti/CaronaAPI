package br.ifsp.demo.models.response;

import br.ifsp.demo.domain.Cpf;

import java.time.LocalDate;

public record PassengerResponseModel(String name, String cpf, String email, LocalDate birthDate) {
}
