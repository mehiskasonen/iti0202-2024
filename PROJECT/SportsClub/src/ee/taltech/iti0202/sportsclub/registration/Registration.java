package ee.taltech.iti0202.sportsclub.registration;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.Membership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.RegularMembership;
import ee.taltech.iti0202.sportsclub.training.GroupTrainingSession;
import ee.taltech.iti0202.sportsclub.training.PersonalTrainingSession;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.training.WebTrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;

import java.math.BigDecimal;

public class Registration {

    private static final double ZERO_POINT_ONE = 0.1;
    private static final double ZERO_POINT_ZERO_FIVE = 0.05;
    private static final double FEE_20 = 20;
    private static final int BONUS_20 = 20;
    private static final int BONUS_8 = 8;
    private final Member member;
    private final TrainingSession trainingSession;

    private Integer bonusPoints = 0;


    /**
     * Registration class constructor.
     * @param member to register to training session.
     * @param trainingSession to register the member to.
     */
    public Registration(Member member, TrainingSession trainingSession) {
        this.member = member;
        this.trainingSession = trainingSession;
    }

    /**
     * @param member that makes registration.
     * @param trainingSession member is registering to.
     * @param bonusPoints used when registering.
     * @throws RegistrationException USED_NEGATIVE_BONUS when negative bp is provided, NOT_ENOUGH_BONUS_POINTS,
     * if member uses more bonus points than they have.
     */
    public Registration(Member member, TrainingSession trainingSession, Integer bonusPoints)
            throws RegistrationException {
        this.member = member;
        this.trainingSession = trainingSession;
        this.bonusPoints = bonusPoints;
        if (bonusPoints < 0) {
            throw new RegistrationException(RegistrationException.Reason.USED_NEGATIVE_BONUS);
        }
        if (bonusPoints > member.getBonusPoints().getPoints(getSessionsClub())) {
            throw new RegistrationException(RegistrationException.Reason.NOT_ENOUGH_BONUS_POINTS);
        }
    }

    /**
     * Registers a member for this training session.
     *
     * @param member The member to register.
     * @return True if the member was successfully registered, false otherwise.
     */
    public boolean registerMemberToSession(Member member, TrainingSession trainingSession)
            throws RegistrationException {
        SportsClub club = getSessionsClub();
        Validator validator = new Validator(member, trainingSession, bonusPoints, club);
        if (validator.canRegister()) {
            trainingSession.getParticipants().add(member);
            member.getRegisteredTrainingSessions().add(trainingSession);
            if ((trainingSession.getClass() == PersonalTrainingSession.class
                    || trainingSession.getClass() == WebTrainingSession.class)
            && this.bonusPoints != null) {
                calculateMembersDiscount(bonusPoints);
                updateRegistrationBonusPoints();
                updateMemberWallet();
                return true;
            }
            updateRegistrationBonusPoints();
            updateMemberWallet();
            return true;
        }
        return false;
    }


    /**
     * De-registers a member from this training session.
     *
     * @param member The member to be deregistered.
     * @return True if the member was successfully deregistered, false otherwise.
     * @throws RegistrationException If de-registration fails due to certain reasons.
     */
    public boolean deregisterMemberFromSession(Member member, TrainingSession trainingSession)
            throws RegistrationException {
        Validator validator = new Validator(member, trainingSession);
        if (validator.canDeregisterFromSession()) {
            trainingSession.getParticipants().remove(member);
            member.getRegisteredTrainingSessions().remove(trainingSession);
            return true;
        }
        return false;
    }


    /**
     * Method to determine discount when member is registering to training session.
     * Discount only applies to personal and web training sessions.
     * Full membership grants 10 cents discount for each bonus point.
     * Regular membership grants 5 cents discount for each bonus point.
     *
     * @param amount of bonus points to spend of discount.
     * @return discount amount
     */
    public BigDecimal calculateMembersDiscount(int amount) {
        BigDecimal discount = new BigDecimal("0");
        Membership membership = getMembershipTypeMatchingClub();
        if (membership instanceof FullMembership) {
            discount = BigDecimal.valueOf(ZERO_POINT_ONE * amount);
        }
        if (membership instanceof RegularMembership) {
            discount = BigDecimal.valueOf(ZERO_POINT_ZERO_FIVE * amount);
        }
        return discount;
    }

