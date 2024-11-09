package ee.taltech.iti0202.zoo;

import java.util.Random;

public class Monkey extends Animal {
    private static final String[] MONKEY_SOUNDS = {"uuh", "ääh"};

    /**
     * Class constructor for monkey.
     * @param name of monkey.
     */
    public Monkey(String name) {
        super(name, "", 2, AnimalType.MAMMAL);
    }

    /**
     * Overwrites superclass method of making sound.
     * When monkey is hungry it makes the sound "BANANA".
     * When monkey is not hungry it makes the sound "uuh" or "ääh", randomly.
     * @return string of sound the monkey makes.
     */
    @Override
    public String getSound() {
        if (isHungry()) {
            return "BANANA";
        } else {
            return MONKEY_SOUNDS[new Random().nextInt(MONKEY_SOUNDS.length)];
        }
    }

    /**
     * Overwrites superclass method of feeding, since
     * monkey makes random sounds. Counter when animal needs to feed again is reset.
     * @return sting of sound the monkey randomly makes.
     */
    @Override
    public String feed() {
        super.feed();
        return getSound();
    }
}
