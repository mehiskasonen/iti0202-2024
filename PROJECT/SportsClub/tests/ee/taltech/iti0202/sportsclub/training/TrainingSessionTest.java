package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels;
import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.exceptions.AssignTrainerException;
import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import ee.taltech.iti0202.sportsclub.exceptions.TransactionException;
import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.sportsclub.membership.FullMembership;
import ee.taltech.iti0202.sportsclub.users.Member;
import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.BASKETBALL;
import static ee.taltech.iti0202.sportsclub.enums.Sports.JOGA;
import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrainingSessionTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    void setAndGetMaxParticipants(int maxParticipants) {
        Trainer trainer = new Trainer("Trainer", List.of(Sports.JUDO));
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(maxParticipants);
        assertEquals(maxParticipants, judoSession.getMaxParticipants());
    }

    @Test
    void testSetTrainerToSessionIsProficientSuccessful() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Optional<Trainer> replacementTrainer = Optional.of(new Trainer("Replacement Trainer",
                List.of(JUDO, BASKETBALL)));
        Training training = new Training("Judo Training", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        club.addTraining(training);
        training.addTrainingSession(judoSession);
        judoSession.setTrainerToSession(replacementTrainer);
        assertEquals(replacementTrainer, judoSession.getTrainer());
    }

    @Test
    void addTrainer() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Trainer newTrainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        club.addTrainer(trainer);
        club.addTrainer(newTrainer);
    }

    @Test
    void canAssignTrainerTrainerAlreadyAssigned() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Trainer replacementTrainer = new Trainer("Replacement Trainer", List.of(JUDO, BASKETBALL));
        Training training = new Training("Judo Training", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        club.addTraining(training);
        training.addTrainingSession(judoSession);

        AssignTrainerException exception = assertThrows(AssignTrainerException.class, () -> {
            judoSession.canAssignTrainer(replacementTrainer);
        });
        assertEquals(AssignTrainerException.AssignTrainerReason.TRAINER_ALREADY_ASSIGNED, exception.getReason());
    }

    @Test
    void canAssignTrainerTrainerNotQualified() {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Trainer replacementTrainer = new Trainer("Replacement Trainer", List.of(BASKETBALL));
        Training training = new Training("Judo Training", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.empty());
        club.addTraining(training);
        training.addTrainingSession(judoSession);

        AssignTrainerException exception = assertThrows(AssignTrainerException.class, () -> {
            judoSession.canAssignTrainer(replacementTrainer);
        });
        assertEquals(AssignTrainerException.AssignTrainerReason.TRAINER_NOT_QUALIFIED, exception.getReason());
    }

    @Test
    void canAssignTrainerSuccessful() throws AssignTrainerException {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Training training = new Training("Judo Training", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.empty());
        club.addTraining(training);
        training.addTrainingSession(judoSession);
        judoSession.canAssignTrainer(trainer);
    }

    @Test
    void testGetParticipantsRightSize() throws RegistrationException, TransactionException {
        SportsClub club = new SportsClub("Club1");
        Trainer trainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        Training training = new Training("Judo Training", JUDO, trainer);
        TrainingSession judoSession = new TrainingSession("Judo", JUDO, Optional.of(trainer));
        judoSession.setMaxParticipants(3);
        club.addTraining(training);
        training.addTrainingSession(judoSession);
        Member member = new Member("Member1", true);
        Member member2 = new Member("Member1", true);

        club.addMember(member);
        member.addClub(club);
        club.addMember(member2);
        member2.addClub(club);

        member.getMemberWallet().setAmount(BigDecimal.valueOf(150));
        member2.getMemberWallet().setAmount(BigDecimal.valueOf(150));

        FullMembership.Builder builder = new FullMembership.Builder();
        FullMembership membership = builder
                .withClub(club)
                .build();

        member.buyMembership(club, membership);
        member2.buyMembership(club, membership);

        member.registerToTrainingSession(judoSession);
        member2.registerToTrainingSession(judoSession);

        assertEquals(2, judoSession.getParticipants().size());
    }

    @Test
    void testGetStartTime() {
        Trainer judoTrainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(LocalTime.of(14, 0), groupJudo.getStartTime().toLocalTime());
    }

    @Test
    void getType() {
        Trainer judoTrainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(TrainingType.GROUP_TRAINING, groupJudo.getType());
    }

    @Test
    void getName() {
        Trainer judoTrainer = new Trainer("Trainer", List.of(JUDO, JOGA));
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals("PersonalJudo", groupJudo.getName());
    }

    @Test
    void testCompareByTrainingSessionLevels() {
        PersonalTrainingSession beginnerSession = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.empty(), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        PersonalTrainingSession beginnerSession2 = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.empty(), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        PersonalTrainingSession intermediateSession = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.empty(), LocalDateTime.of(14, 02, 24, 14, 0), INTERMEDIATE);
        PersonalTrainingSession advancedSession = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.empty(), LocalDateTime.of(14, 02, 24, 14, 0), ADVANCED);

        assertEquals(-1, TrainingSession.compareByTrainingSessionLevel(beginnerSession, intermediateSession)); // beginner < intermediate
        assertEquals(-1, TrainingSession.compareByTrainingSessionLevel(beginnerSession, advancedSession));     // beginner < advanced
        assertEquals(-1, TrainingSession.compareByTrainingSessionLevel(intermediateSession, advancedSession)); // intermediate < advanced

        assertEquals(0, TrainingSession.compareByTrainingSessionLevel(beginnerSession, beginnerSession2));
    }

}
