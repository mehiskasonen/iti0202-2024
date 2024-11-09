package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.RUNNING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingTest {

    @Test
    void testGetName() {
        Trainer trainer = new Trainer("Trainer", List.of(RUNNING));
        Training training = new Training("testTraining", RUNNING, trainer);
        assertEquals("testTraining", training.getName());
    }

    @Test
    void testGetTrainer() {
        Trainer trainer = new Trainer("Trainer", List.of(RUNNING));
        Training training = new Training("testTraining", RUNNING, trainer);
        assertEquals(trainer, training.getTrainer());
    }

    @Test
    void testGetTrainingSessionList() {
        Trainer trainer = new Trainer("Trainer", List.of(RUNNING));
        Training training = new Training("testTraining", RUNNING, trainer);
        TrainingSession session1 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        TrainingSession session2 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        TrainingSession session3 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        training.addTrainingSession(session2);
        training.addTrainingSession(session3);
        training.addTrainingSession(session1);
        List<TrainingSession> expected = List.of(session2, session3, session1);
        assertEquals(expected, training.getTrainingSessionList());

    }

    @Test
    void testAddTrainingSession() {
        Trainer trainer = new Trainer("Trainer", List.of(RUNNING));
        Training training = new Training("testTraining", RUNNING, trainer);
        TrainingSession session1 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        TrainingSession session2 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        TrainingSession session3 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        training.addTrainingSession(session2);
        training.addTrainingSession(session3);
        training.addTrainingSession(session1);
        List<TrainingSession> expected = List.of(session2, session3, session1);
        assertTrue(training.getTrainingSessionList().containsAll(expected));
    }

    @Test
    void testAddTrainingSessionAlreadyExists() {
        Trainer trainer = new Trainer("Trainer", List.of(RUNNING));
        Training training = new Training("testTraining", RUNNING, trainer);
        TrainingSession session1 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        TrainingSession session2 = new TrainingSession("Session1", RUNNING, Optional.of(trainer));
        training.addTrainingSession(session1);
        training.addTrainingSession(session2);
        training.addTrainingSession(session2);
        Assertions.assertFalse(training.addTrainingSession(session2));
    }
}
