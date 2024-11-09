package ee.taltech.iti0202.zoo;

public class Lamb extends Animal {

    /**
     * Class constructor for Lamb.
     * Lamb always makes the noise "Mää" and is never hungry.
     * //TODO Make lamb get hungrier, but then feed on its own.
     * @param name of lamb.
     */
    public Lamb(String name) {
        super(name, "Mää", 2, AnimalType.MAMMAL);
    }

    /**
     * Overwrites superclass method when feeding.
     * @return the sound lamb makes when feeding.
     */
    @Override
    public String feed() {
        return getSound();
    }
}
