import java.util.Arrays;

import static ee.taltech.iti0202.exam.Exam.sameEnds;
import static ee.taltech.iti0202.exam.Exam.mathematics;


public class Main {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3, 4, 5, 6};

        //System.out.println(Arrays.toString(mathematics(array1)));

        System.out.println(sameEnds("aa")); // => "a"
        System.out.println(sameEnds("aaaa")); // => "aa"
        System.out.println(sameEnds("aaa")); // => "a"
        System.out.println(sameEnds("")); // => ""
        System.out.println(sameEnds("abxab")); // => "ab"
        System.out.println(sameEnds("abcdghijkXabcfghijk")); // "ghijk"
    }
}