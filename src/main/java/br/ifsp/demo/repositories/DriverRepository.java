package br.ifsp.demo.repositories;

import br.ifsp.demo.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
}
