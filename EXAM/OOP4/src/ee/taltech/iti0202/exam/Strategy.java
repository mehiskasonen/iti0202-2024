package ee.taltech.iti0202.exam;

import java.math.BigDecimal;

public interface Strategy {

    BigDecimal getDiscount(Double orderSum, Integer orderCount);

    BigDecimal getDiscount(Integer orderCount);
}
