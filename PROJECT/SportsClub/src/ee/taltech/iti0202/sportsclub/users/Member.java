package ee.taltech.iti0202.sportsclub.users;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.registration.Registration;
import ee.taltech.iti0202.sportsclub.registration.Transaction;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.bonuspoints.BonusPoints;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.Membership;
import ee.taltech.iti0202.sportsclub.training.PersonalTrainingSession;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.training.WebTrainingSession;
import ee.taltech.iti0202.sportsclub.users.search.CriteriaBuilder;
import ee.taltech.iti0202.sportsclub.users.wallet.Wallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a member in the sports club system.
 */
public class Member extends User {
    private boolean isMemberFeePaid;
    private List<SportsClub> memberClubs = new ArrayList<>();
    private HashMap<SportsClub, Membership> membershipOverview = new HashMap<>();
    private List<TrainingSession> registeredTrainingSessions = new ArrayList<>();
    private Wallet memberWallet;
    private BonusPoints bonusPoints;
    private List<Membership> memberships = new ArrayList<>();

    /**
     * Constructs a new member with the given name and member fee payment status.
     *
     * @param name            The name of the member.
     * @param isMemberFeePaid True if the member fee is paid, false otherwise.
     */
    public Member(String name, boolean isMemberFeePaid) {
        super(name);
        this.isMemberFeePaid = isMemberFeePaid;
        memberWallet = new Wallet(BigDecimal.valueOf(0));
        bonusPoints = new BonusPoints(new HashMap<>());
    }

    /**
     * @return a map of sports clubs the member has joined/has taken part of trainings
     * and a value if membership fee is paid for that club or not.
     */
    public HashMap<SportsClub, Membership> getMembershipOverview() {
        buildMembershipOverview();
        return this.membershipOverview;
    }

    /**
     * Method that builds the members overview of sports clubs and member fees status.
     */
    public void buildMembershipOverview() {
        for (Membership membership : this.memberships) {
            if (!this.membershipOverview.containsKey(membership.getClub())) {
                this.membershipOverview.put(membership.getClub(), membership);
            }
        }
    }

    /**
     * @return map of members sports clubs and a boolean weather members fee is paid (e.g. not expired)
     */
    public Map<SportsClub, Boolean> getFeeOverview() {
        Map<SportsClub, Boolean> memberFeeMap = new HashMap<>();
        for (Map.Entry<SportsClub, Membership> entry : membershipOverview.entrySet()) {
            memberFeeMap.put(entry.getKey(), entry.getValue().getExpiration());
        }
        return memberFeeMap;
    }

    /**
     * @return members wallet.
     */
    public Wallet getMemberWallet() {
        return this.memberWallet;
    }

    /**
     * @return list of members memberships.
     */
    public List<Membership> getMemberships() {
        return this.memberships;
    }

    /**
     * Adds a membership to users list of memberships.
     * @param membership to be added.
     */
    public void addMembership(Membership membership) {
        this.memberships.add(membership);
        buildMembershipOverview();
    }

    /**
     * @return BonusPoints object.
     */
    public BonusPoints getBonusPoints() {
        return this.bonusPoints;
    }

    /**
     * Method for member to buy a membership.
     * @param club to buy membership to.
     * @param membershipPacket which kind of membership to buy.
     * @return {@code true} if buying is successful
     *         {@code false} if buying is unsuccessful and no exception was thrown.
     * @throws TransactionException if any was countered during transaction process.
     */
    public boolean buyMembership(SportsClub club, Membership membershipPacket)
            throws TransactionException {
        Transaction transaction = new Transaction(this, club, membershipPacket);
        System.out.println("User initiated buying membership");
        return transaction.startTransactionBuyMembership(this, club, membershipPacket);
    }

