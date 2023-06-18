package com.ricardocreates.infra.data.jpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum WeightUnit {
    @JsonProperty("kg")
    KG,
    @JsonProperty("lb")
    LB
}
