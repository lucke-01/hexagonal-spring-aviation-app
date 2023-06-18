package com.ricardocreates.domain.entities.flightinfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightIataInfo {
    private Integer numberFlightDeparting;
    private Integer numberFlightArriving;
    private Integer totalPiecesBaggageDeparting;
    private Integer totalPiecesBaggageArriving;
}
