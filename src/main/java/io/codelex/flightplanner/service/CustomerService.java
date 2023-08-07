package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.models.PageResult;
import io.codelex.flightplanner.models.SearchFlightRequest;
import io.codelex.flightplanner.repository.InMemoryRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private InMemoryRepo inMemoryRepo;

    public CustomerService(InMemoryRepo inMemoryRepo) {
        this.inMemoryRepo = inMemoryRepo;
    }
    public Flight fetchFlight(Integer id) {
        return inMemoryRepo.getFlightList().stream().filter(f -> f.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public List<Airport> searchAirports(String phrase) {
        return inMemoryRepo.getFlightList().stream()
                .map(Flight::getFrom)
                .filter(airport -> airport.containsPhrase(phrase)).toList();
    }
    public PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        ArrayList<Flight> flightList = searchedFlightsFound(searchFlightRequest);
        return new PageResult<>(0, flightList.size(), flightList);
    }

    private ArrayList<Flight> searchedFlightsFound(SearchFlightRequest searchFlightRequest) {
        ArrayList<Flight> foundFlights = new ArrayList<>();

        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (Flight flight : inMemoryRepo.getFlightList()) {
            if (flight.getTo().getAirport().equals(searchFlightRequest.getTo()) && flight.getFrom().getAirport().equals(searchFlightRequest.getFrom())) {
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }

}
