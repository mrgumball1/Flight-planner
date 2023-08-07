package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseFlightRepo extends JpaRepository<Flight, Integer> {
}
