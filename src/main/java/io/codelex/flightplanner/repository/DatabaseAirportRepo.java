package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatabaseAirportRepo extends JpaRepository<Airport, String> {

    @Query("SELECT a FROM Airport a " +
            "WHERE trim(lower(a.airport)) like (:phrase || '%')" +
            "OR trim(lower(a.city)) like (:phrase || '%')" +
            "OR trim(lower(a.country)) like (:phrase || '%')")
    List<Airport> searchAirports(@Param("phrase") String phrase);}