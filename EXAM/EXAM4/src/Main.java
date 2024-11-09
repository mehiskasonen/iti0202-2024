import ee.taltech.iti0202.exam.cafe.Food;
import ee.taltech.iti0202.exam.cafe.Order;

import java.util.ArrayList;
import java.util.List;

import static ee.taltech.iti0202.exam.Exam.longestDistanceBetweenEqualSymbols;
import static ee.taltech.iti0202.exam.Exam.timeDiff;

public class Main {
    public static void main(String[] args) {
        /*System.out.println(timeDiff("10:00", "10:00")); // => "00:00"
        System.out.println(timeDiff("10:00", "11:01")); // => "01:01"
        System.out.println(timeDiff("10:00", "09:01")); // => "23:01"
        System.out.println(timeDiff("10:00", "08:59")); // => "22:59"
        System.out.println(timeDiff("10:01", "10:00")); // => "23:59"*/

        /*System.out.println(longestDistanceBetweenEqualSymbols("abcda")); //=> 3
        System.out.println(longestDistanceBetweenEqualSymbols("aaaa")); //=> 2
        System.out.println(longestDistanceBetweenEqualSymbols("aia")); //=> 1
        System.out.println(longestDistanceBetweenEqualSymbols("aiu")); //=> -1
        System.out.println(longestDistanceBetweenEqualSymbols("")); //=> -1
        System.out.println(longestDistanceBetweenEqualSymbols("aab")); //=> 0
        System.out.println(longestDistanceBetweenEqualSymbols("abcdabbg")); //=> 4*/

        Food Coffee = new Food("Coffee", 2.5);
        Food Tea = new Food("Tea", 1.5);
        List<Food> foodList = new ArrayList<>();
        foodList.add(Coffee);
        foodList.add(Tea);

        Order order = new Order(foodList, "Customer1");
        System.out.println(order);

    }
}
