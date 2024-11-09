package ee.taltech.iti0202.mysticorbs.exceptions;

import ee.taltech.iti0202.mysticorbs.oven.Oven;

public class CannotFixException extends Exception {

    private Oven oven;
    private Reason reason;

    /**
     * Exception constructor.
     * @param oven that can not be fixed.
     * @param reason for not being able to fix.
     */
    public CannotFixException(Oven oven, Reason reason) {

        this.oven = oven;
        this.reason = reason;
    }

    public enum Reason {
        IS_NOT_BROKEN, FIXED_MAXIMUM_TIMES, NOT_ENOUGH_RESOURCES
    }

    public Oven getOven() {
        return oven;
    }

    public Reason getReason() {
        return reason;
    }
}
