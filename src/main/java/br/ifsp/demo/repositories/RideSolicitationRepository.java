package br.ifsp.demo.repositories;

import br.ifsp.demo.domain.RideSolicitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RideSolicitationRepository extends JpaRepository<RideSolicitation, UUID> {
}
