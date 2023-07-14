package io.codelex.flightplanner.service;

import io.codelex.flightplanner.objects.Airport;
import io.codelex.flightplanner.objects.Flight;
import io.codelex.flightplanner.objects.PageResults;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import io.codelex.flightplanner.requests.AddSearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    private final FlightPlannerRepository flightPlannerRepository;

    public CustomerService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }
    public Flight findFlightById(Integer id) {
        return flightPlannerRepository.fetchFlight(id);

    }
    public List<Airport> searchAirports(String phrase) {
        return flightPlannerRepository.searchAirports(phrase);
    }
    public PageResults<Flight> getSearchedFlights(AddSearchRequest searchRequest) {
        return flightPlannerRepository.getSearchedFlights(searchRequest);
    }

}
