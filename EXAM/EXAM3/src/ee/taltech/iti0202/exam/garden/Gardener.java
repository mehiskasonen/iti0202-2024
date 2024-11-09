package ee.taltech.iti0202.exam.garden;

import java.util.ArrayList;
import java.util.List;

public class Gardener {

    private final String name;
    private Garden garden;

    private List<FruitType> types = new ArrayList<>();
    private List<Fruit> harvestedFruit = new ArrayList<>();

    /**
     * Constructor for creating a new gardener with a name and a garden.
     * @param name The name of the gardener.
     * @param garden The garden associated with the gardener.
     */
    public Gardener(String name, Garden garden) {
        this.name = name;
        this.garden = garden;
    }

    /**
     * Toggles whether the gardener deals with the given type.
     * If currently not, then the gardener will harvest this type of fruits.
     * If the gardener currently harvests this type of fruit, the type is removed.
     * @param fruitType Type to toggle.
     */
    public void toggleFruitType(FruitType fruitType) {
        if (types.contains(fruitType)) {
            types.remove(fruitType);
        } else {
            types.add(fruitType);
        }
    }

    /**
     * Returns all the fruit types the given gardener has to harvest.
     * @return List of fruit types.
     */
    public List<FruitType> getFruitTypes() {
        return types;
    }

    /**
     * Returns the name of the gardener.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the garden, where gardener works.
     */
    public Garden getGarden() {
        return garden;
    }

    /**
     * Set new garden to the gardener if the current value of the garden is null.
     * @param garden The garden associated with the gardener.
     */
    public void setGarden(Garden garden) {
        if (this.garden == null) {
            this.garden = garden;
        }
    }

    /**
     * Returns the number of fruits harvested by the gardener.
     * @return The number of harvested fruits.
     */
    public int getHarvestFruitAmount() {
        return harvestedFruit.size();
    }

    /**
     * Harvests ripe fruits from the garden and returns them.
     * Gardener only harvests the types she has specified (with toggle).
     * If the gardener doesn't have any specific fruit types assigned, she harvests all the types.
     * @return A list of harvested fruits.
     */
    public List<Fruit> harvestFruits() {
        List<FruitType> typesToHarvest = new ArrayList<>();
        typesToHarvest.add(FruitType.STONE);
        typesToHarvest.add(FruitType.BERRY);
        typesToHarvest.add(FruitType.PEANUT);

        List<Fruit> harvest = garden.harvestFruits(types);
/*        for (Fruit f : garden.getFruits()) {
            if (f.isRipe() && (types.contains(f.getType()) || types.isEmpty())) {
                harvestedFruit.add(f);
            }
        }*/
        return harvest;
    }
}
