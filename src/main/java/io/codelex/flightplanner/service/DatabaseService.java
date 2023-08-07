package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.*;
import io.codelex.flightplanner.repository.DatabaseAirportRepo;
import io.codelex.flightplanner.repository.DatabaseFlightRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService implements ServiceInterface {
    private DatabaseAirportRepo databaseAirportRepo;
    private DatabaseFlightRepo databaseFlightRepo;

    public DatabaseService(DatabaseAirportRepo databaseAirportRepo, DatabaseFlightRepo databaseFlightRepo) {
        this.databaseAirportRepo = databaseAirportRepo;
        this.databaseFlightRepo = databaseFlightRepo;
    }

    @Override
    public synchronized Flight addFlight(AddFlightRequest addFlightRequest) {

        Airport airport1 = new Airport(addFlightRequest.getFrom().getCountry(),
                addFlightRequest.getFrom().getCity(),
                addFlightRequest.getFrom().getAirport());

        Airport airport2 = new Airport(addFlightRequest.getTo().getCountry(),
                addFlightRequest.getTo().getCity(),
                addFlightRequest.getTo().getAirport());

        Flight flight = new Flight(databaseFlightRepo.findAll().size() + 1, addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        if (airport1.equals(airport2)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } else if (databaseFlightRepo.findAll()
                .stream()
                .anyMatch(flight1 -> flight1.isEqual(flight))) {

            throw new ResponseStatusException(HttpStatus.CONFLICT);

        } else if (flight.isBadDates()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } else

            databaseAirportRepo.save(airport1);
        databaseAirportRepo.save(airport2);

        databaseFlightRepo.save(flight);
        return flight;
    }

    @Override
    public Flight fetchFlight(Integer id) {
        return databaseFlightRepo.findAll()
                .stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public synchronized void deleteFlight(Integer id) {
        databaseAirportRepo.deleteAll();
        databaseFlightRepo.deleteById(id);
    }

    @Override
    public void clearData() {
        databaseAirportRepo.deleteAll();
        databaseFlightRepo.deleteAll();
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return databaseFlightRepo.findAll().stream()
                .map(Flight::getFrom)
                .filter(airport -> airport.containsPhrase(phrase)).toList();
    }

    @Override
    public PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        ArrayList<Flight> flightList = searchedFlightsFound(searchFlightRequest);
        return new PageResult<>(0, flightList.size(), flightList);
    }

    @Override
    public ArrayList<Flight> searchedFlightsFound(SearchFlightRequest searchFlightRequest) {
        ArrayList<Flight> foundFlights = new ArrayList<>();

        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (Flight flight : databaseFlightRepo.findAll()) {
            if (flight.getTo().getAirport().equals(searchFlightRequest.getTo()) && flight.getFrom().getAirport().equals(searchFlightRequest.getFrom())) {
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }
}
