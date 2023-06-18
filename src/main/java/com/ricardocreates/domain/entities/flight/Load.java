package com.ricardocreates.domain.entities.flight;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Load {
    protected int id;
    protected int weight;
    protected WeightUnit weightUnit;
    protected int pieces;
}
