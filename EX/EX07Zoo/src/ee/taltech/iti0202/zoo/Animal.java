package ee.taltech.iti0202.zoo;

enum AnimalType {
    MAMMAL,
    BIRD,
    FISH,
    REPTILE,
    AMPHIBIAN
}

public class Animal {
    String name;
    String sound;
    private final int initialGetsHungryInDays;
    private AnimalType type;
    private int untilHungry;


    /**
     * Constructor for the Animal class.
     * @param name of animal.
     * @param sound animal makes.
     * @param initialGetsHungryInDays count given that determines when animal gets hungry.
     * @param type of animal. Enum.
     */
    public Animal(String name, String sound, int initialGetsHungryInDays, AnimalType type) {
        this.name = name;
        this.sound = sound;
        this.initialGetsHungryInDays = initialGetsHungryInDays;
        this.type = type;
        this.untilHungry = initialGetsHungryInDays;
    }

    /**
     * @return name of animal.
     */
    public String getName() {
        return name;
    }

    /**
     * If the animal is hungry, it makes no sound.
     * @return the sound the animal makes.
     */
    public String getSound() {
        return !isHungry() ? sound : "";
    }

    /**
     * Determines when the animal gets hungry.
     * @return counter (days) until animal gets hungry.
     */
    public int getsHungryIn() {
        return untilHungry;
    }

    /**
     * @return the animals type.
     */
    public AnimalType getType() {
        return type;
    }

    /**
     * If the initial value is -1 then returns false.
     * Initial value is set to -1 by default if AnimalBuilder was used and
     * no value was given to the animal. These animals never get hungry.
     * @return true or false, weather the animal is hungry or not.
     */
    public boolean isHungry() {
        return (untilHungry > -1 || untilHungry == 0) && untilHungry < 1;
    }

    /**
     * Animal goes nom nom.
     * @return sound of animal after it has fed.
     */
    public String feed() {
        if (isHungry()) {
            System.out.println("Has been fed");
            untilHungry = initialGetsHungryInDays;
        }
        return getSound();
    }

    /**
     * Decrement the value of when animals get hungrier.
     * As the days pass, animals get hungrier.
     * This method is used when skipping to next day.
     */
    public void getsHungrier() {
        untilHungry--;
    }
}

