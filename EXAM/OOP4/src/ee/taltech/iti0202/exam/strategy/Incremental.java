package ee.taltech.iti0202.exam.strategy;

import ee.taltech.iti0202.exam.Strategy;

import java.math.BigDecimal;

public abstract class Incremental implements Strategy {
    private static final Integer ORDER_SUM_20 = 20;
    private static final Integer ORDER_SUM_40 = 40;
    private static final Integer ORDER_SUM_50 = 50;



    public Incremental() {

    }

    @Override
    public BigDecimal getDiscount(Integer orderSum) {
        if (orderSum > ORDER_SUM_20) {
            if (orderSum < ORDER_SUM_40 ) {
                return BigDecimal.valueOf(orderSum * 0.05);
            }
            if (orderSum < ORDER_SUM_50) {
                return BigDecimal.valueOf(orderSum * 0.1);
            } else {
                return BigDecimal.valueOf(orderSum * 0.15);
            }
        }
        return null;
    }
}
