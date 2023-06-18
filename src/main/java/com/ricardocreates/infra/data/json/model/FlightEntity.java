package com.ricardocreates.infra.data.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;

@Data
public class FlightEntity {
    private int flightId;
    private int flightNumber;
    private String departureAirportIATACode;
    private String arrivalAirportIATACode;
    private Instant departureDate;
    /**
     * we will calculate this property by cargo.flightId but not with objectMapper
     */
    @JsonIgnore
    private CargoEntity cargo;
}
