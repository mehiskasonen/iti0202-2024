package strategy;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.Strategy;
import ee.taltech.iti0202.sportsclub.users.Member;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BonusPointsStrategy implements Strategy {

    private static final double MAX_DISCOUNT_PERCENTAGE = 20;
    private static final double DISCOUNT_MULTIPLIER = 0.25;

    private SportsClub club;

    /**
     * Bonus Points strategy class constructor.
     * @param club strategy is assigned to.
     */
    public BonusPointsStrategy(SportsClub club) {
        this.club = club;
    }

    /**
     * Method that calculates discount.
     * Discount is determined by all members all bonus points in a club divided by number of members,
     * then exceeded amount is multiplied by 0,25. Maximum discount amount is 20%.
     * @param member
     * @return
     */
    @Override
    public BigDecimal getDiscount(Member member) {
        double totalBP = 0;
        for (Member clubMember : club.getMembers()) {
                double clubBP = clubMember.getBonusPoints().getPoints(club);
                totalBP += clubBP;
        }
        double averageBP = totalBP / club.getMembers().size();
        if (member.getBonusPoints().getPoints(club) > averageBP) {
            BigDecimal exceededBP = BigDecimal.valueOf(member.getBonusPoints().getPoints(club))
                    .subtract(BigDecimal.valueOf(averageBP));
            exceededBP = exceededBP.setScale(2, RoundingMode.HALF_UP);
            BigDecimal discount = BigDecimal.valueOf(DISCOUNT_MULTIPLIER).multiply(exceededBP);
            discount = discount.setScale(2, RoundingMode.HALF_UP);
            if (discount.compareTo(BigDecimal.valueOf(MAX_DISCOUNT_PERCENTAGE)) >= 0) {
                return BigDecimal.valueOf(MAX_DISCOUNT_PERCENTAGE);
            }
            return discount;
        }
        return BigDecimal.ZERO;
    }
}
