package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.*;

import java.util.ArrayList;
import java.util.List;

public interface ServiceInterface {

    Flight addFlight(AddFlightRequest addFlightRequest);

    Flight fetchFlight(Integer id);

    void deleteFlight(Integer id);

    void clearData();

    List<Airport> searchAirports(String phrase);

    PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest);

    ArrayList<Flight> searchedFlightsFound(SearchFlightRequest searchFlightRequest);
}
