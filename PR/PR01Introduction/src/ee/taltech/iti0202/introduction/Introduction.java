package ee.taltech.iti0202.introduction;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Introduction {
    public static final int VALUE_SIX = 6;
    public static final int VALUE_SEVEN = 7;
    public static final int VALUE_NINE = 9;
    public static final int VALUE_NEGATIVE_SEVEN = -7;
    public static final int VALUE_TWENTY_FOUR = 24;

    /**
     * Method gets two numbers as parameters.
     * Method must answer if the given pair gives bad, normal or good outcome.
     * Outcome is "bad" if any of values is less than 5.
     * Outcome is "good" if one value equals doubled second value.
     * Outcome is "ok" if both values equal at least 5.
     * The priority is as follows: "bad", "good", "ok" (if several cases apply, take the higher / earlier).
     *
     * @param valueOne int
     * @param valueTwo int
     * @return String based on the values of valueOne and valueTwo
     */
    public String howIsOutcome(int valueOne, int valueTwo) {
        if (valueOne < 5 || valueTwo < 5) {
            return "bad";
        }
        if (valueOne == 2 * valueTwo || valueTwo == 2 * valueOne) {
            return "good";
        } else {
            return "ok";
        }
    }


    /**
     * Method gets a list of numbers.
     * Return a list containing only even numbers of the given list.
     * If the given list does not contain any even numbers, return an empty list.
     *
     * @param numbers given list that contains numbers.
     * @return list of even numbers.
     */
    public List<Integer> findEvenNumbersList(List<Integer> numbers) {
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        if (numbers.isEmpty()) {
            return evenNumbers;
        }
        for (Integer nr : numbers) {
            if (nr % 2 == 0) {
                evenNumbers.add(nr);
            }
        }
        return evenNumbers;
    }

    /**
     * Method gets an array of numbers.
     * Return an array containing only even numbers of the given array.
     * If the given array does not contain any even numbers, return an empty array.
     * You must not use the previous function in this function!
     *
     * @param numbers given array that contains numbers.
     * @return array of even numbers.
     */
    public int[] findEvenNumbersArray(int[] numbers) {
        int count = 0;
        for (Integer nr : numbers) {
            if (nr % 2 == 0) {
                count++;
            }
        }
        int[] result = new int[count];
        int resultIndex = 0;
        for (int number : numbers) {
            if (number % 2 == 0) {
                result[resultIndex] = number;
                resultIndex++;
            }
        }
        return result;
    }

    /**
     * Method gets two Strings as parameters.
     * If two words have the same length, just put them together. If the length is
     * different, remove so many letters from the beginning of the longer word
     * that the two words are the same length, and then put them together.
     * If the first word was longer, return the answer in lower case. If the second word was longer,
     * return the answer in capital letters.
     * If both words are empty or with spaces, return FALSE.
     *
     * @param first String
     * @param second String
     * @return String based on the values of first and second
     */
    public String findTheString(String first, String second) {
        if (first.isEmpty() && second.isEmpty()) {
            return "FALSE";
        }
        if (first.isEmpty() && second.contains(" ") || first.contains(" ") && second.isEmpty()) {
            return "FALSE";
        }
        if (first.contains(" ") && second.contains(" ")) {
            return "FALSE";
        }
        if (first.length() == second.length()) {
            return first + second;
        }
        if (second.length() > first.length()) {
            int difference = second.length() - first.length();
            return first.toUpperCase() + second.substring(difference).toUpperCase();
        } else {
            int difference = first.length() - second.length();
            return first.substring(difference).toLowerCase() + second.toLowerCase();
        }
        }

    /**
     * Method gets one String as a parameter.
     * In a given string, count the number of characters that appear exactly three times in a row.
     *
     * @param word String
     * @return The number of triples
     */
    public int countTripleChars(String word) {
        int count = 0;
        for (int i = 0; i < word.length() - 2; i++) {
            if (word.charAt(i) == word.charAt(i + 1) && word.charAt(i) == word.charAt(i + 2)) {
                if (i == 0 || word.charAt(i - 1) != word.charAt(i)) {
                    if (i == word.length() - 3) {
                        count++;
                    } else if (word.charAt(i) != word.charAt(i + 3)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Run tests.
     * @param args Arguments from command line.
     */
    public static void main(String[] args) {

        Introduction introduction = new Introduction();
        System.out.println(introduction.howIsOutcome(3, VALUE_SIX)); // "bad"

        List<Integer> nums = new ArrayList<>(Arrays.asList(4, VALUE_SEVEN, 5, 2, 1, 2, -2, 0));
        System.out.println(introduction.findEvenNumbersList(nums)); // [4, 2, 2, -2, 0]

        int[] array = {VALUE_NINE, 0, VALUE_TWENTY_FOUR, VALUE_NEGATIVE_SEVEN, 3};
        System.out.println(Arrays.toString(introduction.findEvenNumbersArray(array))); // [0, 24, -6]

        String result = introduction.findTheString("Good", "afternoon");
        System.out.println(result);  // GOODNOON
        result = introduction.findTheString("Hello", "lo");
        System.out.println(result);  // lolo
        System.out.println(introduction.findTheString("", ""));  // FALSE
        System.out.println(introduction.findTheString("", "   "));  // FALSE
        System.out.println(introduction.findTheString(" ", " "));  // FALSE
        System.out.println(introduction.findTheString("  ", "a"));  //  a  (with space in front)

        System.out.println(introduction.countTripleChars("aaabbbabbb"));  // 3
        System.out.println(introduction.countTripleChars("aaa"));  // 1
        System.out.println(introduction.countTripleChars("aaaa"));  // 0
        System.out.println(introduction.countTripleChars("aaaabbbabbbcCc"));  // 2
    }
}
