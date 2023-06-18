package com.ricardocreates.domain.operation;

import com.ricardocreates.domain.entities.flightinfo.FlightIataInfo;
import com.ricardocreates.domain.entities.flightinfo.FlightInfo;

import java.time.Instant;

public interface FlightOperation {
    FlightInfo getFlightInfo(Integer flightNumber, Instant flightDate);

    FlightIataInfo getFlightInfoIata(String iataCode, Instant flightDate);

}
