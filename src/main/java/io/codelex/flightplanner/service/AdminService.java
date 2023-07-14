package io.codelex.flightplanner.service;

import io.codelex.flightplanner.objects.Flight;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AdminService {
    private final FlightPlannerRepository flightPlannerRepository;

    public AdminService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }

    public void addFlight(Flight flight) {
        if (flight.getFrom().equals(flight.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (flight.isBadDates()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!flightPlannerRepository.isSameFlight(flight)) {
            flightPlannerRepository.addFlight(flight);
        } else {
            flightPlannerRepository.isSameFlight(flight);
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Integer getNewId() {
        return flightPlannerRepository.getFligthsList().size() + 1;
    }

    public Flight fetchFlight(Integer id) {
        return flightPlannerRepository.fetchFlight(id);

    }

    public void deleteFlight(Integer id) {
        flightPlannerRepository.deleteFlight(id);
    }

}
