package com.ricardocreates.infra.data.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(schema = "SMART", name = "FLIGHT")
public class FlightEntity {
    @Id
    @Column(name = "flight_id")
    @EqualsAndHashCode.Include()
    private int flightId;
    @Column(name = "flight_number")
    private int flightNumber;
    @Column(name = "departure_airport_IATA_Code")
    private String departureAirportIATACode;
    @Column(name = "arrival_airport_IATA_code")
    private String arrivalAirportIATACode;
    @Column(name = "departure_date")
    private Instant departureDate;
    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CargoEntity cargo;
}
