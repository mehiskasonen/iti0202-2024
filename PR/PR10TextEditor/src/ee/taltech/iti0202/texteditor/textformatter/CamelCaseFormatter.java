package ee.taltech.iti0202.texteditor.textformatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCaseFormatter implements TextFormatter {

    /**
     * Strategy to be implemented.
     * Only letters and numbers allowed. All spaces and punctuation marks must be removed, unless:
     *      punctuation mark is the last character of the string.
     *      punctuation mark is last but one of string and last is a newline
     * First letter is lowercase, rest uppercase. Ex. Hello, world!\n => helloWorld!\n
     *
     * @param text to format.
     * @return formatted string.
     */
    @Override
    public String format(String text) {
        List<String> spacesAndPunctuationsRemoved = removeSpacesAndPunctuations(text);
        return formatToCamelCase(spacesAndPunctuationsRemoved);
    }

    /**
     * @param text to remove spaces and punctuations from.
     * @return list of formatted strings
     */
    public List<String> removeSpacesAndPunctuations(String text) {
        List<String> explodedText = new ArrayList<>();
        if (Objects.equals(text, "")) {
            return explodedText;
        }
        String[] words = text.split("\\s+|,|'");
        Pattern punctuationPattern = Pattern.compile("\\p{Punct}");
        boolean textEndsWithNewline = text.endsWith("\n");

        for (String word : words) {
            Matcher matcher = punctuationPattern.matcher(word);
            if (matcher.find()) {
                if (matcher.end() == word.length() && textEndsWithNewline) {
                    explodedText.add(word + "\n");
                } else if (matcher.end() == word.length()) {
                    explodedText.add(word);
                } else {
                    explodedText.add(word.replaceAll("\\p{Punct}", ""));
                }
            } else {
                explodedText.add(word);
            }
        }
        return explodedText;
    }


    /**
     * @param explodedText taken from removeSpacesAndPunctuations()
     * @return string formatted to CamelCase.
     */
    public String formatToCamelCase(List<String> explodedText) {
        StringBuilder formattedText = new StringBuilder();
        for (int i = 0; i < explodedText.size(); i++) {
            String word = explodedText.get(i);
            if (!word.isEmpty()) {
                if (i == 0) {
                    formattedText.append(Character.toLowerCase(word.charAt(0)));
                } else {
                    formattedText.append(Character.toUpperCase(word.charAt(0)));
                }
                formattedText.append(word.substring(1).toLowerCase());
            }
        }
/*        if (!formattedText.toString().endsWith("\n")) {
            formattedText.append("\n");
        }*/
        return formattedText.toString();
    }
}
