package ee.taltech.iti0202.files.input;

public class FileReaderException extends RuntimeException {

    /**
     * Custom user-exception.
     * @param message for exception.
     * @param reason for exception.
     */
    public FileReaderException(String message, Throwable reason) {
        super(message, reason);
    }
}
