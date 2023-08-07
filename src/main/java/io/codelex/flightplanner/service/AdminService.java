package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.repository.InMemoryRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class AdminService {
    private InMemoryRepo inMemoryRepo;

    public AdminService(InMemoryRepo inMemoryRepo) {
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


    public Flight fetchFlight(Integer id) {
        return inMemoryRepo.getFlightList().stream().filter(f -> f.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public synchronized void deleteFlight(Integer id) {
        inMemoryRepo.getFlightList().removeIf(f-> f.getId().equals(id));
    }
}
