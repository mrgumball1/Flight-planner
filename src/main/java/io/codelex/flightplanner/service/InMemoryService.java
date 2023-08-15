package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.*;
import io.codelex.flightplanner.repository.InMemoryRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryService implements FlightPlannerServiceInterface {
    private InMemoryRepo inMemoryRepo;

    public InMemoryService(InMemoryRepo inMemoryRepo) {
        this.inMemoryRepo = inMemoryRepo;
    }

    public synchronized Flight addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(inMemoryRepo.getFlightList().size() + 1,
                addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        if (flight.getFrom().equals(flight.getTo())) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } else if (inMemoryRepo.getFlightList().stream().anyMatch(flight1 -> flight1.isEqual(flight))) {

            throw new ResponseStatusException(HttpStatus.CONFLICT);

        } else if (flight.isBadDates()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } else {
            inMemoryRepo.getFlightList().add(flight);
            return flight;
        }
    }


    public Optional<Flight> fetchFlight(Integer id) {
        return Optional.ofNullable(inMemoryRepo.getFlightList()
                .stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public synchronized void deleteFlight(Integer id) {
        inMemoryRepo.getFlightList().removeIf(f -> f.getId().equals(id));
    }

    public void clearData() {
        inMemoryRepo.getFlightList().clear();
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

    public ArrayList<Flight> searchedFlightsFound(SearchFlightRequest searchFlightRequest) {
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
