package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.objects.Airport;
import io.codelex.flightplanner.objects.Flight;
import io.codelex.flightplanner.objects.PageResults;
import io.codelex.flightplanner.requests.AddSearchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class FlightPlannerRepository {

    private CopyOnWriteArrayList<Flight> fligthsList;


    public FlightPlannerRepository() {
        this.fligthsList = new CopyOnWriteArrayList<>();
    }

    public void addFlight(Flight flight) {
        fligthsList.add(flight);
    }

    public List getFligthsList() {
        return fligthsList;
    }

    public Flight fetchFlight(Integer id) {
        return fligthsList.stream().filter(f -> f.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void clearFlights() {
        fligthsList.clear();
    }

    public boolean isSameFlight(Flight flight) {
        return fligthsList.stream().anyMatch(flight::equals);
    }

    public void deleteFlight(Integer id) {
        fligthsList.removeIf(flight -> flight.getId().equals(id));
    }

    public List<Airport> searchAirports(String phrase) {
        return fligthsList.stream().map(Flight::getFrom)
                .filter(airport -> airport.containsPhrase(phrase)).toList();
    }

    public PageResults<Flight> getSearchedFlights(AddSearchRequest searchRequest) {
        List<Flight> list = searchedFlightsFound(searchRequest);
        return new PageResults<>(0, list.size(), list);
    }

    private List<Flight> searchedFlightsFound(AddSearchRequest searchRequest) {
        List<Flight> foundFlights = new ArrayList<>();

        if (searchRequest.getFrom().equals(searchRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (Flight flight : fligthsList) {
            if (flight.getTo().getAirport().equals(searchRequest.getTo()) && flight.getFrom().getAirport().equals(searchRequest.getFrom())) {
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }
}
