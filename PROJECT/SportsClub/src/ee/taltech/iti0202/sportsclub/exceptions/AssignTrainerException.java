package ee.taltech.iti0202.sportsclub.exceptions;

public class AssignTrainerException extends Exception {

    private final AssignTrainerReason reason;

    /**
     * Exception class constructor.
     * @param reason to be thrown.
     */
    public AssignTrainerException(AssignTrainerException.AssignTrainerReason reason) {
        this.reason = reason;
    }

    /**
     * @return reason for throwing an error.
     */
    public AssignTrainerException.AssignTrainerReason getReason() {
        return reason;
    }

    public enum AssignTrainerReason {
        TRAINER_NOT_QUALIFIED, TRAINER_ALREADY_ASSIGNED
    }
}
