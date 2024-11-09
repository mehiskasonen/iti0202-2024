package ee.taltech.iti0202.computerstore.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("Product was not found!");
    }
}
