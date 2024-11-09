package ee.taltech.iti0202.zoo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MonkeyTest {

    @Test
    void testGetSoundHungry() {
        Animal monkey2 = new Monkey("Monkey2");
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey2);
        zoo.nextDay();
        zoo.nextDay();
        assertEquals("BANANA", monkey2.getSound());
    }

    @Test
    void testGetSoundNotHungry() {
        Animal monkey2 = new Monkey("Monkey2");
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey2);
        assertNotEquals("BANANA", monkey2.getSound());
    }

    @Test
    void testFeed() {
    }
}
