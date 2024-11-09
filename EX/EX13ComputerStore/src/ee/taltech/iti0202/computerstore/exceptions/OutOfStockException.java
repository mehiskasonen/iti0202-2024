package ee.taltech.iti0202.computerstore.exceptions;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super("Not enough of item in stick.");
    }
}
