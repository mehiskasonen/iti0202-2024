package ee.taltech.iti0202.generics.food;

public abstract class Food {

    private final String name;

    /**
     * @param name of food.
     */
    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Food{" + "name='" + name + '\'' + '}';
    }
}
