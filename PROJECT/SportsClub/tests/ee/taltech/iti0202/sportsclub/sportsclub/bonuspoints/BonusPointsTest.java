package ee.taltech.iti0202.sportsclub.sportsclub.bonuspoints;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.RegularMembership;
import ee.taltech.iti0202.sportsclub.training.GroupTrainingSession;
import ee.taltech.iti0202.sportsclub.training.PersonalTrainingSession;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.WebTrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;
import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusPointsTest {

    @Test
    void addBonusPoints() {
        int initialPoints = 10;
        SportsClub club = new SportsClub("club1");
        HashMap<SportsClub, Integer> bonusPointsMap = new HashMap<>();
        bonusPointsMap.put(club, initialPoints);
        BonusPoints pointsSystem = new BonusPoints(bonusPointsMap);
        pointsSystem.addBonusPoints(club, 15);
        assertEquals(25, pointsSystem.getPoints(club));
    }

    @Test
    void subtractBonusPoints() {
        int initialPoints = 10;
        SportsClub club = new SportsClub("club1");
        HashMap<SportsClub, Integer> bonusPointsMap = new HashMap<>();
        bonusPointsMap.put(club, initialPoints);
        BonusPoints pointsSystem = new BonusPoints(bonusPointsMap);
        pointsSystem.subtractBonusPoints(club, 7);
        assertEquals(3, pointsSystem.getPoints(club));
    }

    @Test
    void getPoints() {
        int initialPoints = 10;
        SportsClub club = new SportsClub("club1");
        HashMap<SportsClub, Integer> bonusPointsMap = new HashMap<>();
        bonusPointsMap.put(club, initialPoints);
        BonusPoints pointsSystem = new BonusPoints(bonusPointsMap);
        assertEquals(10, pointsSystem.getPoints(club));
    }

    @Test
    void testPersonalTrainingSessionRegistrationFullMemberGetsCorrectBonus()
            throws RegistrationException, TransactionException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(70));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership);
        member.registerToTrainingSession(personalJudo);
        assertEquals(20, member.getBonusPoints().getPoints(club));
    }

    @Test
    void testPersonalTrainingSessionRegistrationRegularMemberGetsCorrectBonus()
            throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(70));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership);
        member.registerToTrainingSession(personalJudo);
        assertEquals(10, member.getBonusPoints().getPoints(club));
    }

    @Test
    void testGroupTrainingSessionRegistrationFullMemberGetsCorrectBonus()
            throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(70));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(groupJudo);
        club.addTraining(training);

        member.buyMembership(club, membership);
        member.registerToTrainingSession(groupJudo);
        assertEquals(4, member.getBonusPoints().getPoints(club));
    }

    @Test
    void testGroupTrainingSessionRegistrationRegularMemberGetsCorrectBonus()
            throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(70));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(groupJudo);
        club.addTraining(training);

        member.buyMembership(club, membership);
        member.registerToTrainingSession(groupJudo);
        assertEquals(2, member.getBonusPoints().getPoints(club));
    }

    @Test
    void testWebTrainingSessionRegistrationFullMemberGetsCorrectBonus()
            throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(70));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(webJudo);
        club.addTraining(training);

        member.buyMembership(club, membership);
        member.registerToTrainingSession(webJudo);
        assertEquals(8, member.getBonusPoints().getPoints(club));
    }

    @Test
    void testWebTrainingSessionRegistrationRegularMemberGetsCorrectBonus()
            throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(70));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(webJudo);
        club.addTraining(training);

        member.buyMembership(club, membership);
        member.registerToTrainingSession(webJudo);
        assertEquals(4, member.getBonusPoints().getPoints(club));
    }

}
