package ee.taltech.iti0202.texteditor.textformatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TitleCaseFormatter implements TextFormatter {

    /**
     * Strategy for formatting text.
     * First and last word must always start with uppercase letter.
     * words 'a', 'an', 'of', 'the', 'and', 'or' must begin with lowercase.
     * All other words begin with uppercase letter.
     * newline must be added to the end of the word.
     *
     * @param text to be formatted.
     * @return formatted string.
     */
    @Override
    public String format(String text) {
        if (Objects.equals(text, "")) {
            return "";
        }
        List<String> titleFormatWords = new ArrayList<>();
        String[] words = text.split("\\s+");
        int lastWordIndex = words.length;
        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                String formattedWord = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
                titleFormatWords.add(formattedWord);
                continue;
            }
            if (i == lastWordIndex - 1) {
                String formattedWord = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
                titleFormatWords.add(formattedWord + "\n");
            }
            else if (!isPrefix(words[i])) {
                String formattedWord = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
                titleFormatWords.add(formattedWord);
            } else {
                titleFormatWords.add(words[i]);
            }
        }
        return String.join(" ", titleFormatWords);
    }

    public boolean isPrefix(String word) {
        List<String> prefixes = List.of("a", "an", "of", "the", "and", "or");
        return prefixes.contains(word);
    }
}
