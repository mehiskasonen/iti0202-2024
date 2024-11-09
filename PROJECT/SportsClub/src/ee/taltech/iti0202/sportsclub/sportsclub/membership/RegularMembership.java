package ee.taltech.iti0202.sportsclub.sportsclub.membership;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.users.Member;

public class RegularMembership extends Membership {

    private static final double FEE_40 = 40.0;
    private static final double FEE = FEE_40;

    /**
     * @param club that the membership is for.
     * @param member that the membership is for.
     * @param fee the regular membership can have.
     */
    public RegularMembership(SportsClub club, Member member, double fee) {
        super(club, member, fee);
    }

    public static class Builder extends MembershipBuilder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public RegularMembership build() {
            return new RegularMembership(club, member, FEE);
        }
    }
}
