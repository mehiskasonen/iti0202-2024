package ee.taltech.iti0202.zoo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZooTest {

    @Test
    void testCreateNewZoo() {
        Zoo zoo = new Zoo("Zoo1");
        assertEquals("Zoo1", zoo.getName());
        assertEquals(0, zoo.getAnimals().size());
    }

    @Test
    void testGetNameZoo() {
        Zoo zoo = new Zoo("Zoo1");
        assertEquals("Zoo1", zoo.getName());
    }

    @Test
    void testAddAnimal() {
        Zoo zoo = new Zoo("Zoo");
        Animal animal = new AnimalBuilder()
                .setName("Animal")
                .build();
        zoo.addAnimal(animal);
        assertTrue(zoo.getAnimals().contains(animal));
    }

/*    @Test
    void testAddAnimals() {
        Zoo zoo = new Zoo("Zoo");
        Animal animal = new AnimalBuilder()
                .setName("Animal")
                .build();
        zoo.addAnimals(animal, 4);
        assertEquals(4, zoo.getAnimals().size());
    }*/

    @Test
    void testAddAnimalsJavaReflection() {
        Zoo zoo = new Zoo("Test Zoo");
        zoo.addAnimalsJavaReflection(Monkey.class, 3);
        assertEquals(3, zoo.getAnimalCount(Monkey.class));
    }

    @Test
    void testAddCaretaker() {
        Zoo zoo = new Zoo("Zoo");
        Caretaker caretaker = new Caretaker("Caretaker1", Set.of(AnimalType.FISH));
        zoo.addCaretaker(caretaker);
        assertTrue(zoo.getCaretakers().contains(caretaker));
    }

    @Test
    void testAddCaretakersJavaReflection() {
        Zoo zoo = new Zoo("Test Zoo");
        Set<AnimalType> canFeed1 = new HashSet<>();
        canFeed1.add(AnimalType.MAMMAL);
        canFeed1.add(AnimalType.BIRD);

        Set<AnimalType> canFeed2 = new HashSet<>();
        canFeed2.add(AnimalType.FISH);
        canFeed2.add(AnimalType.REPTILE);

        zoo.addCaretakersJavaReflection(Caretaker.class,
                List.of("John", "Alice"),
                List.of(canFeed1, canFeed2));

        // Check if caretakers were added successfully
        assertEquals(2, zoo.getCaretakers().size());
    }

/*    @Test
    void testAddCaretakers() {
        Zoo zoo = new Zoo("Zoo");
        Caretaker caretaker = new Caretaker("Caretaker1", Set.of(AnimalType.FISH));
        zoo.addCaretakers(caretaker, 4);
        assertEquals(4, zoo.getCaretakers().size());
    }*/

    @Test
    void testGetAnimalsZoo() {
        Animal monkey = new AnimalBuilder()
                .setName("Monkey")
                .build();
        Animal turtle = new AnimalBuilder()
                .setName("Turtle")
                .build();
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey);
        zoo.addAnimal(turtle);
        Set<Animal> zooAnimals = zoo.getAnimals();
        assertEquals(2, zooAnimals.size());
    }

    @Test
    void testGetUnfedAnimalsZoo() {
        Set<Animal> animals = new HashSet<>();
        Animal monkey = new AnimalBuilder()
                .setName("Monkey")
                .setHungryInDays(1)
                .build();
        Animal turtle = new AnimalBuilder()
                .setName("Turtle")
                .setHungryInDays(0)
                .build();
        Animal giraffe = new AnimalBuilder()
                .setName("Giraffe")
                .setHungryInDays(0)
                .build();
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey);
        zoo.addAnimal(turtle);
        zoo.addAnimal(giraffe);
        Set<Animal> zooUnfedAnimals = zoo.getUnfedAnimals(zoo.getAnimals());
        assertEquals(2, zooUnfedAnimals.size());
    }

    //TODO Feed an animal.
    @Test
    void testGetUnfedAnimalsZooAfterFeeding() {
        Animal monkey = new AnimalBuilder()
                .setName("Monkey")
                .setHungryInDays(1)
                .build();
        Animal turtle = new AnimalBuilder()
                .setName("Turtle")
                .setHungryInDays(0)
                .build();
        Animal giraffe = new AnimalBuilder()
                .setName("Giraffe")
                .setHungryInDays(0)
                .build();
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey);
        zoo.addAnimal(turtle);
        zoo.addAnimal(giraffe);

        Set<Animal> zooUnfedAnimals = zoo.getUnfedAnimals(zoo.getAnimals());
        assertEquals(2, zooUnfedAnimals.size());
    }

    //TODO Skip to next day.
    @Test
    void testGetUnfedAnimalsZooAfterNewDay() {
        Animal monkey = new AnimalBuilder()
                .setName("Monkey")
                .setHungryInDays(1)
                .build();
        Animal turtle = new AnimalBuilder()
                .setName("Turtle")
                .setHungryInDays(0)
                .build();
        Animal giraffe = new AnimalBuilder()
                .setName("Giraffe")
                .setHungryInDays(0)
                .build();
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey);
        zoo.addAnimal(turtle);
        zoo.addAnimal(giraffe);
        Set<Animal> zooUnfedAnimals = zoo.getUnfedAnimals(zoo.getAnimals());
        assertEquals(2, zooUnfedAnimals.size());
    }

    @Test
    void testGetOverviewOfAnimalsZoo() {
        Animal dog = new Animal("Muki", "auh!", 3, AnimalType.MAMMAL);
        Animal monkey2 = new Animal("Abu", "BANANA", 4, AnimalType.MAMMAL);
        Animal fish = new Animal("Sashimi", "", 2, AnimalType.FISH);
        Animal bird = new Animal("Triibu", "tsiutsiu", 5, AnimalType.BIRD);
        Zoo zoo = new Zoo("Zoo1");

        zoo.addAnimal(dog);
        zoo.addAnimal(monkey2);
        zoo.addAnimal(fish);
        zoo.addAnimal(bird);

        List<String> overview = zoo.getOverview(zoo.getAnimals());
        String expectedString = "Muki (MAMMAL): \"auh!\", Abu (MAMMAL): \"BANANA\","
                + " Sashimi (FISH): \"\", Triibu (BIRD): \"tsiutsiu\"";
        List<String> expected = Arrays.asList(expectedString.split(", "));
        assertEquals(expected, overview);
    }

    @Test
    void testGetMostEffectiveWorkerSituational() {
        Animal monkey = new AnimalBuilder()
                .setName("Monkey")
                .setType(AnimalType.MAMMAL)
                .setHungryInDays(1)
                .build();
        Animal turtle = new AnimalBuilder()
                .setName("Turtle")
                .setType(AnimalType.AMPHIBIAN)
                .setHungryInDays(1)
                .build();
        Animal fish = new AnimalBuilder()
                .setName("Fish")
                .setType(AnimalType.FISH)
                .setHungryInDays(1)
                .build();
        Animal snake = new AnimalBuilder()
                .setName("Boa")
                .setType(AnimalType.REPTILE)
                .setHungryInDays(1)
                .build();
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey);
        zoo.addAnimal(turtle);
        zoo.addAnimal(fish);
        zoo.addAnimal(snake);
        zoo.nextDay();

        Caretaker caretaker1 = new Caretaker("Laura",
                Set.of(AnimalType.AMPHIBIAN, AnimalType.REPTILE, AnimalType.FISH));
        Caretaker caretaker2 = new Caretaker("Peeter",
                Set.of(AnimalType.REPTILE, AnimalType.FISH));

        zoo.addCaretaker(caretaker1);
        zoo.addCaretaker(caretaker2);
        Caretaker expected = zoo.getMostEffectiveWorkerSituational(zoo.getAnimals(), zoo.getCaretakers());
        assertEquals(expected, caretaker1);
    }

    @Test
    void testNextDayAnimalsGetHungrier() {
        Animal monkey = new AnimalBuilder()
                .setName("Monkey")
                .setHungryInDays(2)
                .build();
        Zoo zoo = new Zoo("Zoo1");
        zoo.addAnimal(monkey);
        zoo.nextDay();
        assertEquals(1, monkey.getsHungryIn());
    }
}
