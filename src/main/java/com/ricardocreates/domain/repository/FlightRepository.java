package com.ricardocreates.domain.repository;

import com.ricardocreates.domain.entities.flight.Cargo;
import com.ricardocreates.domain.entities.flight.Flight;

import java.time.Instant;
import java.util.List;

public interface FlightRepository {
    public List<Flight> findAllFlight();

    public List<Cargo> findAllCargo();

    Cargo findCargoByFlightId(int id);

    Flight findFlightByFlightNumberAndDate(int flightNumber, Instant date);

    List<Flight> findFlightDeparturesByFlightIataAndDate(String iata, Instant date);

    List<Flight> findFlightArrivingByFlightIataAndDate(String iata, Instant date);
}
