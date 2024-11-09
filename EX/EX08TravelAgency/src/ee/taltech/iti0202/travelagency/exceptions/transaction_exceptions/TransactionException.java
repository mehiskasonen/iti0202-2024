package ee.taltech.iti0202.travelagency.exceptions.transaction_exceptions;

public class TransactionException extends Exception {

    private Reason reason;

    /**
     * Exception class constructor.
     * @param reason to be thrown.
     */
    public TransactionException(Reason reason) {
        this.reason = reason;
    }

    /**
     * @return reason for throwing an error.
     */
    public Reason getReason() {
        return reason;
    }

    public enum Reason {
        FUND_WOULD_BE_NEGATIVE, NOT_ENOUGH_FUNDS
    }
}
