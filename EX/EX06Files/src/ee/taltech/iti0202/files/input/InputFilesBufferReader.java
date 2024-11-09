package ee.taltech.iti0202.files.input;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputFilesBufferReader implements InputFilesReader {

    @Override
    public List<String> readTextFromFile(String filename) {
        List<String> lines = new ArrayList<>();

        Path path = Paths.get(filename);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    lines.add("");
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new FileReaderException("No such file", e);
        }
        return lines;
    }
}
