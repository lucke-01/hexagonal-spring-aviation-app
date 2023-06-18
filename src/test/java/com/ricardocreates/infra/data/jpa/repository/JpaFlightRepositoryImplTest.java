package com.ricardocreates.infra.data.jpa.repository;

import com.ricardocreates.infra.data.jpa.model.FlightEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//we activate jpa profile to test jpa repository
@ActiveProfiles({"test", "jpa"})
public class JpaFlightRepositoryImplTest {
    @Autowired
    private DataJpaFlightRepository dataJpaFlightRepository;
    @Autowired
    private JpaFlightRepositoryImpl jpaFlightRepositoryImpl;

    //just testing jpa works
    @Test
    void findAll() {
        FlightEntity flight = new FlightEntity();
        flight.setFlightId(1);
        dataJpaFlightRepository.save(flight);

        jpaFlightRepositoryImpl.findAllFlight();
    }
    //TODO: test with jpa but since this is an extra and we have JSON repository it will be not necessary so far
}
