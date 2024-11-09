package ee.taltech.iti0202.sportsclub.sportsclub;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.*;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SportsClubTest {

    @Test
    void testGetMembers() {
        SportsClub club = new SportsClub("Club1");
        Member member = new Member("Member", true);
        club.addMember(member);
        assertTrue(club.getMembers().contains(member));
    }

    @Test
    void testGetTrainers() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        club.addTrainer(trainer);
        assertTrue(club.getTrainers().contains(trainer));
    }

    @Test
    void testAddTrainingClubHasRightTraining() {
        SportsClub club = new SportsClub("Club1");
        Trainer basketballTrainer = new Trainer("Ball Basket", List.of(BASKETBALL));
        Training training = new Training("Basketball", BASKETBALL, basketballTrainer);
        club.addTraining(training);
        assertTrue(club.getTrainings().contains(training));

    }

    @Test
    void testAddTrainerClubHasRightTrainer() {
        SportsClub club = new SportsClub("Club1");
        Trainer basketballTrainer = new Trainer("Ball Basket", List.of(BASKETBALL));
        club.addTrainer(basketballTrainer);
        assertTrue(club.getTrainers().contains(basketballTrainer));
    }

    @Test
    void testGetTrainingsClubHasRightTrainings() {
        SportsClub club = new SportsClub("Club1");
        Trainer basketballTrainer = new Trainer("Ball Basket", List.of(BASKETBALL));
        Trainer generalTrainer = new Trainer("Ball Basket", List.of(HIIT));
        Training training = new Training("Basketball", BASKETBALL, basketballTrainer);
        Training training2 = new Training("HIIT", HIIT, generalTrainer);
        club.addTraining(training);
        club.addTraining(training2);
        List<Training> expected = List.of(training, training2);
        assertEquals(expected, club.getTrainings());
    }

    @Test
    void testListTrainingsSameNrParticipantsDifferentNrSessions() throws RegistrationException {
        SportsClub club = new SportsClub("Club1");
        Trainer generalTrainer = new Trainer("General Trainer", List.of(JUDO, JOGA, BASKETBALL));
        Training training1 = new Training("Judo", JUDO, generalTrainer);
        Training training2 = new Training("Joga", JOGA, generalTrainer);
        Training training3 = new Training("Basketball", BASKETBALL, generalTrainer);

        club.addTraining(training2);
        club.addTraining(training1);
        club.addTraining(training3);

        //Judo sessions
        TrainingSession judoSessionMonday = new TrainingSession("JudoM", JUDO, Optional.of(generalTrainer));
        TrainingSession judoSessionWednesday = new TrainingSession("JudoW", JUDO, Optional.of(generalTrainer));
        TrainingSession judoSessionFriday = new TrainingSession("JudoF", JUDO, Optional.of(generalTrainer));
        judoSessionMonday.setMaxParticipants(10);
        judoSessionWednesday.setMaxParticipants(10);
        judoSessionFriday.setMaxParticipants(10);

        //Joga sessions
        TrainingSession jogaSessionMonday = new TrainingSession("JogaM", JOGA, Optional.of(generalTrainer));
        TrainingSession jogaSessionWednesday = new TrainingSession("JogaW", JOGA, Optional.of(generalTrainer));
        jogaSessionMonday.setMaxParticipants(10);
        jogaSessionWednesday.setMaxParticipants(10);


        //Basketball sessions
        TrainingSession basketballSessionMonday = new TrainingSession("BasketballM", JUDO, Optional.of(generalTrainer));
        basketballSessionMonday.setMaxParticipants(10);

        training1.addTrainingSession(judoSessionMonday);
        training1.addTrainingSession(judoSessionWednesday);
        training1.addTrainingSession(judoSessionFriday);
        training2.addTrainingSession(jogaSessionMonday);
        training2.addTrainingSession(jogaSessionWednesday);
        training3.addTrainingSession(basketballSessionMonday);

        //Normal[2, 1, 3]
        //Since participants are equal (0), listing is based on nr of sessions.
        //Expected[1, 2, 3]
        List<Training> expected = List.of(training1, training2, training3);
        assertEquals(expected, club.listTrainings());
    }

    @Test
    void testListTrainingsDifferentNrParticipantsSameNrSessions() throws RegistrationException, TransactionException {
        SportsClub club = new SportsClub("Club1");
        Trainer generalTrainer = new Trainer("Judoka", List.of(JUDO, JOGA, BASKETBALL));
        Training training1 = new Training("Judo", JUDO, generalTrainer);
        Training training2 = new Training("Joga", JOGA, generalTrainer);
        Training training3 = new Training("Basketball", BASKETBALL, generalTrainer);

        club.addTraining(training2);
        club.addTraining(training1);
        club.addTraining(training3);

        //Judo sessions
        TrainingSession judoSessionMonday = new TrainingSession("JudoM", JUDO, Optional.of(generalTrainer));
        TrainingSession judoSessionWednesday = new TrainingSession("JudoW", JUDO, Optional.of(generalTrainer));
        TrainingSession judoSessionFriday = new TrainingSession("JudoF", JUDO, Optional.of(generalTrainer));
        judoSessionMonday.setMaxParticipants(10);
        judoSessionWednesday.setMaxParticipants(10);
        judoSessionFriday.setMaxParticipants(10);

        //Joga sessions
        TrainingSession jogaSessionMonday = new TrainingSession("JogaM", JOGA, Optional.of(generalTrainer));
        TrainingSession jogaSessionWednesday = new TrainingSession("JogaW", JOGA, Optional.of(generalTrainer));
        TrainingSession jogaSessionFriday = new TrainingSession("JogaF", JOGA, Optional.of(generalTrainer));
        jogaSessionMonday.setMaxParticipants(10);
        jogaSessionWednesday.setMaxParticipants(10);
        jogaSessionFriday.setMaxParticipants(10);


        //Basketball sessions
        TrainingSession basketballSessionMonday = new TrainingSession("BasketballM",
                JUDO, Optional.of(generalTrainer));
        TrainingSession basketballSessionWednesday = new TrainingSession("BasketballW",
                JUDO, Optional.of(generalTrainer));
        TrainingSession basketballSessionFriday = new TrainingSession("BasketballF",
                JUDO, Optional.of(generalTrainer));
        basketballSessionMonday.setMaxParticipants(10);
        basketballSessionWednesday.setMaxParticipants(10);
        basketballSessionFriday.setMaxParticipants(10);

        training1.addTrainingSession(judoSessionMonday);
        training1.addTrainingSession(judoSessionWednesday);
        training1.addTrainingSession(judoSessionFriday);
        training2.addTrainingSession(jogaSessionMonday);
        training2.addTrainingSession(jogaSessionWednesday);
        training2.addTrainingSession(jogaSessionFriday);
        training3.addTrainingSession(basketballSessionMonday);
        training3.addTrainingSession(basketballSessionWednesday);
        training3.addTrainingSession(basketballSessionFriday);

        Member member1 = new Member("Member1", true);
        club.addMember(member1);
        member1.addClub(club);

        member1.getMemberWallet().setAmount(BigDecimal.valueOf(1000));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        member1.buyMembership(club, membership);

        member1.registerToTrainingSession(judoSessionMonday);
        member1.registerToTrainingSession(judoSessionWednesday);

        member1.registerToTrainingSession(jogaSessionMonday);

        member1.registerToTrainingSession(basketballSessionMonday);
        member1.registerToTrainingSession(basketballSessionWednesday);
        member1.registerToTrainingSession(basketballSessionFriday);

        //Normal[2, 1, 3]
        //expected[3, 1, 2]
        List<Training> expected = List.of(training3, training1, training2);
        assertEquals(expected, club.listTrainings());
    }

    @Test
    void testCanRegisterSuccessfulRegistration() throws RegistrationException, TransactionException {
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

        assertTrue(club.getTrainings().contains(training1));
        assertTrue(training1.getTrainingSessionList().contains(judoSession));
        assertTrue(member.getMemberClubs().contains(club));

        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        member.buyMembership(club, membership);

        assertTrue(member.registerToTrainingSession(judoSession));
    }

    @Test
    void testGetScheduleContainsRightTraining() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Training training = new Training("Judo Training", JUDO, trainer);
        Training training2 = new Training("Joga Training", JOGA, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        TrainingSession jogaSession = new TrainingSession("Joga", JOGA, Optional.of(trainer));

        club.addTraining(training);
        club.addTraining(training2);
        training.addTrainingSession(judoSession);
        training2.addTrainingSession(jogaSession);

        club.getSchedule().put(training, List.of(LocalDateTime.of(2024, 03, 29, 16, 00)));
        club.getSchedule().put(training2, List.of(LocalDateTime.of(2024, 03, 30, 17, 00)));
        assertTrue(club.getSchedule().containsKey(training));
    }

    @Test
    void buildScheduleBuildsCorrectScheduleDuplicateTimesAllowed() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Training training = new Training("Judo Training", JUDO, trainer);
        Training training2 = new Training("Joga Training", JOGA, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        TrainingSession jogaSession = new TrainingSession("Joga", JOGA, Optional.of(trainer));

        club.addTraining(training);
        club.addTraining(training2);
        training.addTrainingSession(judoSession);
        training2.addTrainingSession(jogaSession);

        LocalDateTime dateTime1 = LocalDateTime.of(2024, 03, 29, 16, 00);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 03, 29, 16, 00);
        List<LocalDateTime> times = new ArrayList<>();
        times.add(dateTime1);
        times.add(dateTime2);

        Map<Training, List<LocalDateTime>> expected = new HashMap<>();
        expected.put(training, List.of(dateTime1, dateTime2));
        expected.put(training2, List.of(dateTime1, dateTime2));

        club.buildSchedule(training, times);
        club.buildSchedule(training2, times);
        assertEquals(expected, club.getSchedule());
    }

    @Test
    void testAddMemberAlreadyContainsMember() {
        SportsClub club = new SportsClub("Club1");
        Member member = new Member("Member", true);
        club.addMember(member);
        assertFalse(club.addMember(member));
    }

    @Test
    void testAddTrainerAlreadyContainsTrainer() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        club.addTrainer(trainer);
        assertFalse(club.addTrainer(trainer));
    }

    @Test
    void testAddTrainingAlreadyContainsTraining() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Training training = new Training("Judo Training", JUDO, trainer);
        club.addTraining(training);
        assertFalse(club.addTraining(training));
    }

    @Test
    void testListSessionsCorrectSize() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Training training = new Training("Judo Training", JUDO, trainer);

        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(trainer), LocalDateTime.of(14, 02, 24, 14, 00), BEGINNER);

        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(trainer), LocalDateTime.of(14, 02, 24, 16, 00), BEGINNER);

        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(trainer), LocalDateTime.of(14, 02, 24, 18, 0), BEGINNER);

        club.addTraining(training);
        training.addTrainingSession(groupJudo);
        training.addTrainingSession(personalJudo);
        training.addTrainingSession(webJudo);

        assertEquals(2, club.listSessions(LocalTime.of(15, 00)).size());
    }

    @Test
    void testListSessionsAscendingOrder() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Training training = new Training("Judo Training", JUDO, trainer);

        GroupTrainingSession groupJudo = new GroupTrainingSession("groupJudo",
                JUDO, Optional.of(trainer), LocalDateTime.of(14, 02, 24, 14, 00), BEGINNER);

        PersonalTrainingSession personalJudo = new PersonalTrainingSession("personalJudo",
                JUDO, Optional.of(trainer), LocalDateTime.of(14, 02, 24, 16, 00), BEGINNER);

        WebTrainingSession webJudo = new WebTrainingSession("webJudo",
                JUDO, Optional.of(trainer), LocalDateTime.of(14, 02, 24, 18, 0), BEGINNER);

        club.addTraining(training);
        training.addTrainingSession(groupJudo);
        training.addTrainingSession(webJudo);
        training.addTrainingSession(personalJudo);

        //initial order: groupJudo, webJudo, personalJudo
        //sorted order : personalJudo, webJudo
        Map<TrainingSession, LocalTime> sortedSessions = club.listSessions(LocalTime.of(15, 00));
        TrainingSession firstSession = sortedSessions.keySet().iterator().next();
        assertEquals(personalJudo, firstSession);
    }


    @Test
    void testSortTrainingSessionsByLevelOnly() {
        SportsClub club = new SportsClub("SportClub");
        Training training = new Training("Training", JUDO, new Trainer("Trainer", List.of(JUDO)));

        PersonalTrainingSession beginnerSession = new PersonalTrainingSession("beginnerJudo",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession intermediateSession = new PersonalTrainingSession("intermediateJudo",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 15, 0), INTERMEDIATE);
        PersonalTrainingSession advancedSession = new PersonalTrainingSession("advancedJudo",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 16, 0), ADVANCED);

        training.addTrainingSession(advancedSession);
        training.addTrainingSession(beginnerSession);
        training.addTrainingSession(intermediateSession);
        club.addTraining(training);

        //initial order: advancedJudo, beginnerJudo, intermediateJudo
        List<TrainingSession> sortedSessions = club.sortTrainingSessions();

        assertEquals(BEGINNER, sortedSessions.get(0).getLevel());
        assertEquals(INTERMEDIATE, sortedSessions.get(1).getLevel());
        assertEquals(ADVANCED, sortedSessions.get(2).getLevel());
    }

    @Test
    void testSortTrainingSessionsByTimeOnly() {
        SportsClub club = new SportsClub("SportClub");
        Training training = new Training("Training", JUDO, new Trainer("Trainer", List.of(JUDO)));

        PersonalTrainingSession earlySession = new PersonalTrainingSession("earlySession",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession middleSession = new PersonalTrainingSession("middleSession",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 15, 0), BEGINNER);
        PersonalTrainingSession lateSession = new PersonalTrainingSession("lateSession",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 16, 0), BEGINNER);

        training.addTrainingSession(lateSession);
        training.addTrainingSession(earlySession);
        training.addTrainingSession(middleSession);
        club.addTraining(training);

        //initial order: lateSession, earlySession, middleSession
        List<TrainingSession> sortedSessions = club.sortTrainingSessions();

        assertEquals(earlySession, sortedSessions.get(0));
        assertEquals(middleSession, sortedSessions.get(1));
        assertEquals(lateSession, sortedSessions.get(2));

    }

    @Test
    void testSortTrainingSessionsMultipleElementsSame() {
        SportsClub club = new SportsClub("SportClub");
        Training training = new Training("Training", JUDO, new Trainer("Trainer", List.of(JUDO)));
        Member member1 = new Member("Member1", true);
        Member member2 = new Member("Member2", true);

        //session1(beginner), session2(intermediate, 1), session3(intermediate, 2), session4(advanced, 1, 14:00), session5(advanced, 1, 15:00), session6(advanced, 2, 14:00)
        PersonalTrainingSession session1 = new PersonalTrainingSession("session1",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);

        PersonalTrainingSession session2 = new PersonalTrainingSession("session2",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 15, 0), INTERMEDIATE);
        session2.setMembers(List.of(member1));

        PersonalTrainingSession session3 = new PersonalTrainingSession("session3",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 16, 0), INTERMEDIATE);
        session3.setMembers(List.of(member1, member2));

        PersonalTrainingSession session4 = new PersonalTrainingSession("session3",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 14, 0), ADVANCED);
        session4.setMembers(List.of(member1));

        PersonalTrainingSession session5 = new PersonalTrainingSession("session3",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 15, 0), ADVANCED);
        session5.setMembers(List.of(member1));

        PersonalTrainingSession session6 = new PersonalTrainingSession("session3",
                JUDO, Optional.empty(), LocalDateTime.of(2024, 2, 24, 14, 0), ADVANCED);
        session6.setMembers(List.of(member1, member2));

        training.addTrainingSession(session5);
        training.addTrainingSession(session3);
        training.addTrainingSession(session6);
        training.addTrainingSession(session1);
        training.addTrainingSession(session4);
        training.addTrainingSession(session2);

        club.addTraining(training);

        //initial order: session5, session3, session6, session1, session4, session2
        List<TrainingSession> sortedSessions = club.sortTrainingSessions();

        assertEquals(session1, sortedSessions.get(0));
        assertEquals(session2, sortedSessions.get(1));
        assertEquals(session3, sortedSessions.get(2));
        assertEquals(session4, sortedSessions.get(3));
        assertEquals(session5, sortedSessions.get(4));
        assertEquals(session6, sortedSessions.get(5));
    }

    @Test
    void testSortTrainersBySpecializationCount() {
        SportsClub club = new SportsClub("SportClub");
        Trainer trainer1 = new Trainer("Trainer1", List.of(JUDO));
        Trainer trainer2 = new Trainer("Trainer2", List.of(JUDO, RUNNING));
        Trainer trainer3 = new Trainer("Trainer3", List.of(JUDO, RUNNING, BASKETBALL));

        Training training = new Training("Training", JUDO, trainer1);
        Training training2 = new Training("Training2", RUNNING, trainer2);
        Training training3 = new Training("Training3", BASKETBALL, trainer3);

        PersonalTrainingSession judoSession = new PersonalTrainingSession("judoSession",
                JUDO, Optional.of(trainer1), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession runningSession = new PersonalTrainingSession("runningSession",
                RUNNING, Optional.of(trainer2), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession basketballSession = new PersonalTrainingSession("basketballSession",
                BASKETBALL, Optional.of(trainer3), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);

        training.addTrainingSession(judoSession);
        training2.addTrainingSession(runningSession);
        training3.addTrainingSession(basketballSession);

        club.addTraining(training);
        club.addTraining(training3);
        club.addTraining(training2);

        assertEquals(trainer3, club.sortTrainers().get(0));
        assertEquals(trainer2, club.sortTrainers().get(1));
        assertEquals(trainer1, club.sortTrainers().get(2));
    }

    @Test
    void testSortTrainersByFollowersCount() {
        SportsClub club = new SportsClub("SportClub");
        Member member1 = new Member("Member1", true);
        Member member2 = new Member("Member2", true);
        Member member3 = new Member("Member3", true);

        Trainer trainer1 = new Trainer("Trainer1", List.of(JUDO));
        Trainer trainer2 = new Trainer("Trainer2", List.of(RUNNING));
        Trainer trainer3 = new Trainer("Trainer3", List.of(BASKETBALL));

        Training training1 = new Training("Training", JUDO, trainer1);
        Training training2 = new Training("Training2", RUNNING, trainer2);
        Training training3 = new Training("Training3", BASKETBALL, trainer3);

        PersonalTrainingSession judoSession = new PersonalTrainingSession("judoSession",
                JUDO, Optional.of(trainer1), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession runningSession = new PersonalTrainingSession("runningSession",
                RUNNING, Optional.of(trainer2), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession basketballSession = new PersonalTrainingSession("basketballSession",
                BASKETBALL, Optional.of(trainer3), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);

        training1.addTrainingSession(judoSession);
        training2.addTrainingSession(runningSession);
        training3.addTrainingSession(basketballSession);

        club.addTraining(training1);
        club.addTraining(training3);
        club.addTraining(training2);

        judoSession.setMembers(List.of(member1));
        runningSession.setMembers(List.of(member1, member2));
        basketballSession.setMembers(List.of(member1, member2, member3));

        assertEquals(trainer3, club.sortTrainers().get(0));
        assertEquals(trainer2, club.sortTrainers().get(1));
        assertEquals(trainer1, club.sortTrainers().get(2));
    }

    @Test
    void testSortTrainersMultipleElementsSame() {
        SportsClub club = new SportsClub("SportClub");
        Member member1 = new Member("Member1", true);
        Member member2 = new Member("Member2", true);
        Member member3 = new Member("Member3", true);

        //trainer1(JUDO, 1), trainer2(JUDO, BASKETBALL; 1), trainer3(JUDO, BASKETBALL; 2), trainer4(JUDO, BASKETBALL, RUNNING, 3, D), trainer5(JUDO, BASKETBALL, RUNNING, 3, E)
        Trainer trainer1 = new Trainer("ATrainer1", List.of(JUDO));
        Trainer trainer2 = new Trainer("BTrainer2", List.of(JUDO, BASKETBALL));
        Trainer trainer3 = new Trainer("CTrainer3", List.of(JUDO, BASKETBALL));
        Trainer trainer4 = new Trainer("DTrainer4", List.of(JUDO, BASKETBALL, RUNNING));
        Trainer trainer5 = new Trainer("ETrainer5", List.of(JUDO, BASKETBALL, RUNNING));

        Training training1 = new Training("Training", JUDO, trainer1);
        Training training2 = new Training("Training2", RUNNING, trainer2);
        Training training3 = new Training("Training3", BASKETBALL, trainer3);
        Training training4 = new Training("Training3", BASKETBALL, trainer4);
        Training training5 = new Training("Training3", BASKETBALL, trainer5);

        PersonalTrainingSession judoSession = new PersonalTrainingSession("judoSession",
                JUDO, Optional.of(trainer1), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession runningSession = new PersonalTrainingSession("runningSession",
                RUNNING, Optional.of(trainer2), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession basketballSession = new PersonalTrainingSession("basketballSession",
                BASKETBALL, Optional.of(trainer3), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession basketballSession2 = new PersonalTrainingSession("basketballSession",
                BASKETBALL, Optional.of(trainer4), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);
        PersonalTrainingSession basketballSession3 = new PersonalTrainingSession("basketballSession",
                BASKETBALL, Optional.of(trainer5), LocalDateTime.of(2024, 2, 24, 14, 0), BEGINNER);

        training1.addTrainingSession(judoSession);
        training2.addTrainingSession(runningSession);
        training3.addTrainingSession(basketballSession);
        training4.addTrainingSession(basketballSession2);
        training5.addTrainingSession(basketballSession3);

        club.addTraining(training1);
        club.addTraining(training3);
        club.addTraining(training2);
        club.addTraining(training4);
        club.addTraining(training5);

        judoSession.setMembers(List.of(member1));
        runningSession.setMembers(List.of(member1));
        basketballSession.setMembers(List.of(member1, member2));
        basketballSession2.setMembers(List.of(member1, member2, member3));
        basketballSession3.setMembers(List.of(member1, member2, member3));

        //should be: trainer4, trainer5, trainer3, trainer2, trainer 1
        assertEquals(trainer4, club.sortTrainers().get(0));
        assertEquals(trainer5, club.sortTrainers().get(1));
        assertEquals(trainer3, club.sortTrainers().get(2));
        assertEquals(trainer2, club.sortTrainers().get(3));
        assertEquals(trainer1, club.sortTrainers().get(4));
    }
}
