import ee.taltech.iti0202.bigdecimal.BigNumber;

import java.math.BigDecimal;
import java.math.BigInteger;


public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        BigNumber bigNumber = new BigNumber();
        System.out.println(bigNumber.power(2, 3)); // 8
        System.out.println(bigNumber.power(3, 3)); // 27

        System.out.println(bigNumber.isSame(BigDecimal.valueOf(30414.878), BigDecimal.valueOf(30414.879), 2));
        System.out.println(bigNumber.fibonacci(-1)); //12586269025

        System.out.println(bigNumber.lucas(6));

        //10420180999117162549
        //12200160415121876738


        //302544139324403592003
        //10420180999117162549
        //218922995834555169026
                                                    //through fibbo 28143753123
                                                    //pure lucas 218922995834555169026
        //BigInteger target = new BigInteger("1772482150909261266935809714765791508254729174221668720920311171304840749088492994378238885428132998042510078073991707620874171944133780111288137927200206172178");
        //int n = bigNumber.findLucasNumber(target);
        //System.out.println("The value of n for the given Lucas number is: " + n);


    }
}