package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.service.FlightPlannerServiceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestController {

    private FlightPlannerServiceInterface flightPlannerServiceInterface;

    public TestController(FlightPlannerServiceInterface flightPlannerServiceInterface) {
        this.flightPlannerServiceInterface = flightPlannerServiceInterface;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightPlannerServiceInterface.clearData();
    }
}
