package br.ifsp.demo.repositories;


import br.ifsp.demo.domain.Passenger;
import br.ifsp.demo.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
    List<Ride> findRideByDriver_Id(UUID driverId);
    List<Ride> findRideByPassengers_Id(UUID passengerId);

    boolean existsByDriverIdAndCarId(UUID driverId, UUID carId);
}
