package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.models.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.service.FlightPlannerServiceInterface;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private FlightPlannerServiceInterface flightPlannerServiceInterface;

    public AdminController(FlightPlannerServiceInterface flightPlannerServiceInterface) {
        this.flightPlannerServiceInterface = flightPlannerServiceInterface;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlights(@Valid @RequestBody AddFlightRequest addFlightRequest) {
         return flightPlannerServiceInterface.addFlight(addFlightRequest);
    }

    @GetMapping("/flights/{id}")
    public Optional<Flight> fetchFlight(@PathVariable("id") Integer id) {
        return flightPlannerServiceInterface.fetchFlight(id);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") Integer id) {
        flightPlannerServiceInterface.deleteFlight(id);
    }
}
