package com.ricardocreates.infra.rest.controller.flight.mapper;

import com.ricardocreates.domain.entities.flightinfo.FlightIataInfo;
import com.ricardocreates.domain.entities.flightinfo.FlightInfo;
import com.swagger.client.codegen.rest.model.FlightIataInfoDto;
import com.swagger.client.codegen.rest.model.FlightInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightDtoMapper {
    FlightInfoDto flightInfoFromDomain(FlightInfo flightInfo);

    FlightIataInfoDto flightIataInfoFromDomain(FlightIataInfo flightIataInfo);
}
