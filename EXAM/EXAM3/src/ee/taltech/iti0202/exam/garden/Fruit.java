package ee.taltech.iti0202.exam.garden;

public class Fruit {

    private final String name;
    private int ripeningDays;
    private final FruitType type;
    private boolean ripe = false;

    /**
     * Constructor for creating a new fruit with a name and ripening days.
     * @param name The name of the fruit.
     * @param ripeningDays The number of days it takes for the fruit to ripen.
     * @param type The type of the fruit.
     */
    public Fruit(String name, int ripeningDays, FruitType type) {
        this.name = name;
        this.ripeningDays = ripeningDays;
        this.type = type;
    }

    /**
     * Returns the name of the fruit.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of ripening days.
     */
    public int getRipeningDays() {
        return ripeningDays;
    }

    /**
     * Returns the type of the fruit.
     */
    public FruitType getType() {
        return type;
    }

    /**
     * Checks if the fruit is ripe.
     * @return True if the fruit is ripe, false otherwise.
     */
    public boolean isRipe() {
        return ripe;
    }

    /**
     * Makes the fruit ripe.
     */
    public void ripen() {
        ripe = true;
    }

    /**
     * Decreases the number of ripening days by one.
     * If the ripening days reach zero, the fruit becomes ripe.
     * Ripening days could not become smaller than zero.
     */
    public void decreaseRipeningDays() {
        if (ripeningDays != 0) {
            ripeningDays -= 1;
            if (ripeningDays == 0) {
                ripen();
            }
        }
    }
}

