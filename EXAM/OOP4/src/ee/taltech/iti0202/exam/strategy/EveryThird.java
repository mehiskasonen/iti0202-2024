package ee.taltech.iti0202.exam.strategy;

import ee.taltech.iti0202.exam.Strategy;

import java.math.BigDecimal;

public abstract class EveryThird implements Strategy {

    public EveryThird() {

    }

    @Override
    public BigDecimal getDiscount(Double orderSum, Integer orderCount) {
        if (orderCount % 3 == 0) {
            return BigDecimal.valueOf(orderSum * 0.1);
        } else {
            return BigDecimal.ZERO;
        }
    }

}