    /**
     * Searches for training sessions that meet the specified criteria.
     *
     * @param criteriaBuilder The CriteriaBuilder object used to define the search criteria.
     * @return A HashMap containing the training sessions that meet the criteria,
     *         along with corresponding information such as the sports club name, start time, and type of training.
     */
    public HashMap<TrainingSession, List<String>> search(CriteriaBuilder criteriaBuilder) {
        HashMap<TrainingSession, List<String>> result = new LinkedHashMap<>();
        for (SportsClub club : memberClubs) {
            List<Training> clubTrainings = club.getTrainings();
            for (Training training : clubTrainings) {
                List<TrainingSession> trainingSessions = training.getTrainingSessionList();
                for (TrainingSession session : trainingSessions) {
                    if (criteriaBuilder.meetsCriteria(session)) {
                        List<String> sessionInfo = new ArrayList<>();
                        sessionInfo.add(club.getName());
                        sessionInfo.add(session.getStartTime().toString());
                        sessionInfo.add(session.getType().toString());
                        result.put(session, sessionInfo);
                    }
                }
            }
        }
        return result;
    }


    /**
     * Retrieves the payment status of the member fee.
     *
     * @return True if the member fee is paid, false otherwise.
     */
    public boolean getMemberFeeStatus() {
        return isMemberFeePaid;
    }

    /**
     * Sets the payment status of the member fee.
     *
     * @param feePaid True if the member fee is paid, false otherwise.
     */
    public void setMemberFeeStatus(boolean feePaid) {
        this.isMemberFeePaid = feePaid;
    }

    /**
     * Retrieves a list of training sessions that this member is registered to.
     *
     * @return A list of training sessions that this member is registered to.
     */
    public List<TrainingSession> getRegisteredTrainingSessions() {
        return registeredTrainingSessions;
    }

    /**
     * Retrieves a list of sports clubs associated with this member.
     *
     * @return A list of sports clubs associated with this member.
     */
    public List<SportsClub> getMemberClubs() {
        return this.memberClubs;
    }

    /**
     * Add a sportsClub to members sportsClub list.
     *
     * @param sportsClub member is joining.
     */
    public void addClub(SportsClub sportsClub) {
        memberClubs.add(sportsClub);
    }

    /**
     * Registers the member to a training session.
     *
     * @param trainingSession The training session to register for.
     * @return True if the registration was successful, false otherwise.
     * @throws RegistrationException If the registration fails due to certain reasons.
     */
    public boolean registerToTrainingSession(TrainingSession trainingSession) throws RegistrationException {
        Registration registration = new Registration(this, trainingSession);
        return registration.registerMemberToSession(this, trainingSession);
    }

    /**
     * Overloaded registration method, if member wishes to use bonus points when registering.
     * Bonus points can only be used when registering to personal or web training sessions.
     *
     * @param trainingSession member wants to register to.
     * @param bonusPointsToUse amount of points member wishes to use.
     * @return {@code true} if registration was successful
     *          {@code false} if registration was unsuccessful and no exception was thrown.
     * @throws RegistrationException if a problem was encountered during registration.
     */
    public boolean registerToTrainingSession(TrainingSession trainingSession, Integer bonusPointsToUse)
            throws RegistrationException {
        if (!(trainingSession instanceof PersonalTrainingSession || trainingSession instanceof WebTrainingSession)) {
            throw new RegistrationException(RegistrationException.Reason.BONUS_POINT_SESSION_MISMATCH);
        }
        Registration registration = new Registration(this, trainingSession, bonusPointsToUse);
        return registration.registerMemberToSession(this, trainingSession);
    }

    /**
     * Front method for member to initialise de-registration from a session.
     *
     * @param trainingSession to deregister from.
     * @return true if de-registration was successful.
     * @throws RegistrationException if de-registering fails.
     */
    public boolean deregisterFromTrainingSession(TrainingSession trainingSession) throws RegistrationException {
        Registration registration = new Registration(this, trainingSession);
        if (registration.deregisterMemberFromSession(this, trainingSession)) {
            return true;
        }
        return false;
    }

}
