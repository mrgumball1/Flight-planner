package io.codelex.flightplanner.service;

import io.codelex.flightplanner.repository.FlightPlannerRepository;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final FlightPlannerRepository flightPlannerRepository;

    public TestService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }
    public void clearFlights() {
        flightPlannerRepository.clearFlights();
    }
}
