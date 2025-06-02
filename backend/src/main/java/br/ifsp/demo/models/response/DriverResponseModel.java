package br.ifsp.demo.models.response;

import java.time.LocalDate;

public record DriverResponseModel(String name, String cpf, String email, LocalDate birthDate) {

}
