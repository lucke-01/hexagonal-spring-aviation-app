package com.ricardocreates.infra.data.jpa.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(schema = "SMART", name = "CARGO")
public class CargoEntity {

    @Id
    @Column(name = "flight_id")
    private int flightId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;

    @OneToMany(mappedBy = "cargo")
    private List<BaggageEntity> baggageList;
    @OneToMany(mappedBy = "cargo")
    private List<ShipmentEntity> shipmentList;
}
