package ee.taltech.iti0202.exam.exceptions;

public class TransactionException extends Exception{
    public final TransactionReason reason;

    /**
     * Exception class constructor.
     * @param reason to be thrown.
     */
    public TransactionException(TransactionException.TransactionReason reason) {
        this.reason = reason;
    }

    /**
     * @return reason for throwing an exception.
     */
    public TransactionException.TransactionReason getReason() {
        return reason;
    }

    public enum TransactionReason {
        NOT_ENOUGH_FUNDS
    }
}
