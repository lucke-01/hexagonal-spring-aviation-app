package com.ricardocreates.infra.data.jpa.repository;

import com.ricardocreates.domain.entities.flight.Cargo;
import com.ricardocreates.domain.entities.flight.Flight;
import com.ricardocreates.domain.repository.FlightRepository;
import com.ricardocreates.infra.data.jpa.mapper.FlightJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Profile("jpa")
public class JpaFlightRepositoryImpl implements FlightRepository {
    private final FlightJpaMapper flightJpaMapper;
    private final DataJpaFlightRepository dataJpaFlightRepository;
    private final DataJpaCargoRepository dataJpaCargoRepository;


    @Override
    public List<Flight> findAllFlight() {
        return flightJpaMapper.flightToDomainList(dataJpaFlightRepository.findAll());
    }

    @Override
    public List<Cargo> findAllCargo() {
        return flightJpaMapper.cargoToDomainList(dataJpaCargoRepository.findAll());
    }

    @Override
    public Cargo findCargoByFlightId(int id) {
        return flightJpaMapper.cargoToDomain(dataJpaCargoRepository.findByFlightId(id));
    }

    @Override
    public Flight findFlightByFlightNumberAndDate(int flightNumber, Instant date) {
        return flightJpaMapper.flightToDomain(dataJpaFlightRepository.findFlightByFlightNumberAndDepartureDate(flightNumber, date));
    }

    @Override
    public List<Flight> findFlightDeparturesByFlightIataAndDate(String iata, Instant date) {
        return flightJpaMapper.flightToDomainList(
                dataJpaFlightRepository.findByDepartureAirportIATACodeAndDepartureDate(iata, date)
        );
    }

    @Override
    public List<Flight> findFlightArrivingByFlightIataAndDate(String iata, Instant date) {
        return flightJpaMapper.flightToDomainList(
                dataJpaFlightRepository.findByArrivalAirportIATACodeAndDepartureDate(iata, date)
        );
    }
}
