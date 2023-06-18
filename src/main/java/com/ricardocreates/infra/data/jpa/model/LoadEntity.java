package com.ricardocreates.infra.data.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class LoadEntity {
    @Id
    @Column(name = "id")
    protected int id;

    @ManyToOne()
    @JoinColumn(name = "id", insertable = false, updatable = false)
    protected CargoEntity cargo;

    @Column(name = "weight")
    protected int weight;
    @Column(name = "weight_unit")
    protected WeightUnit weightUnit;
    @Column(name = "pieces")
    protected int pieces;
}
