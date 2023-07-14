package io.codelex.flightplanner;

import io.codelex.flightplanner.controller.FlightPlannerControllerAdmin;
import io.codelex.flightplanner.controller.FlightPlannerControllerTest;
import io.codelex.flightplanner.objects.Airport;
import io.codelex.flightplanner.objects.Flight;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import io.codelex.flightplanner.requests.AddFlightRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.format.DateTimeFormatter;

@SpringBootTest
class FlightPlannerApplicationTests {

	@Autowired
	FlightPlannerControllerAdmin adminFlightController;
	@Autowired
	FlightPlannerControllerTest testController;
	@Autowired
	FlightPlannerRepository repository;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	@Test
	void  addFlightsTest() {
		Airport from = new Airport("Latvia", "Riga", "RIX");
		Airport to = new Airport("Estonia", "Tallinn", "EST");
		String departureTime = "2022-05-02 08:30";
		String arrivalTime = "2022-05-02 10:30";

		AddFlightRequest addFlightRequest = new AddFlightRequest(from, to, "AIRBALTIC", departureTime, arrivalTime);

		Flight flight = adminFlightController.addFlights(addFlightRequest);
		Assertions.assertNotNull(flight.getId());
		Assertions.assertEquals(from, flight.getFrom());
		Assertions.assertEquals(to, flight.getTo());
		Assertions.assertEquals("AIRBALTIC", flight.getCarrier());
		Assertions.assertEquals(departureTime, flight.getDepartureTime().format(formatter));
		Assertions.assertEquals(arrivalTime, flight.getArrivalTime().format(formatter));
	}
	@Test
	void clearFlightsTest() {
		repository.clearFlights();
		Assertions.assertEquals(0,repository.getFligthsList().size());
	}
}
