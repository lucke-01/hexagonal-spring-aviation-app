package com.ricardocreates.infra.data.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CargoEntity {
    private int flightId;
    @JsonProperty("baggage")
    private List<BaggageEntity> baggageList;
    @JsonProperty("cargo")
    private List<ShipmentEntity> shipmentList;
}
