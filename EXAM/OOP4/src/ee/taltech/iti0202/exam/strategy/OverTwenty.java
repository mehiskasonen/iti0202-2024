package ee.taltech.iti0202.exam.strategy;

import ee.taltech.iti0202.exam.Strategy;

import java.math.BigDecimal;

public abstract class OverTwenty implements Strategy {
    private static final Integer ORDER_SUM_20 = 20;

    public OverTwenty() {
    }

    @Override
    public BigDecimal getDiscount(Integer orderSum) {
        if (orderSum > ORDER_SUM_20) {
            return BigDecimal.valueOf(orderSum * 0.05);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
