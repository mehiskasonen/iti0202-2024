package ee.taltech.iti0202.files.morse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MorseTranslator {

    Map<String, String> morseMap = new HashMap<>();

    /**
     * Map input lines to a morse map, where character is key and value is morse code.
     * @param lines of character and equivalent morse code
     * @return mapped characters to morse.
     */
    public Map<String, String> addMorseCodes(List<String> lines) {
        for (String line : lines) {
            String[] parts = line.split(" ");
            String letter = parts[0].toLowerCase();
            String code = parts[1];
            morseMap.put(letter, code);
        }
        return morseMap;
    }

    /**
     * Translate regular string lines to morse lines.
     * @param lines of regular text.
     * @return Morse lines.
     */
    public List<String> translateLinesToMorse(List<String> lines) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            StringBuilder morseLine = new StringBuilder();
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                for (int j = 0; j < word.length(); j++) {
                    String c = String.valueOf(word.charAt(j)).toLowerCase();
                    String morseChar = morseMap.getOrDefault(c, "");
                    if (!morseChar.isEmpty()) {
                        morseLine.append(morseChar);
                        if (j < word.length() - 1) {
                            morseLine.append(" ");
                        }
                    }
                }
                if (i < words.length - 1) {
                    morseLine.append("\t");
                }
            }
            result.add(morseLine.toString().trim());
        }
        return result;
    }


    /**
     * Helper method for translating lines from morse.
     * @param line of regular text.
     * @return translate String.
     */
    public String containsT(String line) {
        String result = "";
        String buffer = "";
        String[] splitt = line.split("\t");
        System.out.println(splitt[0]);
        if (line.isEmpty()) {
            result += " ";
        }

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (i > 1) {
                char cPrevious = line.charAt(i - 1);
                if (c == ' ' && cPrevious == ' ') {
                    result += ' ';
                    continue;
                }
            }
            if (c == ' ') {
                result += toChar(String.valueOf(buffer));
                buffer = "";
            }
            if (c == '\t') {
                result += toChar(String.valueOf(buffer));
                result += " ";
                buffer = "";
            }
            if (c == '\n') {
                result += toChar(String.valueOf(buffer));
                result += ", ";
                buffer = "";
            }
            if (c != '\t' && c != 't' && c != ' ' && c != '\n') {
                buffer += c;
            }
            if (i == line.length() - 1) {
                result += toChar(String.valueOf(buffer));
                buffer = "";
            }
            if (c == 't') {
                result += "  ";
                continue;
            }
            if (c == '\\') {
                result += toChar(String.valueOf(buffer));
                buffer = "";
            }
        }
        return result;
    }

    /**
     * Translate morse text to regular text.
     * @param lines of morse.
     * @return regular lines.
     */
    public List<String> translateLinesFromMorse(List<String> lines) {
        List<String> result = new ArrayList<>();
        if (lines.isEmpty()) {
            return result;
        }
        String buffer = "";
        for (String line : lines) {
            if (line.isEmpty()) {
                buffer += ' ';
            }
            String translatedLines = containsT(line);
            buffer += translatedLines;
        }
        result.add(buffer);
        return result;
    }

    /**
     * Map morse code to regular character.
     * @param code morse code
     * @return regular character.
     */
    public char toChar(String code) {
        String key = "";
        for (Map.Entry<String, String> entry : morseMap.entrySet()) {
            if (code.equals(entry.getValue())) {
                key = entry.getKey();
                break;
            }
        }
        return key.charAt(0);
    }

    /**
     * Translate line to morse.
     * @param line regular string line.
     * @return sting.
     */
    public String translateLineToMorse(String line) {
        String result = "";
        StringBuilder spacingBuilder = new StringBuilder();
        try {
            String[] splitt = line.split(" ");
            for (int j = 0; j < splitt.length; j++) {
                String word = splitt[j];
                StringBuilder wordBuilder = new StringBuilder();
                for (int i = 0; i < word.length(); i++) {
                    String c = String.valueOf(word.charAt(i)).toLowerCase();
                    String morseChar = morseMap.get(c);
                    if (i == word.length() - 1) {
                        wordBuilder.append(morseChar);
                    } else {
                        wordBuilder.append(morseChar);
                        wordBuilder.append(" ");
                    }
                }
                spacingBuilder.append(wordBuilder);
                if (j != splitt.length - 1) {
                    spacingBuilder.append("\t");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result += (String.valueOf(spacingBuilder));
        return result;
    }

    /**
     * Translate single line from morse to regular text.
     * @param line morse string.
     * @return string.
     */
    public String translateLineFromMorse(String line) {
        String result = containsT(line);
        return result;
    }
}
