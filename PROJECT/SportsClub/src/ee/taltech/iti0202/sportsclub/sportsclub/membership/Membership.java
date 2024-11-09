package ee.taltech.iti0202.sportsclub.sportsclub.membership;


import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.users.Member;

public class Membership {

    private double fee;
    private SportsClub club;

    private Member member;
    int freePersonalTrainingSessions;

    private boolean expiration = true;

    /**
     * Membership class constructor.
     * @param club membership belongs to.
     * @param member membership belongs to.
     * @param fee amount the membership costs.
     */
    public Membership(SportsClub club, Member member, double fee) {
        this.club = club;
        this.member = member;
        this.fee = fee;
    }

    /**
     * @return membership fee amount.
     */
    public double getFee() {
        return fee;
    }

    /**
     * @return membership expiration status.
     */
    public boolean getExpiration() {
        return expiration;
    }

    /**
     * @param expiration to be set for membership.
     */
    public void setExpiration(boolean expiration) {
        this.expiration = expiration;
    }

    /**
     * @return member that has the membership.
     */
    public Member getMember() {
        return member;
    }

    /**
     * @return club that has the membership.
     */
    public SportsClub getClub() {
        return club;
    }

    /**
     * @param club membership is supposed to be set to.
     */
    public void setClub(SportsClub club) {
        this.club = club;
    }

    /**
     * @param member membership is supposed to be set to.
     */
    public void setMember(Member member) {
        this.member = member;
    }
}
