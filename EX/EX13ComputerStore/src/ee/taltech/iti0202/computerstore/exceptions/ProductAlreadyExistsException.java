package ee.taltech.iti0202.computerstore.exceptions;

public class ProductAlreadyExistsException extends Exception {
    public ProductAlreadyExistsException() {
        super("Product already exists!");
    }
}
