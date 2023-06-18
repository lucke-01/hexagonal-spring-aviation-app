package com.ricardocreates.domain.entities.flight;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
public class Flight {
    private int flightId;
    private int flightNumber;
    private String departureAirportIATACode;
    private String arrivalAirportIATACode;
    private Instant departureDate;

    private Cargo cargo;
}
