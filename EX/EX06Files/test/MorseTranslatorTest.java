import ee.taltech.iti0202.files.input.InputFilesScanner;
import ee.taltech.iti0202.files.morse.MorseTranslator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MorseTranslatorTest {

    private MorseTranslator morseTranslator = new MorseTranslator();

    @Test
    public void testTranslateLinesFromMorseVariousScenarios() {
        InputFilesScanner scanner = new InputFilesScanner();
        List<String> lines = scanner.readTextFromFile("morse.txt");

        // Test lines with text
        assertEquals("lorem ipsum dolor sit amet", morseTranslator.translateLineFromMorse(".-.. --- .-. . --  .. .--. ... ..- --  -.. --- .-.. --- .-.  ... .. -  .- -- . - --..--"));

        // Test lines with only a comma
        assertEquals(", ", morseTranslator.translateLineToMorse(", "));

        // Test lines with only newline characters
        assertEquals(", , ", morseTranslator.translateLineToMorse("\n\n"));

        // Test lines with text followed by a comma
        assertEquals("lorem ipsum dolor sit amet, ", morseTranslator.translateLineFromMorse(".-.. --- .-. . --  .. .--. ... ..- --  -.. --- .-.. --- .-.  ... .. -  .- -- . - --..--  ,"));

        // Test lines with text followed by newline characters
        assertEquals("lorem ipsum dolor sit amet, ", morseTranslator.translateLineFromMorse(".-.. --- .-. . --  .. .--. ... ..- --  -.. --- .-.. --- .-.  ... .. -  .- -- . - --..--  \n\n"));

        // Test lines with only comma between newline characters
        assertEquals(", ", morseTranslator.containsT("\n, \n"));

        // Test lines with text, comma, and newline characters
        assertEquals("lorem ipsum dolor sit amet, ", morseTranslator.containsT(".-.. --- .-. . --  .. .--. ... ..- --  -.. --- .-.. --- .-.  ... .. -  .- -- . - --..--  ,\n\n"));
    }
}
