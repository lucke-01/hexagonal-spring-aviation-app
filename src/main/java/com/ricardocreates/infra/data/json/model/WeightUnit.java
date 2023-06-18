package com.ricardocreates.infra.data.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum WeightUnit {
    @JsonProperty("kg")
    KG,
    @JsonProperty("lb")
    LB
}
