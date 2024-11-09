package ee.taltech.iti0202.sportsclub.sportsclub.membership;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.users.Member;

/**
 * The abstract base class for building memberships.
 * This class provides methods for configuring membership attributes
 * such as the member, sports club, and free personal training sessions,
 * and for building the membership object.
 *
 * @param <T> The type of the concrete subclass extending MembershipBuilder.
 */
public abstract class MembershipBuilder<T extends MembershipBuilder<T>> {
    private double feee;
    protected SportsClub club;
    protected Member member;
    protected int freePersonalTrainingSessions;

    /**
     * @param fee The double amount that the membership being built should cost.
     * @return The concrete subclass instance.
     */
    public T withFee(double fee) {
        this.feee = fee;
        return self();
    }

    /**
     * Sets the number of free sessions the membership that is being built has.
     *
     * @param sessions The number of free sessions.
     * @return The concrete subclass instance.
     */
    public T withFreePersonalTrainingSessions(int sessions) {
        this.freePersonalTrainingSessions = sessions;
        return self();
    }

    /**
     * Sets the member for whom the membership is being built.
     *
     * @param member The member for whom the membership is being built.
     * @return The concrete subclass instance.
     */
    public T withMember(Member member) {
        this.member = member;
        return self();
    }

    /**
     * Sets the sports club associated with the membership.
     *
     * @param club The sports club associated with the membership.
     * @return The concrete subclass instance.
     */
    public T withClub(SportsClub club) {
        this.club = club;
        return self();
    }

    /**
     * Returns a reference to the concrete subclass instance.
     * This method must be implemented by each concrete subclass
     * to return itself, facilitating fluent method chaining.
     *
     * @return The concrete subclass instance.
     */
    protected abstract T self();

    /**
     * Builds and returns the membership object using the configured attributes.
     * This method must be implemented by each concrete subclass
     * to construct the appropriate type of membership object.
     *
     * @return The membership object.
     */
    public abstract Membership build();

}
