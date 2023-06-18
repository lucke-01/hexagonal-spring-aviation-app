package com.ricardocreates.infra.data.json.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardocreates.domain.entities.exception.AviationNotFoundException;
import com.ricardocreates.domain.entities.flight.Cargo;
import com.ricardocreates.domain.entities.flight.Flight;
import com.ricardocreates.domain.repository.FlightRepository;
import com.ricardocreates.infra.data.json.model.CargoEntity;
import com.ricardocreates.infra.data.json.model.FlightEntity;
import com.ricardocreates.infra.data.json.repository.mapper.FlightJsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
//we will use JSON by default
@Profile("!jpa")
public class JsonFlightRepository implements FlightRepository {
    private static final String FLIGHT_JSON_PATH = "flight-data/flight.json";
    private static final String CARGO_JSON_PATH = "flight-data/cargo.json";
    private final JsonReader jsonReader;
    private final ObjectMapper objectMapper;
    private final FlightJsonMapper flightJsonMapper;

    //since we are working with resources file we will store all Fights and Cargos as a singleton collections
    //this way the json file will be only loaded once per application
    private List<Flight> allFlights = new ArrayList<>();
    private List<Cargo> allCargos = new ArrayList<>();

    @Override
    public List<Flight> findAllFlight() {
        //this way the json file will be only loaded once
        if (allFlights.isEmpty()) {
            String flightCollectionString = jsonReader.getResourceFileAsString(FLIGHT_JSON_PATH);

            List<FlightEntity> flightEntity = null;
            try {
                flightEntity = objectMapper.readValue(flightCollectionString, new TypeReference<List<FlightEntity>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            System.out.println(flightEntity);
            allFlights = flightJsonMapper.flightToDomainList(flightEntity);
        }
        return allFlights;
    }

    @Override
    public List<Cargo> findAllCargo() {
        //this way the json file will be only loaded once
        if (allCargos.isEmpty()) {
            String cargoCollectionString = jsonReader.getResourceFileAsString(CARGO_JSON_PATH);
            List<CargoEntity> cargoEntityList = null;
            try {
                cargoEntityList = objectMapper.readValue(cargoCollectionString, new TypeReference<List<CargoEntity>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            System.out.println(cargoEntityList);

            allCargos = flightJsonMapper.cargoToDomainList(cargoEntityList);
        }
        return allCargos;
    }

    @Override
    public Cargo findCargoByFlightId(int flyId) {
        Cargo cargoFound = null;
        List<Cargo> allCargo = findAllCargo();

        cargoFound = allCargo.stream().filter(cargo -> cargo.getFlightId() == flyId)
                .findFirst().orElse(null);

        return cargoFound;
    }

    @Override
    public Flight findFlightByFlightNumberAndDate(int flightNumber, Instant date) {
        Flight foundFly = null;
        final List<Flight> flights = findAllFlight();

        //find fly by id and date
        foundFly = flights.stream()
                .filter(flight -> flight.getFlightNumber() == flightNumber && flight.getDepartureDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new AviationNotFoundException(
                        String.format("Flight not found by flightNumber %s and date %s", flightNumber, date))
                );
        //set cargo to fly may be null (maybe better to force every Flight to have a cargo but depends on business logic)
        final Cargo foundFlyCargo = findCargoByFlightId(foundFly.getFlightId());
        foundFly.setCargo(foundFlyCargo);

        return foundFly;
    }

    @Override
    public List<Flight> findFlightDeparturesByFlightIataAndDate(String iata, Instant date) {
        final List<Flight> flights = findAllFlight();

        return flights.stream()
                .filter(flight -> iata.equalsIgnoreCase(flight.getDepartureAirportIATACode()))
                // Since I have differents dates per fly in my json (which make impossible to get more than one Flight)
                // I will not filter by date in order to be able to get more than one Flights
                //.filter(flight -> date.equals(flight.getDepartureDate()))
                .map(flight -> flight.toBuilder()
                        .cargo(findCargoByFlightId(flight.getFlightId()))
                        .build())
                .toList();
    }

    @Override
    public List<Flight> findFlightArrivingByFlightIataAndDate(String iata, Instant date) {
        final List<Flight> flights = findAllFlight();

        return flights.stream()
                .filter(flight -> iata.equalsIgnoreCase(flight.getArrivalAirportIATACode()))
                // Since I have differents dates per fly in my json (which make impossible to get more than one Flight)
                // I will not filter by date in order to be able to get more than one Flights
                //.filter(flight -> date.equals(flight.getDepartureDate()))
                .map(flight -> flight.toBuilder()
                        .cargo(findCargoByFlightId(flight.getFlightId()))
                        .build())
                .toList();
    }
}
