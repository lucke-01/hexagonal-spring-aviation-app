package com.ricardocreates.application.operation;

import com.ricardocreates.application.operation.load.LoadCalculatorImpl;
import com.ricardocreates.domain.entities.flight.Baggage;
import com.ricardocreates.domain.entities.flight.Load;
import com.ricardocreates.domain.entities.flight.Shipment;
import com.ricardocreates.domain.entities.flight.WeightUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LoadCalculatorImplTest {
    @InjectMocks
    private LoadCalculatorImpl loadCalculatorImpl;

    @Test
    void should_transformLbToKg() {
        //GIVEN
        int lb = 5;
        //WHEN
        BigDecimal result = loadCalculatorImpl.lbToKg(lb);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(new BigDecimal("2.268"));
    }

    @Test
    void should_transformLbToKg_0() {
        //GIVEN
        int lb = 0;
        //WHEN
        BigDecimal result = loadCalculatorImpl.lbToKg(lb);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.doubleValue()).isEqualTo(0.0);
    }

    @Test
    void should_calculateLoadWeight_kg() {
        //GIVEN
        Load load1 = new Baggage();
        load1.setId(1);
        load1.setWeightUnit(WeightUnit.KG);
        load1.setPieces(2);
        load1.setWeight(100);

        //WHEN
        BigDecimal result = loadCalculatorImpl.calculateLoadWeight(load1);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.doubleValue()).isEqualTo(200);
    }

    @Test
    void should_calculateLoadWeight_lb() {
        //GIVEN
        Load load1 = new Baggage();
        load1.setId(1);
        load1.setWeightUnit(WeightUnit.LB);
        load1.setPieces(2);
        load1.setWeight(2);

        //WHEN
        BigDecimal result = loadCalculatorImpl.calculateLoadWeight(load1);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.doubleValue()).isEqualTo(1.814);
    }

    @Test
    void should_calculateLoadWeight_list() {
        //GIVEN
        Load load1 = new Baggage();
        load1.setId(1);
        load1.setWeightUnit(WeightUnit.LB);
        load1.setPieces(2);
        load1.setWeight(2);
        Load load2 = new Shipment();
        load2.setId(2);
        load2.setWeightUnit(WeightUnit.KG);
        load2.setPieces(2);
        load2.setWeight(100);

        var loadList = List.of(load1, load2);

        //WHEN
        BigDecimal result = loadCalculatorImpl.calculateLoadWeight(loadList);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.doubleValue()).isEqualTo(201.814);
    }

}
