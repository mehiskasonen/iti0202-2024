package strategy;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.Strategy;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;


public class CombinedStrategy implements Strategy {
    private static final double MAX_DISCOUNT_PERCENTAGE = 90;
    private static final int SCALE_TWO = 2;

    private SportsClub club;

    /**
     * Combined Strategy class constructor.
     * @param club strategy is assigned to.
     */
    public CombinedStrategy(SportsClub club) {
        this.club = club;
    }

    /**
     * Method to calculate discount for member when buying a membership.
     * @param member getting the discount.
     * @return discount amount.
     */
    public BigDecimal getDiscount(Member member) {
        AvgParticipationStrategy avgPartStrat = new AvgParticipationStrategy(club);
        BigDecimal avgParticipationStrategyDiscount = avgPartStrat.getDiscount(member);
        BonusPointsStrategy bpStrategy = new BonusPointsStrategy(club);
        BigDecimal bpDiscount = bpStrategy.getDiscount(member);
        BigDecimal firstTwoCombined = avgParticipationStrategyDiscount.add(bpDiscount);

        EnumSet<Sports> participatedSports = EnumSet.noneOf(Sports.class);
        for (TrainingSession session : member.getRegisteredTrainingSessions()) {
            participatedSports.add(session.getSport());
            }
        BigDecimal combinedStrategyDiscount = BigDecimal.ZERO;
        if (participatedSports.isEmpty() && firstTwoCombined.compareTo(BigDecimal.valueOf(0)) == 0) {
            return combinedStrategyDiscount;
        }
        if (participatedSports.size() <= 3) {
            combinedStrategyDiscount = BigDecimal.valueOf(5);
        }
        if (participatedSports.size() > 3) {
            combinedStrategyDiscount = BigDecimal.valueOf(10);
        }
        BigDecimal totalDiscount = firstTwoCombined.add(combinedStrategyDiscount);

        if (totalDiscount.compareTo(BigDecimal.valueOf(MAX_DISCOUNT_PERCENTAGE)) >= 0) {
            return BigDecimal.valueOf(MAX_DISCOUNT_PERCENTAGE).setScale(SCALE_TWO, RoundingMode.HALF_UP);
        }
        return totalDiscount;
    }
}
