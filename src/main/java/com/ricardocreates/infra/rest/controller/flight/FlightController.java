package com.ricardocreates.infra.rest.controller.flight;

import com.ricardocreates.domain.entities.flightinfo.FlightIataInfo;
import com.ricardocreates.domain.entities.flightinfo.FlightInfo;
import com.ricardocreates.domain.operation.FlightOperation;
import com.ricardocreates.infra.rest.controller.flight.mapper.FlightDtoMapper;
import com.swagger.client.codegen.rest.FlightInfoApi;
import com.swagger.client.codegen.rest.model.FlightIataInfoDto;
import com.swagger.client.codegen.rest.model.FlightInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FlightController implements FlightInfoApi {

    private final FlightOperation flightOperation;
    private final FlightDtoMapper flightDtoMapper;

    @Override
    public ResponseEntity<FlightInfoDto> getFlightInfo(Integer flightNumber, Instant flightDate) {
        FlightInfo flightInfo = flightOperation.getFlightInfo(flightNumber, flightDate);
        FlightInfoDto flightInfoDto = flightDtoMapper.flightInfoFromDomain(flightInfo);
        return ResponseEntity.ok(flightInfoDto);
    }

    @Override
    public ResponseEntity<FlightIataInfoDto> getFlightInfoIata(String iataCode, Instant flightDate) {
        FlightIataInfo flightIataInfo = flightOperation.getFlightInfoIata(iataCode, flightDate);
        FlightIataInfoDto flightInfoDto = flightDtoMapper.flightIataInfoFromDomain(flightIataInfo);
        return ResponseEntity.ok(flightInfoDto);
    }
}