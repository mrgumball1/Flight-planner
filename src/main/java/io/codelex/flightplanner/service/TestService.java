package io.codelex.flightplanner.service;

import io.codelex.flightplanner.repository.InMemoryRepo;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private InMemoryRepo inMemoryRepo;

    public TestService(InMemoryRepo inMemoryRepo) {
        this.inMemoryRepo = inMemoryRepo;
    }
    public void clearData() {
        inMemoryRepo.getFlightList().clear();
    }
}
