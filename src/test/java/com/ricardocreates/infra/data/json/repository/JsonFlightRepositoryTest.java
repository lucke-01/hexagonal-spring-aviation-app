package com.ricardocreates.infra.data.json.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardocreates.config.jackson.JacksonConfiguration;
import com.ricardocreates.domain.entities.exception.AviationNotFoundException;
import com.ricardocreates.domain.entities.flight.Cargo;
import com.ricardocreates.domain.entities.flight.Flight;
import com.ricardocreates.infra.data.json.repository.mapper.FlightJsonMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class JsonFlightRepositoryTest {
    @Spy
    private JsonReader jsonReader = new JsonReader();
    @Spy
    private ObjectMapper objectMapper = new JacksonConfiguration().getJacksonObjectMapper();
    @Spy
    private FlightJsonMapperImpl flightJsonMapper = new FlightJsonMapperImpl();
    @InjectMocks
    private JsonFlightRepository jsonFlightRepository;

    @Test
    void should_findAllFlight_onlyOnce() throws JsonProcessingException {
        //given
        // When
        List<Flight> flightList = jsonFlightRepository.findAllFlight();
        List<Flight> flightList2 = jsonFlightRepository.findAllFlight();
        // Then
        //only be called once
        verify(jsonReader, times(1)).getResourceFileAsString(any());
        verify(objectMapper).readValue(any(String.class), any(TypeReference.class));
        assertThat(flightList).isNotNull();
        assertThat(flightList2).isNotNull();
        assertThat(flightList).hasSize(5);
        assertThat(flightList).isNotNull();
        assertThat(flightList.get(0).getFlightId()).isEqualTo(0);
        assertThat(flightList.get(0).getFlightNumber()).isEqualTo(7539);
    }

    @Test
    void should_findAllCargo_onlyOnce() throws JsonProcessingException {
        //GIVEN
        //WHEN
        List<Cargo> cargoList = jsonFlightRepository.findAllCargo();
        List<Cargo> cargoList2 = jsonFlightRepository.findAllCargo();
        //THEN
        verify(jsonReader, times(1)).getResourceFileAsString(any());
        verify(objectMapper).readValue(any(String.class), any(TypeReference.class));
        assertThat(cargoList).isNotNull();
        assertThat(cargoList2).isNotNull();
        assertThat(cargoList).hasSize(5);
        assertThat(cargoList.get(0).getFlightId()).isEqualTo(0);
        assertThat(cargoList.get(0).getBaggageList()).hasSize(6);
        assertThat(cargoList.get(0).getShipmentList()).hasSize(5);
    }

    @Test
    void should_findCargoByFlightId() throws JsonProcessingException {
        //GIVEN
        int flightId = 1;
        //WHEN
        Cargo cargo = jsonFlightRepository.findCargoByFlightId(flightId);
        //THEN
        verify(jsonReader, times(1)).getResourceFileAsString(any());
        verify(objectMapper).readValue(any(String.class), any(TypeReference.class));
        assertThat(cargo).isNotNull();
        assertThat(cargo.getFlightId()).isEqualTo(flightId);
        assertThat(cargo.getShipmentList()).hasSize(5);
        assertThat(cargo.getBaggageList()).hasSize(4);
    }

    @Test
    void should_not_findCargoByFlightId_notFound() throws JsonProcessingException {
        //GIVEN
        int flightId = -1;
        //WHEN
        Cargo cargo = jsonFlightRepository.findCargoByFlightId(flightId);
        //THEN
        verify(jsonReader, times(1)).getResourceFileAsString(any());
        verify(objectMapper).readValue(any(String.class), any(TypeReference.class));
        assertThat(cargo).isNull();
    }

    @Test
    void should_findFlightByFlightNumberAndDate() throws JsonProcessingException {
        //GIVEN
        var flights = jsonFlightRepository.findAllFlight();
        var firstFlight = flights.get(0);
        //WHEN
        Flight flight = jsonFlightRepository
                .findFlightByFlightNumberAndDate(firstFlight.getFlightNumber(), firstFlight.getDepartureDate());
        //THEN
        verify(jsonReader, times(2)).getResourceFileAsString(any());
        verify(objectMapper, times(2)).readValue(any(String.class), any(TypeReference.class));
        assertThat(flight).isNotNull();
        assertThat(flight.getFlightNumber()).isEqualTo(firstFlight.getFlightNumber());
        assertThat(flight.getDepartureDate()).isEqualTo(firstFlight.getDepartureDate());
        assertThat(flight.getCargo()).isNotNull();
    }

    @Test
    void should_not_findFlightByFlightNumberAndDate_notFound() throws JsonProcessingException {
        //GIVEN
        int flightNotFound = -1;
        //WHEN
        assertThatThrownBy(() -> jsonFlightRepository
                .findFlightByFlightNumberAndDate(flightNotFound, Instant.now()))
                .isInstanceOf(AviationNotFoundException.class);
        //THEN
        verify(jsonReader, times(1)).getResourceFileAsString(any());
        verify(objectMapper, times(1)).readValue(any(String.class), any(TypeReference.class));
    }

    @Test
    void should_findFlightDeparturesByFlightIataAndDate() throws JsonProcessingException {
        //GIVEN
        var flights = jsonFlightRepository.findAllFlight();
        var firstFlight = flights.get(0);

        //WHEN
        List<Flight> result = jsonFlightRepository
                .findFlightDeparturesByFlightIataAndDate(firstFlight.getDepartureAirportIATACode(), firstFlight.getDepartureDate());
        //THEN
        verify(jsonReader, times(2)).getResourceFileAsString(any());
        verify(objectMapper, times(2)).readValue(any(String.class), any(TypeReference.class));
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCargo()).isNotNull();
        assertThat(result.get(1).getCargo()).isNotNull();
        assertThat(result.get(0).getDepartureAirportIATACode()).isEqualTo(firstFlight.getDepartureAirportIATACode());
        assertThat(result.get(1).getDepartureAirportIATACode()).isEqualTo(firstFlight.getDepartureAirportIATACode());
    }

    @Test
    void should_findFlightArrivingByFlightIataAndDate() throws JsonProcessingException {
        //GIVEN
        var flights = jsonFlightRepository.findAllFlight();
        var firstFlight = flights.get(0);

        //WHEN
        List<Flight> result = jsonFlightRepository
                .findFlightArrivingByFlightIataAndDate(firstFlight.getArrivalAirportIATACode(), firstFlight.getDepartureDate());
        //THEN
        verify(jsonReader, times(2)).getResourceFileAsString(any());
        verify(objectMapper, times(2)).readValue(any(String.class), any(TypeReference.class));
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getCargo()).isNotNull();
        assertThat(result.get(0).getArrivalAirportIATACode()).isEqualTo(firstFlight.getArrivalAirportIATACode());
        assertThat(result.get(1).getCargo()).isNotNull();
        assertThat(result.get(1).getArrivalAirportIATACode()).isEqualTo(firstFlight.getArrivalAirportIATACode());
        assertThat(result.get(2).getCargo()).isNotNull();
        assertThat(result.get(2).getArrivalAirportIATACode()).isEqualTo(firstFlight.getArrivalAirportIATACode());
    }
}
