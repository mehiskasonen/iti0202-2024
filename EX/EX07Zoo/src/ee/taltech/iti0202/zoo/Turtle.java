package ee.taltech.iti0202.zoo;

public class Turtle extends Animal {

    /**
     * Class constructor for turtle.
     * @param name of turtle.
     */
    public Turtle(String name) {
        super(name, "", 1, AnimalType.AMPHIBIAN);
    }

    /**
     * Overwrites superclass method of making sound since turtles always make
     * sound "", weather hungry or not.
     * @return string sound in the form of "".
     */
    @Override
    public String getSound() {
        return "";
    }
}
