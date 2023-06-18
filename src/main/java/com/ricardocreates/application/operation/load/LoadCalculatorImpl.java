package com.ricardocreates.application.operation.load;

import com.ricardocreates.domain.entities.flight.Load;
import com.ricardocreates.domain.entities.flight.WeightUnit;
import com.ricardocreates.domain.operation.LoadCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadCalculatorImpl implements LoadCalculator {
    public static final double LB_KG_RELATION = 0.45359237;

    public BigDecimal lbToKg(int lb) {

        BigDecimal lbDDecimal = new BigDecimal(lb);
        BigDecimal lbKgRelation = new BigDecimal(LB_KG_RELATION);

        return lbDDecimal.multiply(lbKgRelation).setScale(3, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateLoadWeight(List<? extends Load> loadList) {
        return loadList.stream()
                .map(this::calculateLoadWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateLoadWeight(Load load) {
        var weight = new BigDecimal(load.getWeight());
        if (WeightUnit.LB.equals(load.getWeightUnit())) {
            weight = lbToKg(load.getWeight());
        }
        return weight.multiply(new BigDecimal(load.getPieces()));
    }
}
