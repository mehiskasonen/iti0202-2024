package ee.taltech.iti0202.zoo;

import java.util.Set;

public class Caretaker {
    private final String name;
    final Set<AnimalType> canFeed;

    /**
     * Class constructor for the caretaker.
     * @param name of the caretaker.
     * @param canFeed a set of animal types the caretaker knows how to feed.
     */
    public Caretaker(String name, Set<AnimalType> canFeed) {
        this.name = name;
        this.canFeed = canFeed;
    }

    /**
     * Method the caretaker uses to feed animals who are hungry.
     * Caretaker can only feed animals he/she knows how to feed.
     * @param zoo that the caretaker should feed animals in.
     */
    public void feedAnimals(Zoo zoo) {
        for (Animal animal : zoo.getUnfedAnimals(zoo.getAnimals())) {
            if (canFeed.contains(animal.getType())) {
                animal.feed();
            }
        }
    }
}
