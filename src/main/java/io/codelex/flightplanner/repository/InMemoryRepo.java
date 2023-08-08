package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Flight;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryRepo {
    private CopyOnWriteArrayList<Flight> flightList;

    public InMemoryRepo() {
        this.flightList = new CopyOnWriteArrayList<>();
    }

    public List<Flight> getFlightList() {
        return flightList;
    }
}

