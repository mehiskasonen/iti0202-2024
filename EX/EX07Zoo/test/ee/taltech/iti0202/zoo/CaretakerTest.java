package ee.taltech.iti0202.zoo;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CaretakerTest {


    @Test
    void testFeed() {
        Zoo zoo = new Zoo("Zoo");
        Caretaker caretaker = new Caretaker("Caretaker1",
                Set.of(AnimalType.MAMMAL));
        Animal hungryAnimal = new Animal("Animal1",
                "Sound1",
                1,
                AnimalType.MAMMAL);
        zoo.addCaretaker(caretaker);
        zoo.addAnimal(hungryAnimal);
        zoo.nextDay();
        caretaker.feedAnimals(zoo);
        assertEquals(1, hungryAnimal.getsHungryIn());
    }

    @Test
    void testFeedAllHungryAnimalsCanBeFed() {
        Zoo zoo = new Zoo("Zoo");
        Caretaker caretaker = new Caretaker("Caretaker1",
                Set.of(AnimalType.MAMMAL, AnimalType.REPTILE, AnimalType.FISH));
        Animal hungryAnimal1 = new Animal("Animal1",
                "Sound1",
                1,
                AnimalType.MAMMAL);
        Animal hungryAnimal2 = new Animal("Animal1",
                "Sound2",
                1,
                AnimalType.REPTILE);
        Animal hungryAnimal3 = new Animal("Animal1",
                "Sound3",
                1,
                AnimalType.FISH);
        zoo.addCaretaker(caretaker);
        zoo.addAnimal(hungryAnimal1);
        zoo.addAnimal(hungryAnimal2);
        zoo.addAnimal(hungryAnimal3);
        zoo.nextDay();
        caretaker.feedAnimals(zoo);
        assertEquals(0, zoo.getUnfedAnimals(zoo.getAnimals()).size());
    }

    @Test
    void testFeedNoAnimalsToFeed() {
        Zoo zoo = new Zoo("Zoo");
        Caretaker caretaker = new Caretaker("Caretaker1",
                Set.of(AnimalType.MAMMAL, AnimalType.REPTILE, AnimalType.FISH));
        Animal hungryAnimal1 = new Animal("Animal1",
                "Sound1",
                1,
                AnimalType.MAMMAL);
        Animal hungryAnimal2 = new Animal("Animal1",
                "Sound2",
                1,
                AnimalType.REPTILE);
        zoo.addCaretaker(caretaker);
        zoo.addAnimal(hungryAnimal1);
        zoo.addAnimal(hungryAnimal2);
        caretaker.feedAnimals(zoo);
        assertEquals(0, zoo.getUnfedAnimals(zoo.getAnimals()).size());
    }

    @Test
    void testFeedHungryAnimalsWrongCaretaker() {
        Zoo zoo = new Zoo("Zoo");
        Caretaker caretaker = new Caretaker("Caretaker1",
                Set.of(AnimalType.AMPHIBIAN));
        Animal hungryAnimal1 = new Animal("Animal1",
                "Sound1",
                1,
                AnimalType.MAMMAL);
        Animal hungryAnimal2 = new Animal("Animal1",
                "Sound2",
                1,
                AnimalType.REPTILE);
        Animal hungryAnimal3 = new Animal("Animal1",
                "Sound3",
                1,
                AnimalType.FISH);
        zoo.addCaretaker(caretaker);
        zoo.addAnimal(hungryAnimal1);
        zoo.addAnimal(hungryAnimal2);
        zoo.addAnimal(hungryAnimal3);
        zoo.nextDay();
        caretaker.feedAnimals(zoo);
        assertEquals(3, zoo.getUnfedAnimals(zoo.getAnimals()).size());
    }

}
