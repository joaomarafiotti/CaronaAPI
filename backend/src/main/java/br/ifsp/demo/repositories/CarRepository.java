package br.ifsp.demo.repositories;

import br.ifsp.demo.domain.Car;
import br.ifsp.demo.domain.LicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    Optional<Car> findByLicensePlate(LicensePlate licensePlate);
}
