package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.models.PageResult;
import io.codelex.flightplanner.models.SearchFlightRequest;
import io.codelex.flightplanner.service.FlightPlannerServiceInterface;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private FlightPlannerServiceInterface flightPlannerServiceInterface;

    public CustomerController(FlightPlannerServiceInterface flightPlannerServiceInterface) {
        this.flightPlannerServiceInterface = flightPlannerServiceInterface;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam("search") String search) {
        return flightPlannerServiceInterface.searchAirports(search);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Flight> findFlightById(@PathVariable("id") Integer id) {
        return flightPlannerServiceInterface.fetchFlight(id);
    }

    @PostMapping("/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightRequest searchFlightRequest) {
        return flightPlannerServiceInterface.searchFlight(searchFlightRequest);
    }
}