package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.objects.Flight;
import io.codelex.flightplanner.requests.AddFlightRequest;
import io.codelex.flightplanner.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin-api")
public class FlightPlannerControllerAdmin {

    private final AdminService adminService;

    public FlightPlannerControllerAdmin(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized Flight addFlights(@Valid @RequestBody AddFlightRequest flightRequest) {
        Flight flight = flightRequest.toDomain(adminService.getNewId());
        adminService.addFlight(flight);
        return flight;
    }

    @DeleteMapping("/flights/{id}")
    public synchronized void deleteFlight(@PathVariable("id") Integer id) {
        adminService.deleteFlight(id);
    }


    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable("id") Integer id) {
        return adminService.fetchFlight(id);

    }

}
