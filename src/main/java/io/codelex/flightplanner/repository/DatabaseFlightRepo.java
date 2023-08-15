package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface DatabaseFlightRepo extends JpaRepository<Flight, Integer> {
    boolean existsFlightByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime);

}
