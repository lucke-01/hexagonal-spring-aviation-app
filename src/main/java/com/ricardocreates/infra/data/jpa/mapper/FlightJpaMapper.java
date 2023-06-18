package com.ricardocreates.infra.data.jpa.mapper;

import com.ricardocreates.domain.entities.flight.Baggage;
import com.ricardocreates.domain.entities.flight.Cargo;
import com.ricardocreates.domain.entities.flight.Flight;
import com.ricardocreates.domain.entities.flight.Shipment;
import com.ricardocreates.infra.data.jpa.model.BaggageEntity;
import com.ricardocreates.infra.data.jpa.model.CargoEntity;
import com.ricardocreates.infra.data.jpa.model.FlightEntity;
import com.ricardocreates.infra.data.jpa.model.ShipmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightJpaMapper {
    Flight flightToDomain(FlightEntity flightEntity);

    List<Flight> flightToDomainList(List<FlightEntity> flightEntity);

    Cargo cargoToDomain(CargoEntity cargoEntity);

    List<Cargo> cargoToDomainList(List<CargoEntity> cargoEntity);


    Baggage baggageToDomain(BaggageEntity value);

    Shipment shipmentToDomain(ShipmentEntity value);
}
