package ee.taltech.iti0202.sportsclub.registration;

import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.Membership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.RegularMembership;
import ee.taltech.iti0202.sportsclub.training.GroupTrainingSession;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.training.WebTrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;

import java.math.BigDecimal;
import java.util.List;

public class Validator {

    private Member member;
    private Membership membership;
    private TrainingSession trainingSession;

    private Integer bonusPoints;
    private SportsClub sportsClub;

    private Registration registration;

    /**
     * Class constructor for validator.
     * @param member to use in validation.
     * @param trainingSession to use in validation.
     */

    public Validator(Member member, TrainingSession trainingSession) {
        this.member = member;
        this.trainingSession = trainingSession;
    }

    /**
     * Overloaded class constructor for validator that is used for registration when bonus points are used.
     * @param member to use in validator.
     * @param trainingSession to use in validator.
     * @param bonuspoints used in registration.
     * @param club to use in validator.
     */
    public Validator(Member member, TrainingSession trainingSession, Integer bonuspoints, SportsClub club) {
        this.member = member;
        this.trainingSession = trainingSession;
        this.bonusPoints = bonuspoints;
        this.sportsClub = club;
    }

    /**
     * Overloaded class constructor for validator that is used by validator when buying a membership.
     * @param member to use in validator.
     * @param membership to use in validator.
     * @param club to use in validator.
     */
    public Validator(Member member, Membership membership, SportsClub club) {
        this.member = member;
        this.membership = membership;
        this.sportsClub = club;
    }

    /**
     * Checks if a member can register for this training session.
     *
     * @return True if the member can register, false otherwise.
     */
    public boolean canRegister() throws RegistrationException {
        if (trainingSession.getClass() != WebTrainingSession.class) {
            if (trainingSession.getParticipants().size() == trainingSession.getMaxParticipants()) {
                throw new RegistrationException(RegistrationException.Reason.MAX_PARTICIPANTS_REACHED);
            }
        }
        if (!validateMemberBelongsToClubTrainingSessionBelongsTo(member, trainingSession)) {
            throw new RegistrationException(RegistrationException.Reason.MEMBER_NOT_PART_OF_SESSIONS_CLUB);
        }
        if (trainingSession.getClass() == GroupTrainingSession.class && bonusPoints != 0) {
            throw new RegistrationException(RegistrationException.Reason.BONUS_POINT_SESSION_MISMATCH);
        }
        if (!validateMembershipTypeToSessionLevel()) {
            throw new RegistrationException(RegistrationException.Reason.MEMBERSHIP_SESSION_LEVEL_MISMATCH);
        }
        if (!validateMembersMembershipFee(member, trainingSession)) {
            throw new RegistrationException(RegistrationException.Reason.MEMBER_FREE_NOT_PAID);
        }
        if (trainingSession.getParticipants().contains(member)) {
            throw new RegistrationException(RegistrationException.Reason.MEMBER_ALREADY_REGISTERED);
        } else {
            return true;
        }
    }

    /**
     * A member with a regular membership can register only to sessions with level BEGINNER or INTERMEDIARY.
     * Meaning that a member with a regular membership can not register to advanced level sessions.
     * A member with full membership can register to sessions of all levels.
     *
     * @return boolean true if membership allows to register.
     */
    public boolean validateMembershipTypeToSessionLevel() {
        Membership currentMembership = member.getMembershipOverview().get(sportsClub);
        if (currentMembership.getClass() == FullMembership.class) {
            return true;
        }
        if ((currentMembership.getClass() == RegularMembership.class)
                && trainingSession.getLevel() == TrainingSessionLevels.ADVANCED) {
            return false;
        }
        return true;
    }

    /**
     * Validates if the member has paid the membership fee for the session's club.
     *
     * @param member          The member whose fee payment is being validated.
     * @param trainingSession The training session for which the fee is being validated.
     * @return True if the fee has been paid, false otherwise.
     */
    public boolean validateMembersMembershipFee(Member member, TrainingSession trainingSession) {
        List<SportsClub> memberClubs = member.getMemberClubs();
        for (int i = 0; i < memberClubs.size(); i++) {
            SportsClub club = memberClubs.get(i);
            for (Training training : club.getTrainings()) {
                for (TrainingSession session : training.getTrainingSessionList()) {
                    if (session.equals(trainingSession)) {
                        if (!member.getMembershipOverview().get(club).getExpiration()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if a member can buy a membership.
     *
     * @return True if the member can buy the membership, false otherwise.
     * @throws TransactionException NOT_ENOUGH_MONEY - If member does not have enough money to buy membership,
     * ALREADY_HAS_MEMBERSHIP - if member already has any kind of membership to that club.
     */
    public boolean canBuyMembership() throws TransactionException {
        double membershipFee = membership.getFee();
        BigDecimal memberWalletAmount = member.getMemberWallet().getAmount();
        if (memberWalletAmount.compareTo(BigDecimal.valueOf(membershipFee)) < 0) {
            throw new TransactionException(TransactionException.TransactionReason.NOT_ENOUGH_FUNDS);
        }
        if (member.getMembershipOverview().containsKey(sportsClub)) {
            throw new TransactionException(TransactionException.TransactionReason.ALREADY_HAS_MEMBERSHIP);
        } else {
            return true;
        }
    }

    /**
     * @param member who's clubs are being compared against training session.
     * @param trainingSession that is being compared against members club training sessions.
     * @return false if training session member is trying to register to belongs to another club
     * that the member is part of.
     * @throws RegistrationException if member is not part of any clubs.
     */
    public boolean validateMemberBelongsToClubTrainingSessionBelongsTo(Member member, TrainingSession trainingSession)
            throws RegistrationException {
        if (member.getMemberClubs().isEmpty()) {
            throw new RegistrationException(RegistrationException.Reason.MEMBER_NOT_PART_OF_ANY_CLUBS);
        }
        List<SportsClub> memberClubs = member.getMemberClubs();
        for (SportsClub club : memberClubs) {
            for (Training training : club.getTrainings()) {
                for (TrainingSession session : training.getTrainingSessionList()) {
                    if (session.equals(trainingSession)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if a member can deregister from a training session.
     *
     * @return True if the member can deregister, false otherwise.
     * @throws RegistrationException If de-registration fails because the member is not registered.
     */
    public boolean canDeregisterFromSession() throws RegistrationException {
        if (member.getRegisteredTrainingSessions().contains(trainingSession)) {
            return true;
        } else throw new RegistrationException(RegistrationException.Reason.MEMBER_NOT_REGISTERED);
    }

    /**
     * @return discount to be applied when buying or renewing membership to sports club.
     */
    public BigDecimal getDiscount() {
        return sportsClub.getDiscountStrategy()
                .map(strategy -> strategy.getDiscount(member))
                .orElse(BigDecimal.ZERO);
    }

}
