package ee.taltech.iti0202.files;

import ee.taltech.iti0202.files.input.InputFilesBufferReader;
import ee.taltech.iti0202.files.input.InputFilesScanner;
import ee.taltech.iti0202.files.morse.MorseTranslator;
import ee.taltech.iti0202.files.output.OutputFilesWriter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MorseFilesController {

    public static void main(String[] args) {
        InputFilesScanner scanner = new InputFilesScanner();
        List<String> lines = scanner.readTextFromFile("EX/EX06Files/src/ee/taltech/iti0202/files/morse/morse");
        lines.forEach(System.out::println); //lines in morse.txt which contains Morse codes
        //System.out.println("END OF FOREACH");
        //System.out.println(lines);
        System.out.println("END OF SCANNER");

        InputFilesBufferReader bufferReader = new InputFilesBufferReader();
        List<String> lines2 = bufferReader.readTextFromFile("EX/EX06Files/src/ee/taltech/iti0202/files/morse/morse");
        lines2.forEach(System.out::println); //lines in morse.txt which contains Morse codes
        System.out.println("END OF BUFFER-READER");
        //System.out.println(lines2);

        MorseTranslator translator = new MorseTranslator();
        Map<String, String> codes = translator.addMorseCodes(lines);
        codes.forEach((key, value) -> System.out.println(key + " " + value)); //key and value
        System.out.println("END OF MORSE-TRANSLATOR");

        List<String> input = scanner.readTextFromFile("EX/EX06Files/src/ee/taltech/iti0202/files/morse/input");
        //input.forEach(System.out::println); //your input lines
        System.out.println(input);
        System.out.println("INPUT LINES READ USING SCANNER");

        List<String> input2 = bufferReader.readTextFromFile("EX/EX06Files/src/ee/taltech/iti0202/files/morse/input");
        System.out.println(input2);
        //input2.forEach(System.out::println);
        System.out.println("INPUT LINES READ USING BUFFER-READER");

        List<String> morseLines = translator.translateLinesToMorse(input);
        //morseLines.forEach(System.out::println); //your input lines in Morse

        List<String> normalLines = translator.translateLinesFromMorse(morseLines);
        System.out.println(normalLines);
        //normalLines.forEach(System.out::println); //your input lines in regular text
        //System.out.println(normalLines);
        System.out.println("your input lines in regular text");


        OutputFilesWriter writer = new OutputFilesWriter();
        //System.out.println(writer.writeLinesToFile(morseLines, "output.txt")); //true
        //System.out.println("OUTPUT FROM LINES TO MORSE");

        System.out.println(normalLines);
        System.out.println();
        System.out.println(writer.writeLinesToFile(normalLines,
                "EX/EX06Files/src/ee/taltech/iti0202/files/morse/output")); //true
        //This should also create a new file/ write in an existing file

        System.out.println("-------------------------------------------------------------------");

        List<String> readMorse = scanner.readTextFromFile(
                "EX/EX06Files/src/ee/taltech/iti0202/files/morse/morsetotext");
        //input.forEach(System.out::println); //your input lines
        //System.out.println("ReadMorse");

        List<String> normallines2 = translator.translateLinesFromMorse(readMorse);
        System.out.println(normallines2);

        System.out.println("--------------------------------------------------------------------");

        String inputSingleLine = "lo ip,";
        String morseLines2 = translator.translateLineToMorse(inputSingleLine);
        System.out.println(morseLines2);
        System.out.println("morselines when read from single file");
        List<String> normalLines2 = Collections.singletonList(translator.translateLineFromMorse(morseLines2));
        System.out.println(normalLines2);
        System.out.println("Should be [lo ip,]");

        List<String> inputMultipleLine2 = Arrays.asList(
                "lorem ipsum dolor sit amet, consectetur adipiscing elit,",
                " ",
                ", sed do"
        );

        // Translate the lines to Morse
        List<String> morseLines3 = translator.translateLinesToMorse(inputMultipleLine2);

        // Print the Morse lines
        System.out.println(morseLines3);

        //List<String> inputMultipleLine2 = List.of("lorem ipsum dolor sit amet,
        //consectetur adipiscing elit", ", ", "sed do");
        //String morseLines3 = translator.translateLinesToMorse(inputMultipleLine2);
        System.out.println(inputMultipleLine2);
        System.out.println("morselines when read from single file");
        List<String> normalLines3 = translator.translateLinesFromMorse(morseLines3);
        System.out.println(normalLines3);

    }
}
