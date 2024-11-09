package ee.taltech.iti0202.zoo;

public class AnimalBuilder {
    private String name;
    private String sound;
    private int hungryInDays = -1;
    private AnimalType type;

    /**
     * Sets the name of the animal being constructed.
     * @param name of animal.
     * @return the AnimalBuilder instance with the name set.
     */
    public AnimalBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the sound of the animal being constructed.
     * @param sound of animal.
     * @return the AnimalBuilder instance with the sound set.
     */
    public AnimalBuilder setSound(String sound) {
        this.sound = sound;
        return this;
    }

    /**
     * Sets the amount of days when the animal gets hungry.
     * @param hungryInDays counter.
     * @return the AnimalBuilder instance with the counter set.
     */
    public AnimalBuilder setHungryInDays(int hungryInDays) {
        this.hungryInDays = hungryInDays;
        return this;
    }

    /**
     * Sets the animals type chosen from Enum.
     * @param type of animal.
     * @return the AnimalBuilder instance with the type set.
     */
    public AnimalBuilder setType(AnimalType type) {
        this.type = type;
        return this;
    }

    /**
     * Builds an instance of Animal using the provided attributes.
     * @return nn instance of Animal with the specified attributes.
     */
    public Animal build() {
        //boolean isHungry = sound == null || sound.isEmpty() || hungryInDays < 0;
        return new Animal(name, sound, hungryInDays, type);
    }
}
