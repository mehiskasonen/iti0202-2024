package ee.taltech.iti0202.exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Exam {

    /**
     * For each integer in the array:
     * Add the value of the integer directly to the left and
     * subtract the value of the integer to the right.
     * If no integers to the left, add the value of the last integer in the array
     * If no integers to the right, subtract the value of the first integer in the array
     * So {1, 2, 3, 4, 5, 6} yields {5, 4, 3, 2, 1, 2}.
     * - for the first element we calculate 1 + 6 - 2 = 5
     * - for the second element we calculate 2 + 5 (previously calculated) - 3 = 4
     * etc.
     * {10, 2} yields {10, 2}
     * {10, 2, 5} yields {13, 10, 2}
     * {10} yields {10}
     */
    public static int[] mathematics(int[] input) {
        int n = input.length;

        for (int i = 0; i < n; i++) {
            int left;
            if (i == 0) {
                left = input[n - 1];
            } else {
                left = input[i - 1];
            }

            int right;
            if (i == n - 1) {
                right = input[0];
            } else {
                right = input[i + 1];
            }
            input[i] = input[i] + left - right;

        }
        return input;
    }

    /**
     * Given a string, return the longest substring
     * that appears at both the beginning and
     * end of the string without overlapping.
     * For example, sameEnds("abXab") is "ab".

     * sameEnds("aa") => "a"
     * sameEnds("aaaa") => "aa"
     * sameEnds("aaa") => "a"
     * sameEnds("") => ""
     * sameEnds("abxab") => "ab"
     *
     * @param text Input text.
     * @return longest common substring from beginning and end.
     */
    public static String sameEnds(String text) {
        if (text.isEmpty()) {
            return "";
        }

        List<String> strings = new ArrayList<>();
        int middle = 0;
        if (text.length() % 2 == 0) {
            middle = (text.length() / 2) + 1;
            System.out.println(middle);
        }
        if (text.length() % 2 == 1) {
            middle = (text.length() / 2) + 1;
            System.out.println(middle);
        }
        boolean flag = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < middle - 1; i++) {
            //System.out.println(text.charAt(i));
            //System.out.println(text.charAt(middle - 2));
            if (text.length() < 5) {
                if (text.charAt(i) == text.charAt(middle - 1)) {
                    builder.append(text.charAt(i));
                    flag = false;
                } else {
                    strings.add(builder.toString());
                    builder.delete(0, builder.length());
                    flag = true;
                }
            } else {
                if (i < middle - 1) {
                    if (text.charAt(i) == text.charAt(middle + i)) {
                        builder.append(text.charAt(i));
                        flag = false;
                    } else {
                        if (!builder.isEmpty()) {
                            strings.add(builder.toString());
                            builder.delete(0, builder.length());
                            flag = true;
                        }
                    }
                }
            }
        }
        if (!flag) {
            strings.add(builder.toString());
        }

        return strings.stream().max(Comparator.comparingInt(String::length)).get();

    }
}
