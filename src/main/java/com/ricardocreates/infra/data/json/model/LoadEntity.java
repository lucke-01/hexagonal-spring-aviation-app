package com.ricardocreates.infra.data.json.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class LoadEntity {
    protected int id;
    protected int weight;
    protected WeightUnit weightUnit;
    protected int pieces;
}
