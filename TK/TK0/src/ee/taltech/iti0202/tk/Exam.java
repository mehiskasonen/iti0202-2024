package ee.taltech.iti0202.tk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Exam {

    /**
     * Return the "centered" average of an array of ints, which we'll say is the mean average of the values,
     * except ignoring the largest and smallest values in the array. If there are multiple copies of the
     * smallest value, ignore just one copy, and likewise for the largest value. Use int division to produce
     * the final average. You may assume that the array is length 3 or more.
     * <p>
     * centeredAverage([1, 2, 3, 4, 100]) → 3
     * centeredAverage([1, 1, 5, 5, 10, 8, 7]) → 5
     * centeredAverage([-10, -4, -2, -4, -2, 0]) → -3
     */
    public static int centeredAverage(List<Integer> nums) {
        /*nums.remove(nums.stream().min(Comparator.naturalOrder()).orElseThrow());
        nums.remove(nums.stream().max(Comparator.naturalOrder()).orElseThrow());*/
        int [] arr = nums.stream().mapToInt(Integer::intValue).toArray();

        int min = arr[0];
        int max = arr[0];
        int sum = 0;


        for (int i = 0; i < arr.length; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
            sum += arr[i];

        }

        sum -= min;
        sum -= max;

        return sum / (arr.length - 2);
    }

    /**
     * Given 3 ints, a b c, return the sum of their rounded values.
     * We'll round an int value up to the next multiple of 10
     * if its rightmost digit is 5 or more, so 15 rounds up to 20.
     * Alternately, round down to the previous multiple of 10
     * if its rightmost digit is less than 5, so 12 rounds down to 10
     *
     * roundSum(16, 17, 18) => 60
     * roundSum(12, 13, 14) => 30
     * roundSum(6, 4, 4) => 10
     */
    public static int roundSum(int a, int b, int c) {
        int lastDigitA = a % 10;
        int lastDigitB = b % 10;
        int lastDigitC = c % 10;

        if (lastDigitA >= 5) {
            int add = 10 - lastDigitA;
            System.out.println("To add " + add);
            a += add;
        } else {
            a = a - lastDigitA;
        }

        if (lastDigitB >= 5) {
            int add = 10 - lastDigitB;
            b += add;
        } else {
            b = b - lastDigitB;
        }

        if (lastDigitC >= 5) {
            int add = 10 - lastDigitC;
            c += add;
        } else {
            c = c - lastDigitC;
        }

        return a + b + c;
    }


    /**
     * A sandwich is two pieces of bread with something in between. Return the string that is between the first and
     * last appearance of "bread" in the given string, or return the empty string ""
     * if there are not two pieces of bread.
     * <p>
     * getSandwich("breadjambread") → "jam"
     * getSandwich("xxbreadjambreadyy") → "jam"
     * getSandwich("xxbreadyy") → ""
     */
    public static String getSandwich(String str) {
        int startIndex = str.indexOf("bread");
        int endIndex = str.lastIndexOf("bread");

        if (startIndex == endIndex) {
            return "";
        } else {
            return str.substring(startIndex + 5, endIndex);
        }

        /*Matcher matcher = Pattern.compile("\\b(bread)\\b").matcher(str);
        if (matcher.find()) {
            int start = matcher.end();
            if (matcher.find()) {
                int end = matcher.start();
                return str.substring(start, end);
            }
        }
        return "";*/
    }


    /**
     * Given a map of food keys and topping values, modify and return the map as follows: if the key
     * "ice cream" is present, set its value to "cherry". In all cases, set the key "bread"
     * to have the value "butter".
     * <p>
     * <p>
     * topping({"ice cream": "peanuts"}) → {"bread": "butter", "ice cream": "cherry"}
     * topping({}) → {"bread": "butter"}
     * topping({"pancake": "syrup"}) → {"bread": "butter", "pancake": "syrup"}
     */
    public static Map<String, String> topping(Map<String, String> map) {
        if ((map.containsKey("ice cream")) && !Objects.equals(map.get("ice cream"), "cherry")) {
            map.put("ice cream", "cherry");
        }
        if ((!map.containsKey("bread")) || !Objects.equals(map.get("butter"), "bread")) {
            map.put("bread", "butter");
        }
        return map;
    }

    public static void main(String[] args) {
        List<Integer> nums1 = new ArrayList<>(List.of(1, 2, 3, 4, 100));
        System.out.println(centeredAverage(nums1)); // → 3

        List<Integer> nums2 = new ArrayList<>(List.of(1, 1, 5, 5, 10, 8, 7));
        System.out.println(centeredAverage(nums2)); // → 5

        List<Integer> nums3 = new ArrayList<>(List.of(-10, -4, -2, -4, -2, 0));
        System.out.println(centeredAverage(nums3)); // → -3

        /*System.out.println(roundSum(16, 17, 18));  // => 60
        System.out.println(roundSum(12, 13, 14)); // => 30
        System.out.println(roundSum(6, 4, 4)); // => 10*/

        /*System.out.println(getSandwich("breadjambread")); //→ "jam"
        System.out.println(getSandwich("xxbreadjambreadyy")); // → "jam"
        System.out.println(getSandwich("xxbreadyy")); // → ""*/

        /*Map<String, String> topping1 = new HashMap<>();
        topping1.put("ice cream", "peanuts");
        System.out.println(topping(topping1)); // → {"bread": "butter", "ice cream": "cherry"}

        Map<String, String> topping2 = new HashMap<>();
        System.out.println(topping(topping2)); // → {"bread": "butter"}

        Map<String, String> topping3 = new HashMap<>();
        topping3.put("pancake", "syrup");
        System.out.println(topping(topping3)); // → {"bread": "butter", "pancake": "syrup"}*/

    }
}
