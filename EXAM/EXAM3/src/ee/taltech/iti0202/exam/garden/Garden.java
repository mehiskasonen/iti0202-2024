package ee.taltech.iti0202.exam.garden;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Garden {

    private final String name;
    private List<Fruit> fruits = new ArrayList<>();
    private List<Gardener> gardeners = new ArrayList<>();

    /**
     * Constructor for creating a new garden with a name.
     * @param name The name of the garden.
     */
    public Garden(String name) {
        this.name = name;
    }

    /**
     * Returns fruits in this garden.
     */
    public List<Fruit> getFruits() {
        return fruits;
    }

    /**
     * Returns gardeners in this garden.
     */
    public List<Gardener> getGardeners() {
        return gardeners;
    }

    /**
     * Adds a fruit to the garden.
     * @param fruit The fruit to be added to the garden.
     */
    public void addFruit(Fruit fruit) {
        if (!fruits.contains(fruit)) {
            fruits.add(fruit);
        }
    }

    /**
     * Adds a gardener to the garden if the number of gardeners is less than 5
     * and gardener´s garden is equal to null.
     * @param gardener The gardener to be added to the garden.
     */
    public void addGardener(Gardener gardener) {
        if (gardeners.size() < 5 && gardener.getGarden() == null) {
            gardeners.add(gardener);
        }
    }

    /**
     * Updates the garden's state at the beginning of a new day,
     * decreasing the ripening days of all fruits waiting to ripen by 1.
     */
    public void newDay() {
        for (Fruit f : fruits) {
            f.decreaseRipeningDays();
        }
    }

    /**
     * Harvests ripe fruits with the given types from the garden and returns them.
     * All harvested fruits have to be removed from fruits list in this garden.
     * @param types Only the given types of fruits are harvested.
     * @return A list of harvested fruits.
     */
    public List<Fruit> harvestFruits(List<FruitType> types) {
        List<Fruit> harvest = new ArrayList<>();
        Iterator<Fruit> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            Fruit fruit = iterator.next();
            if (fruit.isRipe() && (types.contains(fruit.getType()) || types.isEmpty())) {
                harvest.add(fruit);
                iterator.remove();
            }
        }
        return harvest;
    }

    /**
     * Returns the name of the best gardener who has harvested the most fruits from the garden.
     * @return The name of the best gardener.
     */
    public String getBestGardener() {
        return null;
    }

    /**
     * Returns a string representation of the garden with structure "Welcome to {garden name} garden!".
     */
    @Override
    public String toString() {
        return "Welcome to " + name + " garden!";
    }
}

