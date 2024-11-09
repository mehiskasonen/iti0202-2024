package ee.taltech.iti0202.sportsclub.sportsclub.membership;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.users.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MembershipTest {

    @Test
    void testConstructorFullMembershipHasCorrectDefaultFee() {
        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .build();
        assertEquals(60.0, membership.getFee());
    }

    @Test
    void testConstructorRegularMembershipHasCorrectDefaultFee() {
        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .build();
        assertEquals(40.0, membership.getFee());
    }

    @Test
    void testConstructorFullMembershipCanAssignMember() {
        Member member1 = new Member("member1", true);
        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withMember(member1)
                .build();
        assertEquals(member1, membership.getMember());
    }

    @Test
    void testConstructorRegularMembershipCanAssignMember() {
        Member member1 = new Member("member1", true);
        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withMember(member1)
                .build();
        assertEquals(member1, membership.getMember());
    }

    @Test
    void testConstructorFullMembershipCanAssignClub() {
        SportsClub club = new SportsClub("Club1");
        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();
        assertEquals(club, membership.getClub());
    }

    @Test
    void testConstructorRegularMembershipCanAssignClub() {
        SportsClub club = new SportsClub("Club1");
        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();
        assertEquals(club, membership.getClub());
    }


    @Test
    void testSetClub() {
        SportsClub club = new SportsClub("Club1");
        SportsClub club2 = new SportsClub("Club2");
        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();
        membership.setClub(club2);
        assertEquals(club2, membership.getClub());
    }

    @Test
    void testSetMember() {
        Member member1 = new Member("member1", true);
        Member member2 = new Member("member2", false);
        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withMember(member1)
                .build();
        membership.setMember(member2);
        assertEquals(member2, membership.getMember());
    }
}
