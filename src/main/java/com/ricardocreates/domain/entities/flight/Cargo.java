package com.ricardocreates.domain.entities.flight;

import lombok.Data;

import java.util.List;

@Data
public class Cargo {
    private int flightId;
    private List<Baggage> baggageList;
    private List<Shipment> shipmentList;
}
