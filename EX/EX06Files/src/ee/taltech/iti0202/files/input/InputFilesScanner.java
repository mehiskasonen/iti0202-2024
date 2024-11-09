package ee.taltech.iti0202.files.input;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class InputFilesScanner implements InputFilesReader {

    @Override
    public List<String> readTextFromFile(String filename) {
        List<String> resultScanner = new ArrayList<>();
        Path path = Paths.get(filename);
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    line = "";
                    resultScanner.add(line);
                } else {
                    resultScanner.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
            throw new FileReaderException("No such file", e);
        }
        return resultScanner;
    }
}
