package br.ifsp.demo.models.response;

import java.time.LocalDate;

public record UserResponseModel(String name, String cpf, String email, LocalDate birthDate) {
}