package com.ricardocreates.application.operation;

import com.ricardocreates.application.operation.flight.FlightOperationImpl;
import com.ricardocreates.domain.entities.exception.AviationNotFoundException;
import com.ricardocreates.domain.entities.flight.Baggage;
import com.ricardocreates.domain.entities.flight.Cargo;
import com.ricardocreates.domain.entities.flight.Flight;
import com.ricardocreates.domain.entities.flightinfo.FlightIataInfo;
import com.ricardocreates.domain.entities.flightinfo.FlightInfo;
import com.ricardocreates.domain.operation.LoadCalculator;
import com.ricardocreates.domain.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FlightOperationImplTest {
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private LoadCalculator loadCalculator;
    @InjectMocks
    private FlightOperationImpl flightOperationImpl;

    @Test
    void should_getFlightInfo() throws ParseException {
        //GIVEN
        BigDecimal baggageWeight = BigDecimal.TEN;
        BigDecimal cargoWeight = BigDecimal.ONE;
        Flight foundFlight = mockFlight(1);
        given(flightRepository.findFlightByFlightNumberAndDate(anyInt(), any(Instant.class)))
                .willReturn(foundFlight);
        given(loadCalculator.calculateLoadWeight(anyList()))
                .willReturn(baggageWeight)
                .willReturn(cargoWeight);
        Integer flightNumber = 1;
        Instant flightDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-31").toInstant();
        //WHEN
        FlightInfo flightInfo = flightOperationImpl.getFlightInfo(flightNumber, flightDate);
        //THEN
        verify(flightRepository).findFlightByFlightNumberAndDate(anyInt(), any(Instant.class));
        verify(loadCalculator, times(2)).calculateLoadWeight(anyList());
        assertThat(flightInfo).isNotNull();
        assertThat(flightInfo.getBaggageWeight()).isEqualTo(baggageWeight);
        assertThat(flightInfo.getCargoWeight()).isEqualTo(cargoWeight);
        assertThat(flightInfo.getTotalWeight()).isEqualTo(baggageWeight.add(cargoWeight));
    }

    @Test
    void should_not_getFlightInfo_notFlightFound() throws ParseException {
        //GIVEN
        given(flightRepository.findFlightByFlightNumberAndDate(anyInt(), any(Instant.class)))
                .willThrow(new AviationNotFoundException(""));
        final Integer flightNumber = 1;
        final Instant flightDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-31").toInstant();
        //WHEN
        assertThatThrownBy(() -> flightOperationImpl.getFlightInfo(flightNumber, flightDate))
                .isInstanceOf(AviationNotFoundException.class);
        //THEN
        verify(flightRepository).findFlightByFlightNumberAndDate(anyInt(), any(Instant.class));
        verify(loadCalculator, times(0)).calculateLoadWeight(anyList());
    }

    @Test
    void should_getFlightInfoIata() throws ParseException {
        //GIVEN
        final String iataCode = "ALC";
        final Instant flightDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-31").toInstant();
        final List<Flight> departureFlights = mockFlights(0, 5);
        final List<Flight> arrivingFlights = mockFlights(5, 10);
        given(flightRepository.findFlightDeparturesByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class)))
                .willReturn(departureFlights);
        given(flightRepository.findFlightArrivingByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class)))
                .willReturn(arrivingFlights);
        //WHEN
        FlightIataInfo flightIataInfo = flightOperationImpl.getFlightInfoIata(iataCode, flightDate);
        //THEN
        verify(flightRepository).findFlightDeparturesByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class));
        verify(flightRepository).findFlightArrivingByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class));
        assertThat(flightIataInfo).isNotNull();
        assertThat(flightIataInfo.getNumberFlightArriving()).isEqualTo(5);
        assertThat(flightIataInfo.getNumberFlightDeparting()).isEqualTo(5);
        assertThat(flightIataInfo.getTotalPiecesBaggageArriving()).isEqualTo(5 * (1 + 2 + 3));
        assertThat(flightIataInfo.getTotalPiecesBaggageDeparting()).isEqualTo(5 * (1 + 2 + 3));
    }

    @Test
    void should_getFlightInfoIata_noFlights() throws ParseException {
        //GIVEN
        final String iataCode = "ALC";
        final Instant flightDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-31").toInstant();
        given(flightRepository.findFlightDeparturesByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class)))
                .willReturn(new ArrayList<>());
        given(flightRepository.findFlightArrivingByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class)))
                .willReturn(new ArrayList<>());
        //WHEN
        FlightIataInfo flightIataInfo = flightOperationImpl.getFlightInfoIata(iataCode, flightDate);
        //THEN
        verify(flightRepository).findFlightDeparturesByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class));
        verify(flightRepository).findFlightArrivingByFlightIataAndDate(ArgumentMatchers.eq(iataCode), any(Instant.class));
        assertThat(flightIataInfo).isNotNull();
        assertThat(flightIataInfo.getNumberFlightArriving()).isEqualTo(0);
        assertThat(flightIataInfo.getNumberFlightDeparting()).isEqualTo(0);
        assertThat(flightIataInfo.getTotalPiecesBaggageArriving()).isEqualTo(0);
        assertThat(flightIataInfo.getTotalPiecesBaggageDeparting()).isEqualTo(0);
    }

    private Flight mockFlight(int flightId) {
        Cargo cargo = mockCargo(flightId);

        return Flight.builder()
                .flightId(flightId)
                .flightNumber(12)
                .cargo(cargo)
                .build();
    }

    private Cargo mockCargo(int flightId) {
        Cargo cargo = new Cargo();
        cargo.setFlightId(flightId);

        Baggage b1 = new Baggage();
        b1.setPieces(1);
        Baggage b2 = new Baggage();
        b2.setPieces(2);
        Baggage b3 = new Baggage();
        b3.setPieces(3);
        cargo.setBaggageList(List.of(b1, b2, b3));

        cargo.setShipmentList(new ArrayList<>());

        return cargo;
    }

    private List<Flight> mockFlights(int from, int to) {
        List<Flight> flightList = new ArrayList<>();
        for (int index = from; index < to; index++) {
            var flight = mockFlight(index);
            flightList.add(flight);
        }
        return flightList;
    }

}
