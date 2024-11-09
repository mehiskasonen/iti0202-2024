package ee.taltech.iti0202.sportsclub.sportsclub;

import ee.taltech.iti0202.sportsclub.users.Member;

import java.math.BigDecimal;

/**
 * Represents a strategy that determines discount given to a member.
 */
public interface Strategy {
    /**
     * Gets the discount that a member gets when paying the next monthly members fee.
     *
     * @return The action determined by the strategy.
     */
    BigDecimal getDiscount(Member member);

}
