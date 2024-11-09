package strategy;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.Strategy;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * If member's participation count in training sessions is over average,
 * then member gets an appropriate amount of discount when paying for next month's membership fee.
 * Maximum possible discount is 60%.
 */
public class AvgParticipationStrategy implements Strategy {

    private static final double MAX_DISCOUNT_PERCENTAGE = 60;
    private static final int SCALE_TWO = 2;
    private SportsClub club;

    /**
     * Average Participation Strategy constructor.
     * @param club strategy is assigned to.
     */
    public AvgParticipationStrategy(SportsClub club) {
        this.club = club;
    }



    /**
     * Method that calculates discount.
     * Discount is determined by:
     * 1. members participation count: nr of members sessions in a club / clubs total training
     *          sessions
     * 2. average participation count:
     *      2.1 average = total participants nr over all clubs sessions / unique members:
     *      2.2 average participation count: average divided by total training sessions.
     * Finally discount = members participation count - average participation count.
     * Maximum possible discount is 60%.
     *
     * @param member to get discount.
     * @return BigDecimal of discount.
     */
    @Override
    public BigDecimal getDiscount(Member member) {
        List<TrainingSession> membersSessionsForClub = new ArrayList<>();

        int totalParticipants = 0;
        int totalTrainingSessions = 0;
        HashSet<Member> uniqueMembers = new HashSet<>();
        for (Training training : club.getTrainings()) {
            for (TrainingSession session : training.getTrainingSessionList()) {
                totalTrainingSessions++;
                totalParticipants += session.getParticipants().size();
                uniqueMembers.addAll(session.getParticipants());
                if (session.getParticipants().contains(member)) {
                    membersSessionsForClub.add(session);
                }
            }
        }

        if (membersSessionsForClub.isEmpty()) {
            return BigDecimal.valueOf(0);
        }

        BigDecimal memberParticipationCount = BigDecimal.valueOf(membersSessionsForClub.size()).divide(BigDecimal
                .valueOf(totalTrainingSessions)); // 1 sessions / 2 = 0,5
        BigDecimal avg = BigDecimal.valueOf(totalParticipants).divide(BigDecimal.valueOf(uniqueMembers.size()));
        BigDecimal avgParticipationCount = avg.divide(BigDecimal.valueOf(totalTrainingSessions));

        //int avgParticipationCount = totalParticipants / totalTrainingSessions; //3 members / 2 sessions = 1,5
        BigDecimal discountPercentage = memberParticipationCount.subtract(avgParticipationCount)
                .multiply(BigDecimal.valueOf(100))
                .setScale(SCALE_TWO, RoundingMode.HALF_UP);
        if (discountPercentage.compareTo(BigDecimal.valueOf(MAX_DISCOUNT_PERCENTAGE)) > 0) {
            return BigDecimal.valueOf(MAX_DISCOUNT_PERCENTAGE);
        }
        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            return discountPercentage;
        }
        return BigDecimal.ZERO;
    }
}
