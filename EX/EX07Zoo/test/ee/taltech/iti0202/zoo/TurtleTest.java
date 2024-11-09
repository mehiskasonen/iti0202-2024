package ee.taltech.iti0202.zoo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TurtleTest {

    @Test
    void testGetSound() {
        Turtle turtle = new Turtle("Donatello");
        assertEquals("", turtle.getSound());
    }
}
