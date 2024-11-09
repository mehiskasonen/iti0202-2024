package ee.taltech.iti0202.tk;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.MAX_VALUE;

public class Exam {

    /**
     * Javadoc here...
     * Given a list of positive integers, l and a positive integer, n,
     * find from the list l, the integer that is closest to the
     * integer n, but not exactly the same, as n.
     * If there are multiple integers in the list that are closest to n, then return
     * the one with the greatest index (the right most of the suitable integers).
     * If no suitable integer is within the list (if the list is empty, or the only
     * integer in the list == n), return -1
     * closestInteger(new int[] {4, 2, 7}, 3) => 2
     * closestInteger(new int[] {4, 2, 7}, 2) => 4
     * closestInteger(new int[] {4}, 4) => -1
     */
    public static int closestInteger(int[] l, int n) {
        int closestNr = MAX_VALUE;
        int closestIndex = -1;

        if ((l.length == 1 && l[0] == n) || l.length == 0) {
            return -1;
        }

        for (int i = 0; i < l.length; i++) {
            int distance = Math.abs(n - l[i]);
            if (distance < closestNr) {
                if (l[i] != n) {
                    closestNr = distance;
                    closestIndex = i;
                }
            } else if (distance == closestNr && l[i] != n) {
                closestIndex = i;
            }
        }
        return l[closestIndex];
    }

    /**
     * Given 3 int values, a b c, return their sum. However, if one of the values
     * is the same as another of the values,
     * it does not count towards the sum.
     * <p>
     * loneSum(1, 2, 3) → 6
     * loneSum(3, 2, 3) → 2
     * loneSum(3, 3, 3) → 0
     */
    public static int loneSum(int a, int b, int c) {
        int finalSum = 0;
        if (a == b && b == c) {
            return finalSum;
        }
        if (a == b) {
            finalSum += c;
            return finalSum;
        }
        if (a == c) {
            finalSum += b;
            return finalSum;
        }
        if (b == c) {
            finalSum += a;
        } else {
            finalSum += a + b + c;
        }
        return finalSum;
    }

    /**
     * Given a string, compute a new string by moving the first char to come after the next two chars,
     * so "abc" yields "bca".
     * Repeat this process for each subsequent group of 3 chars, so "abcdef" yields "bcaefd".
     * Ignore any group of fewer than 3 chars at the end.
     * oneTwo("abc") => "bca"
     * oneTwo("tca") => "cat"
     * oneTwo("tcagdo") => "catdog"
     * oneTwo("abcd") => "bca"
     * oneTwo("a") => ""
     */
    public static String oneTwo(String str) {
        StringBuilder result = new StringBuilder();
        if (str.length() < 3) {
            return result.toString();
        }

        for (int i = 0; i < str.length() - 2; i += 3) {
            String group = str.substring(i, i + 3);
            String modifiedGroup = group.substring(1) + group.charAt(0);
            result.append(modifiedGroup);
        }
        return result.toString();
    }

    /**
     * Modify and return the given map as follows:
     * if exactly one of the keys "a" or "b" exists in the map (but not both),
     * set the other to have that same value in the map.
     * mapAXorB({"a": "aaa", "c": "cake"}) => {"a": "aaa", "b": "aaa", "c": "cake"}
     * mapAXorB({"b": "bbb", "c": "cake"}) => {"a": "bbb", "b": "bbb", "c": "cake"}
     * mapAXorB({"a": "aaa", "b": "bbb", "c": "cake"}) => {"a": "aaa", "b": "bbb", "c": "cake"}
     */
    public static Map<String, String> mapAXorB(Map<String, String> map) {
        Map<String, String> result = new HashMap<>(map);
        if (map.containsKey("a") && !map.containsKey("b")) {
            result.put("b", map.get("a"));
        }
        if (map.containsKey("b") && !map.containsKey("a")) {
            result.put("a", map.get("b"));
        }
        return result;
    }

    public static void main(String[] args) {
        /*Shop shop = new Shop();
        Product p1 = new Product("Bread", 3);
        Product p2 = new Product("Milk", 4);
        Product p3 = new Product("Milk", 4);
        Product p4 = new Product("Milk", 7);
        Product p5 = new Product("Cheat", -1);
        Product p6 = new Product("", 2);
        Product p7 = new Product(11);
        Product p8 = new Product(0);
        System.out.println(shop.addProduct(p1));  // true
        System.out.println(shop.addProduct(p2));  // true
        System.out.println(shop.addProduct(p3));  // false
        System.out.println(shop.addProduct(p4));  // true
        System.out.println(shop.addProduct(p5));  // false
        System.out.println(shop.addProduct(p6));  // true
        System.out.println(shop.addProduct(p7));  // true
        System.out.println(shop.addProduct(p8));  // true


        System.out.println(shop.sellProduct("Pizza", 10));  // Optional.empty
        System.out.println(shop.sellProduct("Milk", 10).get());  // Milk (7)
        System.out.println(shop.sellProduct("Milk", 10).get());  // Milk (4)
        System.out.println(shop.sellProduct("Milk", 10));  // Optional.empty
        System.out.println(shop.sellProduct("", 20).get());  //  (2)*/

        /*System.out.println(closestInteger(new int[] {4, 2, 7}, 3)); // => 2
        System.out.println(closestInteger(new int[] {4, 2, 7}, 2)); // => 4
        System.out.println(closestInteger(new int[] {4}, 4)); // => -1

        System.out.println(loneSum(1, 2, 3));  // → 6;
        System.out.println(loneSum(3, 2, 3));  // → 2
        System.out.println(loneSum(3, 3, 3));  //→ 0*/

        System.out.println(oneTwo("abc")); // => "bca");
        System.out.println(oneTwo("tca")); // => "cat");
        System.out.println(oneTwo("tcagdo")); // => "catdog");
        System.out.println(oneTwo("abcd")); // => "bca");
        System.out.println(oneTwo("a")); // => "");

        /*Map<String, String> result = mapAXorB(Map.of("a", "aaa", "c", "cake"));
        System.out.println(result); // {"a": "aaa", "b": "aaa", "c": "cake"}

        Map<String, String> result2 = mapAXorB(Map.of("b", "bbb", "c", "cake"));
        System.out.println(result2); // => {"a": "bbb", "b": "bbb", "c": "cake"}

        Map<String, String> result3 = mapAXorB(Map.of("a", "aaa", "b", "bbb", "c", "cake"));
        System.out.println(result3);  // => {"a": "aaa", "b": "bbb", "c": "cake*/
    }
}
