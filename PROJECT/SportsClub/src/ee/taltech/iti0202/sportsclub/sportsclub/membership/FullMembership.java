package ee.taltech.iti0202.sportsclub.sportsclub.membership;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.users.Member;

public class FullMembership extends Membership {

    private static final double FEE_60 = 60;
    private static double fee = FEE_60;
    private int defaultFreeTrainingSessions = 1;

    /**
     * @param club that the membership is for.
     * @param member that the membership is for.
     * @param fee that the membership is supposed to cost.
     */
    public FullMembership(SportsClub club, Member member, double fee) {
        super(club, member, fee);
        this.freePersonalTrainingSessions = defaultFreeTrainingSessions;
    }

    /**
     * @return memberships default free training sessions count.
     */
    public int getDefaultFreeTrainingSessions() {
        return defaultFreeTrainingSessions;
    }

    /**
     * Sets the nr of default free training sessions.
     * @param defaultFreeTrainingSessions int for the default count to be set to.
     */
    public void setDefaultFreeTrainingSessions(int defaultFreeTrainingSessions) {
        this.defaultFreeTrainingSessions = defaultFreeTrainingSessions;
    }

    public static class Builder extends MembershipBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public FullMembership build() {
            return new FullMembership(club, member, fee);
        }
    }

}
