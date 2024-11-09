package ee.taltech.iti0202.sportsclub.training;

import ee.taltech.iti0202.sportsclub.enums.TrainingType;
import ee.taltech.iti0202.sportsclub.users.Trainer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ee.taltech.iti0202.sportsclub.enums.Sports.JUDO;
import static ee.taltech.iti0202.sportsclub.enums.TrainingSessionLevels.BEGINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTrainingSessionTest {

    @Test
    public void testConstructorHasDefaultPrice() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(BigDecimal.valueOf(5), webJudo.getDefaultPrice());
    }

    @Test
    public void testConstructorHasTime() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(LocalTime.of(14, 0), webJudo.getStartTime().toLocalTime());
    }

    @Test
    public void testGetTypeCorrectType() {
        Trainer judoTrainer = new Trainer("Judoka", List.of(JUDO));
        WebTrainingSession webJudo = new WebTrainingSession("PersonalJudo",
                JUDO, Optional.of(judoTrainer), LocalDateTime.of(14, 02, 24, 14, 0), BEGINNER);
        assertEquals(TrainingType.WEB_TRAINING, webJudo.getType());

    }

}
