package ee.taltech.iti0202.generics.animalbox;

import ee.taltech.iti0202.generics.animal.Animal;
import ee.taltech.iti0202.generics.food.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalBox<T extends Animal> {

    private final List<T> box = new ArrayList<>();

    /**
     * @param animal to put into box.
     */
    public void put(T animal) {
        if (box.isEmpty()) {
            box.add(animal);
        }
    }

    /**
     * @return string of sound the animal in the box makes.
     */
    public String sound() {
        if (box.isEmpty()) {
            return "";
        }
        return getAnimal().get().sound();
    }

    /**
     * @param s food type.
     * @return string stating which animal was given which food.
     */
    public String feed(Food s) {
        if (!box.isEmpty()) {
            return getAnimal().get().getName() + " was fed with " + s.getName() + ".";
        }
        return "No animal to feed.";
    }

    /**
     * @return optional of animal, if animal is in the box.
     */
    public Optional<T> getAnimal() {
        if (!box.isEmpty()) {
            return Optional.ofNullable(box.get(0));
        } else {
            return Optional.empty();
        }
    }
}
