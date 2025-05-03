package br.ifsp.demo.repositories;

import br.ifsp.demo.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
}
