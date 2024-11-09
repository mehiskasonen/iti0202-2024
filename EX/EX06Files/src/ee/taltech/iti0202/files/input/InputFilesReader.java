package ee.taltech.iti0202.files.input;

import java.util.List;

public interface InputFilesReader {

    /**
     * Interface for reading text from file.
     * @param filename of input file to read from.
     * @throws FileReaderException
     */
    List<String> readTextFromFile(String filename) throws FileReaderException;
}
