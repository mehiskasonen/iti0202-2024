package ee.taltech.iti0202.files.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InputFilesLines implements InputFilesReader {

    @Override
    public List<String> readTextFromFile(String filename) {
        List<String> resultLinesStream = new ArrayList<>();

        try {
            Files.lines(Path.of(filename))
                    .map(s -> s.trim())
                    .forEach(s -> {
                        if (!s.isEmpty() && !s.equals("\n")) {
                            resultLinesStream.add(s);
                        } else {
                            resultLinesStream.add("");
                        }
                    });
        } catch (IOException e) {
            System.out.println("No such file" + e.getMessage());
            throw new FileReaderException("No such file", e);
        }
        return resultLinesStream;
    }
}
