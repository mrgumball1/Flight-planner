package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.service.ServiceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestController {

    private ServiceInterface serviceInterface;

    public TestController(ServiceInterface serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        serviceInterface.clearData();
    }
}
