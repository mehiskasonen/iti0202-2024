package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupTrainingSessionTest {

    @Test
    public void testConstructorHasStartTime() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(LocalTime.of(14, 0), groupJudo.getStartTime().toLocalTime());
    }

    @Test
    public void testConstructorHasCorrectMaxParticipants() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        GroupTrainingSession groupJudo = new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(25, groupJudo.getMaxParticipants());
    }

    @Test
    public void testConstructorInvalidStartTime() throws IllegalArgumentException {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        assertThrows(IllegalArgumentException.class, () -> new GroupTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 16, 0), BEGINNER));
    }

}
