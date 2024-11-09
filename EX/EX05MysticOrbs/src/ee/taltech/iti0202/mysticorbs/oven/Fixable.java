package ee.taltech.iti0202.mysticorbs.oven;

import ee.taltech.iti0202.mysticorbs.exceptions.CannotFixException;

public interface Fixable {
    /**
     * method to fix oven that needs to be implemented.
     * @throws CannotFixException why oven can not be fixed.
     */
    void fix() throws CannotFixException;

    /**
     * @return nr of times oven has been fixed.
     */
    int getTimesFixed();
}
