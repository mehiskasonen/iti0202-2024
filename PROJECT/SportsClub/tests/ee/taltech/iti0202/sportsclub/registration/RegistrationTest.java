package ee.taltech.iti0202.sportsclub.registration;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.RegularMembership;
import ee.taltech.iti0202.sportsclub.training.GroupTrainingSession;
import ee.taltech.iti0202.sportsclub.training.PersonalTrainingSession;
import ee.taltech.iti0202.sportsclub.training.Training;
import ee.taltech.iti0202.sportsclub.training.TrainingSession;
import ee.taltech.iti0202.sportsclub.training.WebTrainingSession;
import ee.taltech.iti0202.sportsclub.users.Member;
import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.BASKETBALL;
import static ee.taltech.iti0202.sportsclub.enums.Sports.JOGA;
import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.ADVANCED;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationTest {

    @Test
    void testRegisterMemberToSessionAddsMemberToParticipators() throws RegistrationException, TransactionException {
        SportsClub club = new SportsClub("Club1");
        Trainer generalTrainer = new Trainer("Judoka", List.of(JUDO, JOGA, BASKETBALL));
        club.addTrainer(generalTrainer);

        Member member = new Member("Member", true);
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        club.addMember(member);
        member.addClub(club);

        Training training1 = new Training("Judo", JUDO, generalTrainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(generalTrainer));
        judoSession.setMaxParticipants(2);

        club.addTraining(training1);
        training1.addTrainingSession(judoSession);

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        member.buyMembership(club, membership);

        member.registerToTrainingSession(judoSession);
        assertTrue(judoSession.getParticipants().contains(member));
    }

    @Test
    void testRegisterMemberToSessionAddsSessionToMembersListOfTrainingSessions()
            throws RegistrationException, TransactionException {
        SportsClub club = new SportsClub("Club1");
        Trainer generalTrainer = new Trainer("Judoka", List.of(JUDO, JOGA, BASKETBALL));
        club.addTrainer(generalTrainer);

        Member member = new Member("Member", true);
        club.addMember(member);
        member.addClub(club);
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        Training training1 = new Training("Judo", JUDO, generalTrainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(generalTrainer));
        judoSession.setMaxParticipants(2);

        club.addTraining(training1);
        training1.addTrainingSession(judoSession);

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        member.buyMembership(club, membership);
        member.registerToTrainingSession(judoSession);
        assertTrue(member.getRegisteredTrainingSessions().contains(judoSession));
    }

    @Test
    void testRegisterFullMemberBonusPointsCorrectDiscount() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(100));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));

        //Register to first session, get 20 bonus points.
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
        member.registerToTrainingSession(personalJudo); //first is free
        //---------------------------------------------

        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        training.addTrainingSession(webJudo);
        member.registerToTrainingSession(webJudo, 2); //full member 2x10 cents  5 - 0.2 = 4.8
        BigDecimal expected = new BigDecimal("35.2");
        assertEquals(expected, member.getMemberWallet().getAmount()); //100 - 64.8 = 35.2

    }

    @Test
    void testRegisterRegularMemberBonusPointsCorrectDiscount() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(100));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership); //100 - 40 = 60
        member.registerToTrainingSession(personalJudo); //60 - 30 = 30
        //---------------------------------------------

        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        training.addTrainingSession(webJudo);
        member.registerToTrainingSession(webJudo, 2); //regular member 2x5 cents  5 - 0.1 = 4.9
        BigDecimal expected = new BigDecimal("25.1");
        assertEquals(expected, member.getMemberWallet().getAmount()); //30 - 4.9 = 25.1

    }

    @Test
    void testRegisterFullMemberSecondPersonalTrainingCorrectDiscount()
            throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        Trainer jogaTrainer = new Trainer("Guru", List.of(JOGA));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership); //150 - 60 = 90
        member.registerToTrainingSession(personalJudo); //first is free
        //---------------------------------------------

        PersonalTrainingSession personalJoga = new PersonalTrainingSession("PersonalJoga",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        training.addTrainingSession(personalJoga);

        member.registerToTrainingSession(personalJoga, 2); //regular member 2x10 cents  20 - 0.2 = 19.8
        BigDecimal expected = new BigDecimal("70.2");
        assertEquals(expected, member.getMemberWallet().getAmount()); //90 - 19.8 = 70.2
    }

    @Test
    void testRegisterFullMemberSecondPersonalTrainingCorrectBonusPoints()
            throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        Trainer jogaTrainer = new Trainer("Guru", List.of(JOGA));

        //Register to first session, get 20 bonus points.
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
        //---------------------------------------------

        PersonalTrainingSession personalJoga = new PersonalTrainingSession("PersonalJoga",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        training.addTrainingSession(personalJoga);

        member.registerToTrainingSession(personalJoga, 2); // 20 - 2 + 20
        Integer expected2 = 38;
        assertEquals(expected2, member.getBonusPoints().getPoints(club));
    }

    @Test
    void testRegisterRegularMemberUseNegativeBonusPoints() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership); //150 - 60 = 90
        member.registerToTrainingSession(personalJudo); //first is free
        //---------------------------------------------

        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        training.addTrainingSession(webJudo);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, webJudo, -2).registerMemberToSession(member, webJudo);
        });
        assertEquals(RegistrationException.Reason.USED_NEGATIVE_BONUS, exception.getReason());
    }

    @Test
    void testRegisterRegularMemberTooManyBonusPointsUSed() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership); //150 - 60 = 90
        member.registerToTrainingSession(personalJudo); //first is free
        //---------------------------------------------

        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        training.addTrainingSession(webJudo);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, webJudo, 25).registerMemberToSession(member, webJudo);
        });
        assertEquals(RegistrationException.Reason.NOT_ENOUGH_BONUS_POINTS, exception.getReason());
    }

    @Test
    void testRegisterUsingBonusPointsWrongSessionType() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership); //150 - 60 = 90
        member.registerToTrainingSession(personalJudo); //first is free
        //---------------------------------------------
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        training.addTrainingSession(groupJudo);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, groupJudo, 2).registerMemberToSession(member, groupJudo);
        });
        assertEquals(RegistrationException.Reason.BONUS_POINT_SESSION_MISMATCH, exception.getReason());
    }

    @Test
    void testRegularMemberToAdvancedSessionThrowException() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), ADVANCED);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership regularmembership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, regularmembership); //150 - 60 = 90
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, personalJudo).registerMemberToSession(member, personalJudo);
        });
        assertEquals(RegistrationException.Reason.MEMBERSHIP_SESSION_LEVEL_MISMATCH, exception.getReason());
    }

    @Test
    void testDeregisterMemberFromSessionSuccessful() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        club.addTraining(training);

        member.buyMembership(club, membership); //150 - 60 = 90
        member.registerToTrainingSession(personalJudo); //first is free
        assertEquals(personalJudo, member.getRegisteredTrainingSessions().get(0));

        member.deregisterFromTrainingSession(personalJudo);
        assertTrue(member.getRegisteredTrainingSessions().isEmpty());
    }

    @Test
    void testDeregisterMemberFromSessionNotRegistered() throws TransactionException, RegistrationException {
        Member member = new Member("Member1", false);
        SportsClub club = new SportsClub("club1");
        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        Trainer jogaTrainer = new Trainer("Guru", List.of(JOGA));

        //Register to first session, get 20 bonus points.
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        RegularMembership.Builder builder = new RegularMembership.Builder();
        RegularMembership membership = builder
                .withClub(club)
                .build();

        PersonalTrainingSession personalJoga = new PersonalTrainingSession("PersonalJoga",
                JOGA, Optional.of(jogaTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);

        Training training = new Training("Judo training", JUDO, judoTrainer);
        training.addTrainingSession(personalJudo);
        training.addTrainingSession(personalJoga);
        club.addTraining(training);

        member.buyMembership(club, membership);
        member.registerToTrainingSession(personalJudo);
        assertEquals(personalJudo, member.getRegisteredTrainingSessions().get(0));

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            new Registration(member, personalJoga).deregisterMemberFromSession(member, personalJoga);
        });
        assertEquals(RegistrationException.Reason.MEMBER_NOT_REGISTERED, exception.getReason());
    }


}
