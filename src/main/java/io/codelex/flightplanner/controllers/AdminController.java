package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.models.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.service.ServiceInterface;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private ServiceInterface serviceInterface;

    public AdminController(ServiceInterface serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlights(@Valid @RequestBody AddFlightRequest addFlightRequest) {
         return serviceInterface.addFlight(addFlightRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable("id") Integer id) {
        return serviceInterface.fetchFlight(id);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") Integer id) {
        serviceInterface.deleteFlight(id);
    }
}
