package ee.taltech.iti0202.sportsclub.registration;

import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.Membership;
import ee.taltech.iti0202.sportsclub.users.Member;

import java.math.BigDecimal;

public class Transaction {
    private final Member member;
    private final SportsClub club;
    private Validator validator;
    private final Membership membership;

    /**
     * Constructor for Transaction class.
     * Transaction is used in the process of buying a membership
     *
     * @param member     The member for whom the transaction is being made.
     * @param club       The sports club where the membership is being bought.
     * @param membership The membership being bought.
     */
    public Transaction(Member member, SportsClub club, Membership membership) {
        this.member = member;
        this.membership = membership;
        this.club = club;
    }

    /**
     * Starts a transaction to buy a membership for the member.
     * Uses validator class to validate if member can buy a membership.
     * If a member is able to buy a membership, updates necessary data structures.
     *
     * @param member     The member for whom the transaction is being made.
     * @param club       The sports club where the membership is being bought.
     * @param membership The membership being bought.
     * @return True if the transaction was successful, false otherwise.
     * @throws TransactionException If the transaction fails due to certain reasons.
     */
    public boolean startTransactionBuyMembership(Member member, SportsClub club, Membership membership)
            throws TransactionException {
        Validator validator = new Validator(member, membership, club);
        if (validator.canBuyMembership()) {
            BigDecimal discount = validator.getDiscount();
            member.getMemberWallet()
                    .subtractFromMemberWallet(calculateMembershipCostWithDiscount(membership, discount));
            member.getMemberships().add(membership);
            member.getMemberClubs().add(club);
            club.addMember(member);
            updateMembershipOverview(member, club, membership);
            return true;
        }
        return false;
    }

    /**
     * @param membership to use in discount calculation.
     * @return final membership fee after discount.
     */
    public BigDecimal calculateMembershipCostWithDiscount(Membership membership, BigDecimal discount) {
        BigDecimal membershipFee = BigDecimal.valueOf(membership.getFee());
        BigDecimal discountAmount = membershipFee.multiply(discount).divide(BigDecimal.valueOf(100));
        return membershipFee.subtract(discountAmount);
    }

    /**
     * Updates the membership overview of the member with the new membership,
     * also sets the expiration of a membership to false, e.g. makes membership active.
     *
     * @param member     The member for whom the membership overview is being updated.
     * @param club       The sports club where the membership is being bought.
     * @param membership The membership being added to the membership overview.
     */
    public void updateMembershipOverview(Member member, SportsClub club, Membership membership) {
        membership.setExpiration(false);
        member.getMembershipOverview().put(club, membership);
    }

}
