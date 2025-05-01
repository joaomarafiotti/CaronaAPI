package br.ifsp.demo.repositories;

import br.ifsp.demo.dto.RideDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<RideDTO, UUID> {
}
