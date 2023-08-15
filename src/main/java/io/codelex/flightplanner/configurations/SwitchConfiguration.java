package io.codelex.flightplanner.configurations;

import io.codelex.flightplanner.repository.DatabaseAirportRepo;
import io.codelex.flightplanner.repository.DatabaseFlightRepo;
import io.codelex.flightplanner.repository.InMemoryRepo;
import io.codelex.flightplanner.service.DatabaseService;
import io.codelex.flightplanner.service.InMemoryService;
import io.codelex.flightplanner.service.FlightPlannerServiceInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwitchConfiguration {

    @Bean
    @ConditionalOnProperty(prefix="flight-planner", name="store-type", havingValue="in-memory")
    public FlightPlannerServiceInterface getInMemoryService(InMemoryRepo inMemoryRepo) {
        return new InMemoryService(inMemoryRepo);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
    public FlightPlannerServiceInterface getDatabaseService(DatabaseAirportRepo databaseAirportRepo, DatabaseFlightRepo databaseFlightRepo) {
        return new DatabaseService(databaseAirportRepo, databaseFlightRepo);
    }


}
