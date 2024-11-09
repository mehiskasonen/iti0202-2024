package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonalTrainingSessionTest {

    @Test
    public void testConstructorHasTime() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(LocalTime.of(14, 0), personalJudo.getStartTime().toLocalTime());
    }

    @Test
    public void testConstructorHasDefaultPrice() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(BigDecimal.valueOf(30.0), personalJudo.getDefaultPrice());
    }

    @Test
    public void testConstructorHasCorrectParticipantAmount() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        PersonalTrainingSession personalJudo = new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(1, personalJudo.getMaxParticipants());
    }

    @Test
    public void testConstructorInvalidStartTime() throws IllegalArgumentException {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        assertThrows(IllegalArgumentException.class, () -> new PersonalTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 19, 0), BEGINNER));
    }

}
