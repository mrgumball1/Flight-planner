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
import java.util.Optional;

public class DatabaseService implements FlightPlannerServiceInterface {
    private DatabaseAirportRepo databaseAirportRepo;
    private DatabaseFlightRepo databaseFlightRepo;

    public DatabaseService(DatabaseAirportRepo databaseAirportRepo, DatabaseFlightRepo databaseFlightRepo) {
        this.databaseAirportRepo = databaseAirportRepo;
        this.databaseFlightRepo = databaseFlightRepo;
    }

    @Override
    public synchronized Flight addFlight(AddFlightRequest addFlightRequest) {

        Airport airport1 = addFlightRequest.getFrom();
        Airport airport2 = addFlightRequest.getTo();

        Flight flight = new Flight(addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        if (airport1.equals(airport2)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        } else if (databaseFlightRepo.existsFlightByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(flight.getFrom(),
                flight.getTo(),
                flight.getCarrier(),
                flight.getDepartureTime(),
                flight.getArrivalTime())) {

            throw new ResponseStatusException(HttpStatus.CONFLICT);

        } else if (flight.isBadDates()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }

        databaseAirportRepo.save(airport1);
        databaseAirportRepo.save(airport2);

        databaseFlightRepo.save(flight);
        return flight;
    }

    @Override
    public Optional<Flight> fetchFlight(Integer id) {
        return Optional.ofNullable(databaseFlightRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public synchronized void deleteFlight(Integer id) {
        databaseFlightRepo.deleteById(id);
    }

    @Override
    public void clearData() {
        databaseAirportRepo.deleteAll();
        databaseFlightRepo.deleteAll();
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return databaseAirportRepo.searchAirports(phrase.toLowerCase().trim());
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
