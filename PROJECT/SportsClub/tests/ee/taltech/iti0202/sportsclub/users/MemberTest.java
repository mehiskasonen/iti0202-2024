package ee.taltech.iti0202.sportsclub.users;

import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.registration.Registration;
import ee.taltech.iti0202.sportsclub.registration.Transaction;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.Membership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.RegularMembership;
import ee.taltech.iti0202.sportsclub.training.GroupTrainingSession;
import ee.taltech.iti0202.sportsclub.training.PersonalTrainingSession;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.users.search.CriteriaBuilder;
import org.junit.jupiter.api.Test;
import projbasetest.StrategyBaseTest;
import strategy.BonusPointsStrategy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.JOGA;
import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.ADVANCED;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberTest extends StrategyBaseTest {

    @Test
    void testConstructorAndMemberFeeStatusTrue() {
        Member member = new Member("Member", true);
        assertTrue(member.getMemberFeeStatus());
    }

    @Test
    void testGetMemberFeeStatusFalse() {
        Member member = new Member("Member", false);
        assertFalse(member.getMemberFeeStatus());
    }

    @Test
    void testSetMemberFeeStatusTrueToFalse() {
        Member member = new Member("Member", true);
        member.setMemberFeeStatus(false);
        assertFalse(member.getMemberFeeStatus());
    }

    @Test
    void testSetMemberFeeStatusFalseToTrue() {
        Member member = new Member("Member", false);
        member.setMemberFeeStatus(true);
        assertTrue(member.getMemberFeeStatus());
    }

    @Test
    void testGetRegisteredTrainingSessionsHasSessions() throws RegistrationException, TransactionException {
        SportsClub sportsClub = new SportsClub("Joga Club");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        TrainingSession jogaSession = new TrainingSession("Joga", JOGA, Optional.of(trainer));

        Training judoTraining = new Training("Judo", JUDO, trainer);
        Training jogaTraining = new Training("Joga", JOGA, trainer);

        judoSession.setMaxParticipants(4);
        jogaSession.setMaxParticipants(4);

        sportsClub.addTrainer(trainer);
        sportsClub.addTraining(judoTraining);
        sportsClub.addTraining(jogaTraining);
        judoTraining.addTrainingSession(judoSession);
        jogaTraining.addTrainingSession(jogaSession);

        Member member = new Member("Member", true);
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        sportsClub.addMember(member);
        member.addClub(sportsClub);

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(sportsClub)
                .build();
        member.buyMembership(sportsClub, membership);

        member.registerToTrainingSession(judoSession);
        member.registerToTrainingSession(jogaSession);

        assertEquals(2, member.getRegisteredTrainingSessions().size());
    }

    @Test
    void testGetRegisteredTrainingSessionsNoSessions() throws RegistrationException {
        SportsClub sportsClub = new SportsClub("Joga Club");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(2);
        sportsClub.addTrainer(trainer);

        Training judoTraining = new Training("Judo", JUDO, trainer);
        sportsClub.addTraining(judoTraining);

        Member member = new Member("Member", true);
        sportsClub.addMember(member);
        member.addClub(sportsClub);

        assertEquals(List.of(), member.getRegisteredTrainingSessions());
    }

    @Test
    void testGetClientClubsCorrectNumber() {
        SportsClub club1 = new SportsClub("Club1");
        SportsClub club2 = new SportsClub("Club2");
        Member member = new Member("Member", true);
        member.addClub(club1);
        member.addClub(club2);
        assertEquals(2, member.getMemberClubs().size());
    }

    @Test
    void testAddClubAddsClubCorrectNumber() {
        Member member = new Member("Member", true);
        SportsClub club1 = new SportsClub("Club1");
        SportsClub club2 = new SportsClub("Club2");
        member.addClub(club1);
        member.addClub(club2);
        assertEquals(2, member.getMemberClubs().size());
    }

    @Test
    void getMembershipOverview() {
        Member member = new Member("Member", false);
        SportsClub sportsClub1 = new SportsClub("Club1");
        SportsClub sportsClub2 = new SportsClub("Club2");
        FullMembership.Builder builder = new FullMembership.Builder();

        FullMembership membership1 = builder
                .withMember(member)
                .withClub(sportsClub1)
                .build();

        RegularMembership.Builder regularBuilder = new RegularMembership.Builder();
        RegularMembership membership2 = regularBuilder
                .withMember(member)
                .withClub(sportsClub2)
                .build();

        member.addMembership(membership1);
        member.addMembership(membership2);

        Map<SportsClub, Membership> expected = new HashMap<>();
        expected.put(sportsClub1, membership1);
        expected.put(sportsClub2, membership2);

        assertEquals(expected, member.getMembershipOverview());
    }

    @Test
    void testSetMembershipOverview() {
        Member member = new Member("Member", true);

    }

    @Test
    void testGetFeeOverviewManual() {
        Member member = new Member("Member", true);
        SportsClub club1 = new SportsClub("Club1");
        SportsClub club2 = new SportsClub("Club2");

        Membership fullMembership = new FullMembership(club1, member, 30);
        fullMembership.setExpiration(false);
        Membership regularMembership = new RegularMembership(club2, member, 20);
        regularMembership.setExpiration(false);

        member.addMembership(fullMembership);
        member.addMembership(regularMembership);
        Map<SportsClub, Boolean> expected = new HashMap<>();
        expected.put(club1, false);
        expected.put(club2, false);
        assertEquals(expected, member.getFeeOverview());
    }

    @Test
    void testGetFeeOverviewWithBuying() throws TransactionException {
        Member member = new Member("Member", true);
        SportsClub club1 = new SportsClub("Club1");
        SportsClub club2 = new SportsClub("Club2");

        member.getMemberWallet().setAmount(BigDecimal.valueOf(100));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        FullMembership.Builder fullBuilder = new FullMembership.Builder();
        FullMembership fullMembership = fullBuilder
                .withClub(club1)
                .build();

        RegularMembership.Builder regularBuilder = new RegularMembership.Builder();
        RegularMembership regularMembership = regularBuilder
                .withClub(club1)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club1.addTraining(training);

        member.buyMembership(club1, fullMembership);
        member.buyMembership(club2, regularMembership);

        Map<SportsClub, Boolean> expected = new HashMap<>();
        expected.put(club1, false);
        expected.put(club2, false);
        assertEquals(expected, member.getFeeOverview());
    }

    @Test
    void testSearchByTrainingSessionLevel() {
        Member member = new Member("Member1", true);
        SportsClub club1 = new SportsClub("Club1");
        member.addClub(club1);
        Trainer jogaTrainer = new Trainer("JogaTrainer", Collections.singletonList(JOGA));

        Training training = new Training("Training", JOGA, jogaTrainer);
        GroupTrainingSession groupJoga = new GroupTrainingSession("GroupJogaBegEarly",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(14, 02, 24, 10, 0), BEGINNER);


        GroupTrainingSession groupJogaAdvanced = new GroupTrainingSession("GroupJogaAdv",
                JOGA, Optional.empty(), LocalDateTime.of(14, 02, 24, 12, 0), ADVANCED);


        GroupTrainingSession groupJogaBeginner = new GroupTrainingSession("GroupJogaBeginnerLate",
                JOGA, Optional.empty(), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        training.addTrainingSession(groupJoga);
        training.addTrainingSession(groupJogaAdvanced);
        training.addTrainingSession(groupJogaBeginner);

        club1.addTraining(training);

        CriteriaBuilder builder = new CriteriaBuilder()
                .level(TrainingSessionLevels.BEGINNER);

        Map<TrainingSession, List<String>> result = member.search(builder);
        assertTrue(result.containsKey(groupJoga));
        assertTrue(result.containsKey(groupJogaBeginner));
    }

    @Test
    void testSearchByStartTime() {
        Member member = new Member("Member1", true);
        SportsClub club1 = new SportsClub("Club1");
        member.addClub(club1);
        Trainer jogaTrainer = new Trainer("JogaTrainer", Collections.singletonList(JOGA));

        Training training = new Training("Training", JOGA, jogaTrainer);
        GroupTrainingSession groupJoga = new GroupTrainingSession("GroupJogaBegEarly",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(14, 02, 24, 10, 0), BEGINNER);

        GroupTrainingSession groupJogaAdvanced = new GroupTrainingSession("GroupJogaAdv",
                JOGA, Optional.empty(), LocalDateTime.of(14, 02, 24, 12, 0), ADVANCED);

        training.addTrainingSession(groupJoga);
        training.addTrainingSession(groupJogaAdvanced);

        club1.addTraining(training);

        CriteriaBuilder builder = new CriteriaBuilder()
                .startTime(LocalTime.of(12, 0));

        Map<TrainingSession, List<String>> result = member.search(builder);
        assertTrue(result.containsKey(groupJogaAdvanced));
        assertFalse(result.containsKey(groupJoga));
    }

    @Test
    void testSearchByTrainingType() {
        Member member = new Member("Member1", true);
        SportsClub club1 = new SportsClub("Club1");
        member.addClub(club1);
        Trainer jogaTrainer = new Trainer("JogaTrainer", Collections.singletonList(JOGA));

        Training training = new Training("Training", JOGA, jogaTrainer);

        GroupTrainingSession groupJoga = new GroupTrainingSession("GroupJogaBegEarly",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(14, 02, 24, 10, 0), BEGINNER);

        PersonalTrainingSession personalJogaAdvanced = new PersonalTrainingSession("PersonalJogaAdv",
                JOGA, Optional.empty(), LocalDateTime.of(14, 02, 24, 14, 0), ADVANCED);

        training.addTrainingSession(groupJoga);
        training.addTrainingSession(personalJogaAdvanced);

        club1.addTraining(training);

        CriteriaBuilder builder = new CriteriaBuilder()
                .type(TrainingType.PERSONAL_TRAINING);

        Map<TrainingSession, List<String>> result = member.search(builder);
        assertTrue(result.containsKey(personalJogaAdvanced));
        assertFalse(result.containsKey(groupJoga));

    }

    @Test
    void testSearchByDayOfWeek() {
        Member member = new Member("Member1", true);
        SportsClub club1 = new SportsClub("Club1");
        member.addClub(club1);
        Trainer jogaTrainer = new Trainer("JogaTrainer", Collections.singletonList(JOGA));

        Training training = new Training("Training", JOGA, jogaTrainer);

        GroupTrainingSession groupJoga = new GroupTrainingSession("GroupJogaBegEarly",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(24, 05, 17, 10, 0), BEGINNER);

        PersonalTrainingSession personalJogaAdvanced = new PersonalTrainingSession("PersonalJogaAdv",
                JOGA, Optional.empty(), LocalDateTime.of(24, 05, 18, 14, 0), ADVANCED);

        training.addTrainingSession(groupJoga);
        training.addTrainingSession(personalJogaAdvanced);

        club1.addTraining(training);
        CriteriaBuilder builder = new CriteriaBuilder()
                .dayOfWeek(DayOfWeek.SATURDAY);
        Map<TrainingSession, List<String>> result = member.search(builder);
        assertTrue(result.containsKey(personalJogaAdvanced));
        assertFalse(result.containsKey(groupJoga));

    }

    @Test
    void testSearchByDateRange() {
        Member member = new Member("Member1", true);
        SportsClub club1 = new SportsClub("Club1");
        member.addClub(club1);
        Trainer jogaTrainer = new Trainer("JogaTrainer", Collections.singletonList(JOGA));

        Training training = new Training("Training", JOGA, jogaTrainer);

        GroupTrainingSession groupJoga = new GroupTrainingSession("GroupJogaBegEarly",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(2024, 06, 2, 10, 0), BEGINNER);

        PersonalTrainingSession personalJogaAdvanced = new PersonalTrainingSession("PersonalJogaAdv",
                JOGA, Optional.empty(), LocalDateTime.of(2024, 05, 1, 14, 0), ADVANCED);

        training.addTrainingSession(groupJoga);
        training.addTrainingSession(personalJogaAdvanced);

        club1.addTraining(training);
        CriteriaBuilder builder = new CriteriaBuilder()
                .dateRange(LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 31));
        Map<TrainingSession, List<String>> result = member.search(builder);
        assertTrue(result.containsKey(personalJogaAdvanced));
        assertFalse(result.containsKey(groupJoga));

    }

    @Test
    void testSearchByDateRangeStartAfterEndThrowsException() {
        Member member = new Member("Member1", true);
        SportsClub club1 = new SportsClub("Club1");
        member.addClub(club1);
        Trainer jogaTrainer = new Trainer("JogaTrainer", Collections.singletonList(JOGA));

        Training training = new Training("Training", JOGA, jogaTrainer);

        GroupTrainingSession groupJoga = new GroupTrainingSession("GroupJogaBegEarly",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(2024, 06, 2, 10, 0), BEGINNER);

        PersonalTrainingSession personalJogaAdvanced = new PersonalTrainingSession("PersonalJogaAdv",
                JOGA, Optional.empty(), LocalDateTime.of(2024, 05, 1, 14, 0), ADVANCED);

        training.addTrainingSession(groupJoga);
        training.addTrainingSession(personalJogaAdvanced);
        club1.addTraining(training);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CriteriaBuilder()
                    .dateRange(LocalDate.of(2024, 5, 31), LocalDate.of(2024, 5, 1));
        });
        assertEquals("Start is after end", exception.getMessage());
    }

    @Test
    void testBuyMembershipAlreadyOwnedThrowsException() throws TransactionException {
        Member member = new Member("Member", true);
        SportsClub club1 = new SportsClub("Club1");

        member.getMemberWallet().setAmount(BigDecimal.valueOf(130));

        FullMembership.Builder fullBuilder = new FullMembership.Builder();
        FullMembership fullMembership = fullBuilder
                .withClub(club1)
                .build();

        RegularMembership.Builder regularBuilder = new RegularMembership.Builder();
        RegularMembership regularMembership = regularBuilder
                .withClub(club1)
                .build();

        member.buyMembership(club1, fullMembership);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            new Transaction(member, club1, regularMembership)
                    .startTransactionBuyMembership(member, club1, regularMembership);
        });
        assertEquals(TransactionException.TransactionReason.ALREADY_HAS_MEMBERSHIP, exception.getReason());
    }

    @Test
    void testBuyMembershipNotEnoughFundsThrowsException() throws TransactionException {
        Member member = new Member("Member", true);
        SportsClub club1 = new SportsClub("Club1");

        member.getMemberWallet().setAmount(BigDecimal.valueOf(20));

        FullMembership.Builder fullBuilder = new FullMembership.Builder();
        FullMembership fullMembership = fullBuilder
                .withClub(club1)
                .build();

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            new Transaction(member, club1, fullMembership)
                    .startTransactionBuyMembership(member, club1, fullMembership);
        });
        assertEquals(TransactionException.TransactionReason.NOT_ENOUGH_FUNDS, exception.getReason());
    }


    @Test
    void testCorrectDiscountNewMembership() throws TransactionException {
        Member member = new Member("Member", true);
        member.getMemberWallet().setAmount(BigDecimal.valueOf(100));
        SportsClub club2 = testClubs.get(2);

        BonusPointsStrategy bpStrat = new BonusPointsStrategy(club2);
        club2.setDiscountStrategy(bpStrat);

        member.getBonusPoints().addBonusPoints(club2, 24);

        FullMembership.Builder fullBuilder = new FullMembership.Builder();
        FullMembership fullMembership = fullBuilder
                .withClub(club2)
                .build();

        member.buyMembership(club2, fullMembership);
        // Full membership normal cost: 60
        // Discount with 24 points for club2: 3,33
        // (60 * 3,33) / 100 = 1,998
        // 60 - 1,998 = 58,002
        // 100 - 58,002 = 41.998
        assertEquals(new BigDecimal("41.998"), member.getMemberWallet().getAmount());
    }

    @Test
    void testNewMembershipNoStrategy() throws TransactionException {
        Member member = new Member("Member", true);
        member.getMemberWallet().setAmount(BigDecimal.valueOf(100));
        SportsClub club2 = testClubs.get(2);
        FullMembership.Builder fullBuilder = new FullMembership.Builder();
        FullMembership fullMembership = fullBuilder
                .withClub(club2)
                .build();
        member.buyMembership(club2, fullMembership);
        assertEquals(new BigDecimal("40.0"), member.getMemberWallet().getAmount());

    }

}
