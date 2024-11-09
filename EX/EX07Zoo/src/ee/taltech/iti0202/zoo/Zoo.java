package ee.taltech.iti0202.zoo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Zoo {
    private final String name;
    private LinkedHashSet<Animal> animals = new LinkedHashSet<>();
    private LinkedHashSet<Caretaker> caretakers = new LinkedHashSet<>();


    /**
     * Class constructor for zoo.
     *
     * @param name of zoo.
     */
    public Zoo(String name) {
        this.name = name;
    }

    /**
     * @return the name of Zoo.
     */
    public String getName() {
        return name;
    }

    /**
     * @return linked hashset of animals the zoo has.
     */
    public LinkedHashSet<Animal> getAnimals() {
        return animals;
    }


    /**
     * @param a for animal to add to the zoo.
     */
    public void addAnimal(Animal a) {
        animals.add(a);
    }

/*    public void addAnimals(Animal animal, int count) {
        for (int i = 0; i < count; i++ ) addAnimal(animal);
    }*/

    /**
     * Adds multiple animals of the specified class to the zoo using reflection.
     *
     * @param animalClass class of the animal to add.
     * @param count       number of animals to add to zoo animals linked hashset.
     *                    Throws RuntimeException if the constructor for the specified animal class cannot be found,
     *                    or if there is an error during instantiation or invocation.
     */
    public void addAnimalsJavaReflection(Class<? extends Animal> animalClass, int count) {
        try {
            Constructor<? extends Animal> constructor = animalClass.getConstructor(String.class);
            for (int i = 0; i < count; i++) {
                Animal animal = constructor.newInstance("defaultName" + i);
                addAnimal(animal);
            }
        } catch (NoSuchMethodException
                 | InvocationTargetException
                 | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param animalClass class of animal to count.
     * @return integer amount of animals with given class.
     */
    public int getAnimalCount(Class<? extends Animal> animalClass) {
        long count = animals.stream()
                .filter(animalClass::isInstance)
                .count();
        return (int) count;
    }

    /**
     * @param c for single caretaker to add to zoos linked hashset.
     */
    public void addCaretaker(Caretaker c) {
        caretakers.add(c);
    }

/*    public void addCaretakers(Caretaker c, int count) {
        for (int i = 0; i < count; i++ ) addCaretaker(c);
    }*/

    /**
     * Adds multiple caretakers of the specified class to the zoo using reflection.
     *
     * @param caretakerClass class of the caretaker to add.
     * @param names          list of names for the caretakers.
     * @param canFeedSets    list of sets of AnimalType indicating the types of animals each caretaker can feed.
     *                       throws RuntimeException if the constructor for the specified caretaker
     *                       class cannot be found,
     *                       or if there is an error during instantiation or invocation.
     */
    public void addCaretakersJavaReflection(Class<? extends Caretaker> caretakerClass,
                                            List<String> names,
                                            List<Set<AnimalType>> canFeedSets) {
        try {
            Constructor<? extends Caretaker> constructor = caretakerClass.getConstructor(String.class, Set.class);
            for (int i = 0; i < names.size(); i++) {
                Caretaker caretaker = constructor.newInstance(names.get(i), canFeedSets.get(i));
                caretakers.add(caretaker);
            }
        } catch (NoSuchMethodException | InstantiationException
                 | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return linked hashset of caretakers the zoo has.
     */
    public LinkedHashSet<Caretaker> getCaretakers() {
        return caretakers;
    }

    /**
     * @param animals the zoo has.
     * @return all the animals who are hungry.
     */
    public Set<Animal> getUnfedAnimals(Set<Animal> animals) {
        return animals.stream()
                .filter(Animal::isHungry)
                .collect(Collectors.toSet());
    }

    /**
     * Method to get an overview of all the animals that are in the zoo,
     * their type and the sound they make.
     *
     * @param animals the zoo has.
     * @return list of animals on the form of [name (TYPE): sound] etc...
     */
    public List<String> getOverview(Set<Animal> animals) {
        List<String> returnList = new LinkedList<>();
        Set<Animal> orderedAnimals = new LinkedHashSet<>(animals); // Use LinkedHashSet to maintain insertion order
        for (Animal animal : orderedAnimals) {
            String strBuilder = animal.getName()
                    + " ("
                    + animal.getType()
                    + "): "
                    + "\""
                    + animal.getSound()
                    + "\"";
            returnList.add(strBuilder);
        }
        System.out.println(returnList);
        return returnList;
    }

    /**
     * @param animals    the zoo has.
     * @param caretakers the zoo has.
     * @return the worker who can feed the most amount of currently hungry animals.
     */
    public Caretaker getMostEffectiveWorkerSituational(Set<Animal> animals, Set<Caretaker> caretakers) {
        Set<Animal> unfedAnimals = getUnfedAnimals(animals);
        Map<Caretaker, Integer> caretakerCounts = new HashMap<>();

        for (Caretaker caretaker : caretakers) {
            int count = 0;
            for (Animal animal : unfedAnimals) {
                if (caretaker.canFeed.contains(animal.getType())) {
                    count++;
                }
            }
            caretakerCounts.put(caretaker, count);
        }
        Caretaker mostEffectiveCaretaker = null;
        int maxCount = 0;
        for (Map.Entry<Caretaker, Integer> entry : caretakerCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostEffectiveCaretaker = entry.getKey();
            }
        }
        System.out.println(mostEffectiveCaretaker);
        return mostEffectiveCaretaker;
    }

    /**
     * Method to simulate days going by.
     * Every day animals get hungrier.
     * Uses the method getsHungrier.
     * The counter to track when animals get hungry, can not be negative.
     */
    public void nextDay() {
        for (Animal animal : getAnimals()) {
            if (animal.getsHungryIn() == 0) {
                continue;
            }
            if (animal instanceof Lamb) {
                continue;
            } else animal.getsHungrier();
        }
    }
}
