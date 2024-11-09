package ee.taltech.iti0202.files.output;

import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OutputFilesWriter {

    /**
     * Write lines to designated file.
     * @param lines String.
     * @param filename to write to.
     * @return
     */
    public boolean writeLinesToFile(List<String> lines, String filename) {
        if (filename == null) {
            return false;
        }
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
            } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
