package ee.taltech.iti0202.exam;
import java.time.Duration;
import java.time.LocalTime;

public class Exam {

    /**
     * Find the longest distance between two equal symbols.
     * The same symbol can contain in this distance.
     * If there are no equal symbols, return -1.
     * longestDistanceBetweenEqualSymbols("abcda") => 3
     * longestDistanceBetweenEqualSymbols("aaaa") => 2
     * longestDistanceBetweenEqualSymbols("aia") => 1
     * longestDistanceBetweenEqualSymbols("aiu") => -1
     * longestDistanceBetweenEqualSymbols("") => -1
     * longestDistanceBetweenEqualSymbols("aab") => 0
     * longestDistanceBetweenEqualSymbols("abcdabbg") => 4
     *
     * @param s input string
     * @return longest distance
     */
    public static int longestDistanceBetweenEqualSymbols(String s) {
        if (s.isEmpty()) {
            return -1;
        }

        int longest = -1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(j) == s.charAt(i)) {
                    int distance = j - i - 1;
                    if (distance > longest) {
                        longest = distance;
                    }
                }
            }
        }
        return longest;
    }

    /**
     * Write a  method that finds correct difference between two timestamps. Timestamps are given in format HH:MM where
     * HH is two-digit hour marker and MM is two-digit
     * minutes marker. Result must be also in format HH:MM. Difference means basically "time2" minus "time1".
     * timeDiff("10:00", "10:00") => "00:00"
     * timeDiff("10:00", "11:01") => "01:01"
     * timeDiff("10:00", "09:01") => "23:01"
     * timeDiff("10:00", "08:59") => "22:59"
     * timeDiff("10:01", "10:00") => "23:59"
     *
     * @param time1 time as HH:MM
     * @param time2 time as HH:MM
     * @return time difference as HH:MM
     */
    public static String timeDiff(String time1, String time2) {
        LocalTime localTime1 = LocalTime.parse(time1);
        LocalTime localTime2 = LocalTime.parse(time2);
        Duration duration = Duration.between(localTime1, localTime2);

        long totalMinutes = duration.toMinutes();
        System.out.println(totalMinutes);
        if (totalMinutes < 0) {
            totalMinutes += 24 * 60;

        }
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }


    /**
     * Given a string, return the sum of the numbers appearing in the string, ignoring all other characters.
     * A number is a series of 1 or more digit chars in a row. (Note: Character.isDigit(char)
     * tests if a char is one of the chars '0', '1', .. '9'. Integer.parseInt(string)
     * converts a string to an int.)
     * sumNumbers("abc123xyz") → 123
     * sumNumbers("aa11b33") → 44
     * sumNumbers("7 11") → 18
     * @param str to convert.
     * @return sum of numbers appearing in the string.
     */
    public int sumNumbers(String str) {
        StringBuilder builder = new StringBuilder();
        int total = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c) && (i != str.length() && Character.isDigit(c))) {
                builder.append(c);
            } else {
                if (Character.isDigit(c)) {
                    builder.append(c);
                    total += Integer.parseInt(builder.toString());
                }
            }
        }
        return total;
    }


}