    /**
     * Calculates final registration price for a member.
     * With full membership first personal training is free and each executive costs 20 euros.
     * After the first free training session is used, sets the value to 0.
     * Price is calculated according to formula "price = training session intrinsic price - possible discount"
     *
     * @return double of total cost to register.
     */
    public BigDecimal calculateRegistrationPrice() {
        BigDecimal price = new BigDecimal("0");
        if (getMembershipTypeMatchingClub() instanceof FullMembership
                && ((FullMembership) getMembershipTypeMatchingClub()).getDefaultFreeTrainingSessions() == 1
                && trainingSession instanceof PersonalTrainingSession) {
            ((FullMembership) getMembershipTypeMatchingClub()).setDefaultFreeTrainingSessions(0);
            return price;
        }
        if (getMembershipTypeMatchingClub() instanceof FullMembership
        && ((FullMembership) getMembershipTypeMatchingClub()).getDefaultFreeTrainingSessions() == 0
        && trainingSession instanceof PersonalTrainingSession) {
            price = (BigDecimal.valueOf(FEE_20).subtract(calculateMembersDiscount(bonusPoints)));
        }
        if (getMembershipTypeMatchingClub() instanceof RegularMembership
        && trainingSession instanceof PersonalTrainingSession) {
            price = ((PersonalTrainingSession) trainingSession)
                    .getDefaultPrice().subtract(calculateMembersDiscount(bonusPoints));
        }
        if (trainingSession instanceof WebTrainingSession) {
            price = ((WebTrainingSession) trainingSession)
                    .getDefaultPrice().subtract(calculateMembersDiscount(bonusPoints));
        }
        return price;
    }


    /**
     * Method that subtracts sessions registration cost from the members wallet amount.
     * Uses calculateRegistrationPrice() to get the exact price for registration.
     */
    public void updateMemberWallet() {
        member.getMemberWallet().setAmount(member.getMemberWallet().getAmount().subtract(calculateRegistrationPrice()));
    }

    /**
     * Method to determine how many bonus points the member gets for registering to a session.
     * Bonus points are added to the user in the club that the training session belongs to.

     * For personal training session full membership grants 20 points, regular membership grants 10 points.
     * For group training session full membership grants 4 points, regular membership grants 2 points.
     * FOr web training session full membership grants 8 points, regular membership grants 4 points.
     */
    public void updateRegistrationBonusPoints() {
        SportsClub club = getSessionsClub();
        if (bonusPoints != 0) {
            member.getBonusPoints().subtractBonusPoints(club, bonusPoints);
        }
        Membership membership = getMembershipTypeMatchingClub();
        if (trainingSession instanceof PersonalTrainingSession) {
            if (membership instanceof FullMembership) {
                member.getBonusPoints().addBonusPoints(club, BONUS_20);
            } else {
                member.getBonusPoints().addBonusPoints(club, 10);
            }
        }
        if (trainingSession instanceof GroupTrainingSession) {
            if (membership instanceof FullMembership) {
                member.getBonusPoints().addBonusPoints(club, 4);
            } else {
                member.getBonusPoints().addBonusPoints(club, 2);
            }
        }
        if (trainingSession instanceof WebTrainingSession) {
            if (membership instanceof FullMembership) {
                member.getBonusPoints().addBonusPoints(club, BONUS_8);
            } else {
                member.getBonusPoints().addBonusPoints(club, 4);
            }
        }
    }

    /**
     * Helper method for addRegistrationBonusPoints();
     * Method to determine the club session user is registering belongs to.
     * @return club
     */
    private SportsClub getSessionsClub() {
        for (SportsClub club : member.getMemberClubs()) {
            for (Training training : club.getTrainings()) {
                for (TrainingSession session : training.getTrainingSessionList()) {
                    if (session.equals(trainingSession)) {
                        return club;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Helper method for addRegistrationBonusPoints() and getSessionsClub().
     * @return membership type member has for current registration.
     */
    private Membership getMembershipTypeMatchingClub() {
        return member.getMembershipOverview().get(getSessionsClub());
    }

}
