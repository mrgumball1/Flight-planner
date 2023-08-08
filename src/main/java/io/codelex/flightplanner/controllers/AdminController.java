package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.models.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlights(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return adminService.addFlight(addFlightRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable("id") Integer id) {
        return adminService.fetchFlight(id);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") Integer id) {
        adminService.deleteFlight(id);
    }
}
