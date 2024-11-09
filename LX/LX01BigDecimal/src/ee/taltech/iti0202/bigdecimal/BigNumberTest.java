package ee.taltech.iti0202.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class BigNumberTest {

    BigNumber big = new BigNumber();

    @org.junit.jupiter.api.Test
    void multiplyAndRoundBigDecimal() {
        double value = 12345.67;
        int multiplier = 885;
        int rounding = 3; // Assuming you want 3 decimal places
        BigDecimal expected = new BigDecimal("10925917.950"); // Expected result

        // Call the method
        BigDecimal result = big.multiplyAndRoundBigDecimal(value, multiplier, rounding);

        // Assert the result
        assertEquals(expected, result);
    }

    @Test
    public void testIsSame_PositiveRounding() {
        BigDecimal val1 = new BigDecimal("1.234");
        BigDecimal val2 = new BigDecimal("1.235");
        int rounding = 2;
        boolean result = big.isSame(val1, val2, rounding);
        assertTrue(result);
    }

    @Test
    public void testIsSame_NegativeRounding() {
        BigDecimal val1 = new BigDecimal("1.234");
        BigDecimal val2 = new BigDecimal("1.235");
        int rounding = -2;
        boolean result = big.isSame(val1, val2, rounding);
        assertFalse(result);
    }

    @Test
    public void testIsSame_RoundingToZero() {
        BigDecimal val1 = new BigDecimal("1.234");
        BigDecimal val2 = new BigDecimal("1.235");
        int rounding = 0;
        boolean result = big.isSame(val1, val2, rounding);
        assertTrue(result);
    }

    @Test
    public void testIsSame_LargeDecimalPlaces() {
        BigDecimal val1 = new BigDecimal("1.23456789");
        BigDecimal val2 = new BigDecimal("1.23456789");
        int rounding = 10;
        boolean result = big.isSame(val1, val2, rounding);
        assertTrue(result);
    }

    @Test
    public void testIsSame_NegativeValues() {
        BigDecimal val1 = new BigDecimal("-1.234");
        BigDecimal val2 = new BigDecimal("-1.235");
        int rounding = 2;
        boolean result = big.isSame(val1, val2, rounding);
        assertFalse(result);
    }

}