package com.ricardocreates.application.operation.flight;

import com.ricardocreates.domain.entities.flight.Flight;
import com.ricardocreates.domain.entities.flight.Load;
import com.ricardocreates.domain.entities.flightinfo.FlightIataInfo;
import com.ricardocreates.domain.entities.flightinfo.FlightInfo;
import com.ricardocreates.domain.operation.FlightOperation;
import com.ricardocreates.domain.operation.LoadCalculator;
import com.ricardocreates.domain.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightOperationImpl implements FlightOperation {

    private final FlightRepository flightRepository;
    private final LoadCalculator loadCalculator;

    @Override
    public FlightInfo getFlightInfo(Integer flightNumber, Instant flightDate) {
        final FlightInfo flightInfo = FlightInfo.builder().build();

        //get flight
        Flight flight = flightRepository.findFlightByFlightNumberAndDate(flightNumber, flightDate);

        var baggageWeight = loadCalculator.calculateLoadWeight(flight.getCargo().getBaggageList());
        var cargoWeight = loadCalculator.calculateLoadWeight(flight.getCargo().getShipmentList());
        var totalWeight = baggageWeight.add(cargoWeight);
        //calculate weight info
        flightInfo.setBaggageWeight(baggageWeight);
        flightInfo.setCargoWeight(cargoWeight);
        flightInfo.setTotalWeight(totalWeight);

        return flightInfo;
    }

    @Override
    public FlightIataInfo getFlightInfoIata(String iataCode, Instant flightDate) {
        final FlightIataInfo flightIataInfo = FlightIataInfo.builder().build();

        List<Flight> departingFlights = flightRepository.findFlightDeparturesByFlightIataAndDate(iataCode, flightDate);
        List<Flight> arrivingFlights = flightRepository.findFlightArrivingByFlightIataAndDate(iataCode, flightDate);

        var totalDepartingPieces = departingFlights.stream()
                .flatMap(flight -> flight.getCargo().getBaggageList().stream())
                .mapToInt(Load::getPieces).sum();
        var totalArrivingPieces = arrivingFlights.stream()
                .flatMap(flight -> flight.getCargo().getBaggageList().stream())
                .mapToInt(Load::getPieces).sum();

        flightIataInfo.setNumberFlightDeparting(departingFlights.size());
        flightIataInfo.setNumberFlightArriving(arrivingFlights.size());

        flightIataInfo.setTotalPiecesBaggageArriving(totalArrivingPieces);
        flightIataInfo.setTotalPiecesBaggageDeparting(totalDepartingPieces);

        return flightIataInfo;
    }


}
