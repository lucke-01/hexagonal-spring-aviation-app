package com.ricardocreates.domain.entities.flightinfo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FlightInfo {
    private BigDecimal cargoWeight;

    private BigDecimal baggageWeight;

    private BigDecimal totalWeight;
}
