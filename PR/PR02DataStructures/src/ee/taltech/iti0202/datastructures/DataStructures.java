package ee.taltech.iti0202.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;

public class DataStructures {

    private final Map<String, Integer> students = new HashMap<>();

    /**
     * The given String is a sentence with some words.
     * There is only a single whitespace between every word and no punctuation marks.
     * There are no capital letters in input string.
     * <p>
     * Return the longest word from the input sentence.
     * <p>
     * If there is more than one word with the same length, return the word which comes first alphabetically.
     * <p>
     * Hints:
     * You can split words into an array using "str.split()"
     * Sorting the list with the longest words can help you find the word which comes first alphabetically.
     *
     * @param sentence input String to find the longest words
     * @return the longest String from input
     */
    public static String findLongestWord(String sentence) {
        String[] word = sentence.split(" ");
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(word));
        List<String> sortedWords = wordList.stream()
                .sorted(
                        Comparator.comparing(String::length).reversed()
                                .thenComparing(String::compareTo))
                .toList();

        return sortedWords.get(0);
    }

    /**
     * Classic count the words exercise.
     * <p>
     * From input count all the words and collect results to map.
     *
     * @param sentence array of strings, can't be null.
     * @return map containing all word to count mappings.
     */
    public static Map<String, Integer> wordCount(String[] sentence) {
        Map<String, Integer> wordDict = new HashMap<>();
        for (String word : sentence) {
            if (!wordDict.containsKey(word)) {
                wordDict.put(word, 1);
            } else {
                int oldValue = wordDict.get(word);
                int newValue = oldValue + 1;
                wordDict.replace(word, newValue);
            }
        }
        return wordDict;
    }

    /**
     * Loop over the given list of strings to build a resulting list of strings like this:
     * when a string appears the 2nd, 4th, 6th, etc. time in the list, append the string to the result.
     * <p>
     * Return the empty list if no string appears a 2nd time.
     * <p>
     * Use map to count times that the string has appeared.
     *
     * @param words input list to filter
     * @return list of strings matching criteria
     */
    public static List<String> onlyEvenWords(List<String> words) {
        Map<String, Integer> wordDictionary = new HashMap<>();
        ArrayList<String> wordList = new ArrayList<>();
        if (words == null) {
            return wordList;
        }
        for (String word : words) {
            wordDictionary.put(word, wordDictionary.getOrDefault(word, 0) + 1);
            if (wordDictionary.get(word) % 2 == 0) {
                wordList.add(word);
            }
        }
        return wordList;
    }

    /**
     * Method to save student and student's grade (you should use a Map here).
     * Only add student if his/hers grade is in the range of 0-5.
     *
     * @param studentInfo String with a pattern (name:grade)
     */
    public void addStudent(String studentInfo) {
        String[] input = studentInfo.split(":");
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(input));

        if (!students.containsKey(wordList.get(0))) {
            if (Integer.parseInt(wordList.get(1)) >= 0 && Integer.parseInt(wordList.get(1)) <= 5) {
                students.put(wordList.get(0), Integer.parseInt(wordList.get(1)));
            } else {
                students.put(wordList.get(0), null);
            }
        }
    }

    /**
     * Method to get student's grade.
     * Return the student's grade by his/hers name.
     * You can assume that the student is already added (previous function with student's info already called).
     *
     * @param name String students name
     * @return int student's grade.
     */
    public int getStudentGrade(String name) {
        if (students.containsKey(name)) {
            if (students.get(name) == null) {
                return -1;
            } else {
                return students.get(name);
            }
        } else {
            return 0;
        }
    }

    /**
     * Main.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        System.out.println(findLongestWord("nimi on salastatud"));  // "salastatud"
        System.out.println(findLongestWord("aaa bbbbb"));  // "bbbbb"
        System.out.println(findLongestWord("hello ahllo")); // "ahllo"

        System.out.println(wordCount(new String[]{})); // empty
        System.out.println(wordCount(new String[]{"eggs", "SPAM", "eggs", "bacon", "SPAM", "bacon", "SPAM"}));
        // {bacon=2, eggs=2, SPAM=3}

        System.out.println(onlyEvenWords(Arrays.asList("foo", "bar", "baz", "baz", "bar", "foo")));
        // [baz, bar, foo] any order
        System.out.println(onlyEvenWords(Arrays.asList("a", "b", "b", "a"))); // [b, a] any order
        System.out.println(onlyEvenWords(Arrays.asList("eggs", "bacon", "SPAM", "ham", "SPAM", "SPAM"))); // [SPAM]

        DataStructures dataStructures = new DataStructures();

        dataStructures.addStudent("Ago:5");
        dataStructures.addStudent("Martin:0");
        dataStructures.addStudent("Margo:3");
        dataStructures.addStudent("Cheater:6");

        System.out.println(dataStructures.getStudentGrade("Ago")); // 5
        System.out.println(dataStructures.getStudentGrade("Martin")); // 0
        System.out.println(dataStructures.getStudentGrade("Margo")); // 3
        System.out.println(dataStructures.getStudentGrade("Cheater")); // -1
    }
}
