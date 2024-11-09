package ee.taltech.iti0202.sportsclub.exceptions;

public class RegistrationException extends Exception {

    private final Reason reason;

    /**
     * Exception class constructor.
     * @param reason to be thrown.
     */
    public RegistrationException(Reason reason) {
        this.reason = reason;
    }

    /**
     * @return reason for throwing an exception.
     */
    public Reason getReason() {
        return reason;
    }

    public enum Reason {
        MEMBER_ALREADY_REGISTERED, MEMBER_FREE_NOT_PAID, MAX_PARTICIPANTS_REACHED, UNSPECIFIED,
        MEMBER_NOT_REGISTERED, MEMBER_NOT_PART_OF_SESSIONS_CLUB, MEMBER_NOT_PART_OF_ANY_CLUBS,
        MEMBERSHIP_SESSION_LEVEL_MISMATCH, BONUS_POINT_SESSION_MISMATCH, USED_NEGATIVE_BONUS,
        NOT_ENOUGH_BONUS_POINTS
    }
}
