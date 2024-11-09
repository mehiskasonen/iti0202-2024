package ee.taltech.iti0202.sportsclub.users;

import ee.taltech.iti0202.sportsclub.enums.Sports;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrainerTest {

    @Test
    void testConstructorNoSports() {
        String name = "Trainer";
        List<Enum<Sports>> trainsSports = new ArrayList<>();
        Trainer trainer = new Trainer(name, trainsSports);
        assertNotNull(trainer);
        assertEquals(name, trainer.getName());
    }

    @Test
    void testGetTrainsSportsHasSports() {
        String name = "Trainer";
        List<Enum<Sports>> trainsSports = Arrays.asList(Sports.BASKETBALL, Sports.TENNIS);
        Trainer trainer = new Trainer(name, trainsSports);
        assertEquals(trainsSports, trainer.getTrainsSports());
    }
}
