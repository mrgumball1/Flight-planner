package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.objects.Airport;
import io.codelex.flightplanner.objects.Flight;
import io.codelex.flightplanner.objects.PageResults;
import io.codelex.flightplanner.requests.AddSearchRequest;
import io.codelex.flightplanner.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightPlannerControllerCustomer {
    private final CustomerService customerService;

    public FlightPlannerControllerCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam("search") String search) {
        return customerService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResults searchFlights(@Valid @RequestBody AddSearchRequest addSearchRequest) {
        return customerService.getSearchedFlights(addSearchRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(@PathVariable("id") Integer id) {
        return customerService.findFlightById(id);
    }
}
