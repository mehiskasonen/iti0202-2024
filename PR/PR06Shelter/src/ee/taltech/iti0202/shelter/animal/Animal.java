package ee.taltech.iti0202.shelter.animal;

/**
 * Animal class
 */
public abstract class Animal {

    public enum Type {
        HIROLA, TARANTULA, WOMBAT
    }

    private String color;
    private Type type;


    /**
     * Constructor for animal object.
     * @param color of the animal.
     */
    public Animal(String color) {
        this.color = color;
    }

    /**
     * Method to return animal object color.
     * @return color of animal
     */
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Return animal object in string form.
     * @return string representation of an animal.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + color + '}';
    }
}
