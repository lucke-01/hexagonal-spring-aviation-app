package com.ricardocreates.infra.data.jpa.repository;

import com.ricardocreates.infra.data.jpa.model.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataJpaCargoRepository extends JpaRepository<CargoEntity, Long> {
    CargoEntity findByFlightId(int flightId);
}