package ee.taltech.iti0202.computerstore.exceptions;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
        super("Not enough money!");
    }
}
