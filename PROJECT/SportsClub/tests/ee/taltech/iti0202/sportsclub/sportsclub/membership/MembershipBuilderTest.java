package ee.taltech.iti0202.sportsclub.sportsclub.membership;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.users.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MembershipBuilderTest {

    @Test
    void withFreePersonalTrainingSessions() {
        MembershipBuilderImplementation builder = new MembershipBuilderImplementation();
        builder.withFreePersonalTrainingSessions(5); // Example value
        assertEquals(5, builder.freePersonalTrainingSessions);
    }

    @Test
    void withMember() {
        MembershipBuilderImplementation builder = new MembershipBuilderImplementation();
        Member member = new Member("John", true); // Example member
        builder.withMember(member);
        assertEquals(member, builder.member);
    }

    @Test
    void withClub() {
        MembershipBuilderImplementation builder = new MembershipBuilderImplementation();
        SportsClub club = new SportsClub("Club1"); // Example club
        builder.withClub(club);
        assertEquals(club, builder.club);
    }

    @Test
    void testBuild() {
        Member member = new Member("John", true); // Example member
        SportsClub club = new SportsClub("Club1"); // Example club
        MembershipBuilderImplementation builder = new MembershipBuilderImplementation()
                .withMember(member)
                .withClub(club)
                .withFee(50.0); // Example fee
        Membership membership = builder.build();
        assertNotNull(membership);
        assertEquals(member, membership.getMember());
        assertEquals(club, membership.getClub());
        assertEquals(50.0, membership.getFee());
    }

        public static class MembershipBuilderImplementation
                extends MembershipBuilder<MembershipBuilderImplementation> {

            private Member member;
            private SportsClub club;
            private double fee;

            @Override
            protected MembershipBuilderImplementation self() {
                return this;
            }

            @Override
            public Membership build() {
                return new Membership(club, member, fee);
            }

            public MembershipBuilderImplementation withMember(Member member) {
                this.member = member;
                return this;
            }

            public MembershipBuilderImplementation withClub(SportsClub club) {
                this.club = club;
                return this;
            }

            public MembershipBuilderImplementation withFee(double fee) {
                this.fee = fee;
                return this;
            }
    }
}
