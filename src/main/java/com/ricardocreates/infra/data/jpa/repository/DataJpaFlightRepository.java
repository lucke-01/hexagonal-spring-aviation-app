package com.ricardocreates.infra.data.jpa.repository;

import com.ricardocreates.infra.data.jpa.model.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DataJpaFlightRepository extends JpaRepository<FlightEntity, Long> {
    FlightEntity findFlightByFlightNumberAndDepartureDate(int flightNumber, Instant departureDate);

    List<FlightEntity> findByDepartureAirportIATACodeAndDepartureDate(String iataCode, Instant departureDate);

    List<FlightEntity> findByArrivalAirportIATACodeAndDepartureDate(String iataCode, Instant departureDate);
}