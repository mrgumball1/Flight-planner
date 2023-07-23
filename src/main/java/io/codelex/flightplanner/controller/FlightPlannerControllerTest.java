package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.service.TestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testing-api")
public class FlightPlannerControllerTest {

    private final TestService testService;

    public FlightPlannerControllerTest(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        testService.clearFlights();
    }
}
