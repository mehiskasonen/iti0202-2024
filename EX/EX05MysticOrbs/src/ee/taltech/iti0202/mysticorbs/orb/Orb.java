package ee.taltech.iti0202.mysticorbs.orb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orb {
    protected final String creator;
    protected static Map<Orb, Integer> orbMap = new HashMap<>();
    private static List<Orb> orbs = new ArrayList<>();

    /**
     * Constructor that gives orb the Oven name that it was produced in.
     * @param creator string.
     */
    public Orb(String creator) {
        this.creator = creator;
        orbMap.put(this, 0);
    }

    /**
     * Method for charging an orb.
     * Orb energy increases by the number of symbols in resource name * amount.
     * E.g. if the resource is 'dust' and consists of spaces, nothing happens.
     * @param resource String.
     * @param amount integer.
     */
    public void charge(String resource, int amount) {
        int energyIncrease = resource.length() * amount;
        if (!resource.equalsIgnoreCase("dust") && !resource.equals("DUST") && !resource.trim().isEmpty()) {
            if (energyIncrease > 0) {
                int currentEnergy = orbMap.get(this);
                orbMap.put(this, currentEnergy + energyIncrease);
            }
        }
    }

    /**
     * Return orbs current energy.
     * @return integer.
     */
    public int getEnergy() {
        return orbMap.get(this);
    }

    /**
     * toString method that returns a line in the form of "Orb by [creator]", where creator is the name of the oven
     * that has produced that orb.
     * @return string.
     */
    public String toString() {
        if (this instanceof MagicOrb) {
            return "MagicOrb by " + creator;
        }
        return "Orb by " + creator;
    }


    /**
     * @return List of orbs that have been produced.
     */
    public static List<Orb> getProducedOrbs() {
        orbs.addAll(orbMap.keySet());
        return orbs;
    }

    /**
     * Clears the list of orbs that have been produced.
     */
    public static void clearProducedOrbs() {
        orbs.clear();
    }
}
