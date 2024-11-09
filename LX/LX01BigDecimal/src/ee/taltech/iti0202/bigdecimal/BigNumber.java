package ee.taltech.iti0202.bigdecimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BigNumber {
    /**
     * Multiply 2 int values and return product in BigInteger
     * @param factor1 first factor
     * @param factor2 second factor
     * @return factor1 x factor2
     */
    public BigInteger multiplyBigInteger(int factor1, int factor2) {
        BigInteger f1 = BigInteger.valueOf(factor1);
        BigInteger f2 = BigInteger.valueOf(factor2);
        return f1.multiply(f2);
    }

    /**
     * Divide 2 values and return quotient in BigInteger
     * If divisor is 0, return 0
     * @param dividend dividend
     * @param divisor divisor
     * @return dividend / divisor
     */
    public BigInteger divideBigInteger(BigInteger dividend, int divisor) {
        return dividend.divide(BigInteger.valueOf(divisor));
    }

    /**
     * Add 2 values and return sum in BigInteger
     * @param add1 first value
     * @param add2 second value
     * @return add1 + add2
     */
    public BigInteger addBigInteger(int add1, int add2) {
        return BigInteger.valueOf(add1).add(BigInteger.valueOf(add2));
    }

    /**
     * Subtract 2 values and return difference in BigInteger
     * @param minuend first value
     * @param subtrahend second value
     * @return value1 - value2
     */
    public BigInteger subtractBigInteger(BigInteger minuend, int subtrahend) {
        return minuend.subtract(BigInteger.valueOf(subtrahend));
    }

    /**
     * Multiply double value with int multiplier, round according to rounding
     * and return in BigDecimal
     * @param value value to calculate
     * @param multiplier multiplier to use
     * @param rounding rounding to use
     * @return value multiplied by multiplier and rounded by rounding
     */
    public BigDecimal multiplyAndRoundBigDecimal(double value, int multiplier, int rounding) {
        BigDecimal multiplicationValue = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(multiplier));

        int newScale = -1;
        BigDecimal res = multiplicationValue.setScale(newScale, rounding);

        BigDecimal finalRes = res.setScale(rounding, RoundingMode.HALF_UP);
        BigDecimal res2 = res.setScale(newScale, rounding);
        System.out.println(res2.toEngineeringString());
        return res;
        //return multiplicationValue.setScale(rounding);
    }

    /**
     * Calculate n factorial and return in BigInteger
     * If n < 0, it should return 1
     * @param n n-th factorial to calculate
     * @return n-th factorial
     */
    public BigInteger factorial(int n) {
        if (n < 0) {
            return BigInteger.ONE;
        }
        BigInteger factorial = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    /**
     * Calculate base to the power of exponent and return in BigInteger
     * @param base base
     * @param exponent exponent
     * @return base to the power of exponent
     */
    public BigInteger power(int base, int exponent) {
        if (base == 0) {
            return BigInteger.ZERO;
        }
        if (exponent == 0) {
            return BigInteger.ONE;
        }
        BigInteger power = BigInteger.valueOf(base);
        BigInteger newBase = BigInteger.valueOf(base);
        for (int i = 1; i < exponent; i++) {
            power = newBase.multiply(BigInteger.valueOf(base));
            newBase = power;
        }
        return power;
    }

    /**
     * Round val1 and val2 with the rounding given, and check if they are equal after that
     * @param val1 first value to round
     * @param val2 second value to round
     * @param rounding rounding to use
     * @return true or false if val1 and val2 are equal after rounded with rounding
     */
    public boolean isSame(BigDecimal val1, BigDecimal val2, int rounding) {
        BigDecimal roundedVal1 = val1.setScale(Math.abs(rounding), RoundingMode.HALF_EVEN);
        BigDecimal roundedVal2 = val2.setScale(Math.abs(rounding), RoundingMode.HALF_EVEN);
        return roundedVal1.compareTo(roundedVal2) == 0;
    }

    /**
     *
     * @param n n-th fib number to calculate
     * @return n-th fib number value
     */
    public BigInteger fibonacci(int n) {
        if (n < 1) {
            return BigInteger.ZERO;
        }
        BigInteger fib = BigInteger.ONE;
        BigInteger prev = BigInteger.ONE;
        for (int i = 0; i < n - 2; i++) {
            fib = fib.add(prev);
            prev = fib.subtract(prev);
        }
        return fib;
    }

    /**
     *
     * @param n n-th luc number to calculate
     * @return n-th lucas number
     */
    public BigInteger lucas(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.TWO;
        if (n == 2) return BigInteger.ONE;

        BigInteger prev = BigInteger.TWO;
        BigInteger luc = BigInteger.ONE;
        BigInteger next;
        for (int i = 3; i <= n; i++) {
            next = luc.add(prev);
            prev = luc;
            luc = next;

            //i = 3 | luc value of 1
            //[1772482150909261266935809714765791508254729174221668720920311171304840749088492994378238885428132998042510078073991707620874171944133780111288137927200206172178] but found
            //[1095454213714443800439711069811084084866955252702160132828229205763100339585868530671329388645095337700713414636870707326599962634279114040577698660293795566051]


            //i = 3
            //[1772482150909261266935809714765791508254729174221668720920311171304840749088492994378238885428132998042510078073991707620874171944133780111288137927200206172178] but found
            //[1282580133288445520249428042821691721926228555073433259117832543935524517370269502761209208985898866365648729771769166037844495693336090874888422488378882816517]

            //i = 2
            //[1772482150909261266935809714765791508254729174221668720920311171304840749088492994378238885428132998042510078073991707620874171944133780111288137927200206172178] but found
            //[2075258248956075293812474413699283657523956490998631056433186460501732803022315513905388741529563601054436111241315790492659315135874492513377129537936442277373]

            //[1772482150909261266935809714765791508254729174221668720920311171304840749088492994378238885428132998042510078073991707620874171944133780111288137927200206172178]
            //[1095454213714443800439711069811084084866955252702160132828229205763100339585868530671329388645095337700713414636870707326599962634279114040577698660293795566051]
            //1772482150909261266935809714765791508254729174221668720920311171304840749088492994378238885428132998042510078073991707620874171944133780111288137927200206172178]
            //1282580133288445520249428042821691721926228555073433259117832543935524517370269502761209208985898866365648729771769166037844495693336090874888422488378882816517]
        }
        return luc;
    }

    public int findLucasNumber(BigInteger target) {
        BigInteger[][] matrix = {{BigInteger.ONE, BigInteger.ONE}, {BigInteger.ONE, BigInteger.ZERO}};
        int n = 0;

        while (true) {
            BigInteger lucas = matrix[0][0];
            if (lucas.equals(target)) {
                return n;
            }
            matrix = multiply(matrix, new BigInteger[][]{{BigInteger.valueOf(2), BigInteger.valueOf(1)}, {BigInteger.valueOf(1), BigInteger.valueOf(1)}});
            n++;
        }
    }

    private BigInteger[][] multiply(BigInteger[][] a, BigInteger[][] b) {
        BigInteger[][] result = new BigInteger[2][2];
        result[0][0] = a[0][0].multiply(b[0][0]).add(a[0][1].multiply(b[1][0]));
        result[0][1] = a[0][0].multiply(b[0][1]).add(a[0][1].multiply(b[1][1]));
        result[1][0] = a[1][0].multiply(b[0][0]).add(a[1][1].multiply(b[1][0]));
        result[1][1] = a[1][0].multiply(b[0][1]).add(a[1][1].multiply(b[1][1]));
        return result;
    }
}
