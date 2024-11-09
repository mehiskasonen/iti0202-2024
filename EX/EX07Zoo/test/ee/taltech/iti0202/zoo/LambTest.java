package ee.taltech.iti0202.zoo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LambTest {

    @Test
    void testGetSound() {
        Lamb lamb = new Lamb("Lamb1");
        assertEquals("Mää", lamb.getSound());
    }

    @Test
    void testFeed() {
        Lamb lamb = new Lamb("Lamb1");
        assertEquals("Mää", lamb.getSound());
    }

    @Test
    void testLambDoesNotGetHungrier() {
        Lamb lamb = new Lamb("Lamb1");
        Zoo zoo = new Zoo("Zoo");
        zoo.addAnimal(lamb);
        zoo.nextDay();
        assertEquals(2, lamb.getsHungryIn());
    }

}
