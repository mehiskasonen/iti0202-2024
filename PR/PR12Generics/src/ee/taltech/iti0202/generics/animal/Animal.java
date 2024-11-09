package ee.taltech.iti0202.generics.animal;

public abstract class Animal {

    private String name;

    /**
     * @param name of animal.
     */
    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * @param name to set for animal.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return method declaration for abstract Animal.
     */
    public abstract String sound();

}
