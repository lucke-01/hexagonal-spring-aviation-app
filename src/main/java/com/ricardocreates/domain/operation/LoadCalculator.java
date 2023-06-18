package com.ricardocreates.domain.operation;

import com.ricardocreates.domain.entities.flight.Load;

import java.math.BigDecimal;
import java.util.List;

public interface LoadCalculator {
    BigDecimal lbToKg(int lb);

    BigDecimal calculateLoadWeight(List<? extends Load> loadList);

    BigDecimal calculateLoadWeight(Load load);
}
