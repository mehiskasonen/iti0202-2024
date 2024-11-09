package ee.taltech.iti0202.mysticorbs.oven;

import ee.taltech.iti0202.mysticorbs.storage.ResourceStorage;
import ee.taltech.iti0202.mysticorbs.orb.Orb;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Oven implements Comparable<Oven> {

    private static final int NR_CREATEDORBSTOTAL = 15;
    private final String name;
    private ResourceStorage resourceStorage;
    public int createdOrbsTotal = 0;
    protected int nrOfMagicOrbsCrafted = 0;
    public int craftedByMagicOvenEvenOrNot = 0;
    public int craftedByMagicOven = 0;
    protected boolean kaput;

    /**
     * Constructor for creating an oven instance. Takes in oven name and reference to ResourceStorage.
     *
     * @param name            string.
     * @param resourceStorage object string?
     */
    public Oven(String name, ResourceStorage resourceStorage) {
        this.name = name;
        this.resourceStorage = resourceStorage;
        this.kaput = false;
    }

    /**
     * Method to return name of oven.
     *
     * @return string of object.
     */
    public String getName() {
        return name;
    }

    /**
     * Return reference to resourceStorage where this oven takes resources from.
     *
     * @return object?
     */
    public ResourceStorage getResourceStorage() {
        return this.resourceStorage;
    }

    /**
     * Return integer that is equal to the amount of orbs created.
     *
     * @return integer.
     */
    public int getCreatedOrbsAmount() {
        return this.createdOrbsTotal;
    }

    /**
     * Return true if the oven is broken, and return false if it is working.
     *
     * @return boolean.
     */
    public boolean isBroken() {
        return createdOrbsTotal >= NR_CREATEDORBSTOTAL;
    }

    /**
     * Creates an Orb if the owen is working and if there are enough resources. If by any chance an orb can
     * not be created, returns an empty Optional.
     *
     * @return empty Optional.
     */
    public Optional<Orb> craftOrb() {
        if (this instanceof MagicOven && getResourceStorage().hasEnoughResource("dust", 3)
                && getResourceStorage().hasEnoughResource("gold", 1)) {
            if (isBroken()) {
                return Optional.empty();
            }
            resourceStorage.takeResource("dust", 3);
            resourceStorage.takeResource("gold", 1);
            Orb orb = new Orb(name);
            orb.charge("gold", 1);
            orb.charge("dust", 3);
            createdOrbsTotal++;
            craftedByMagicOven++;
            craftedByMagicOvenEvenOrNot++;
            System.out.println(orb);
            return Optional.of(orb);
        }
        if (getResourceStorage().hasEnoughResource("pearl", 1)
                && getResourceStorage().hasEnoughResource("silver", 1) && !isBroken()) {
            Orb orb = new Orb(name);
            orb.charge("pearl", 1);
            orb.charge("silver", 1);
            resourceStorage.takeResource("silver", 1);
            resourceStorage.takeResource("pearl", 1);
            if (this instanceof MagicOven) {
                nrOfMagicOrbsCrafted++;
            }
            createdOrbsTotal++;
            System.out.println(orb);
            return Optional.of(orb);
        } else {
            return Optional.empty();
        }
    }


    /**
     * @param o the object to be compared.
     * @return integer
     */
    @Override
    public int compareTo(Oven o) {
        //1 (o1 > o2), -1 (o1 < o2) või 0 (o1 == o2)

        if (this.isBroken() != o.isBroken()) {
            if (this.isBroken()) {
                return -1;
            } else return 1;
        } //both broken or not broken, prioritize on type
        List<Class<? extends Oven>> ovenTypes = Arrays.asList(
                Oven.class,
                MagicOven.class,
                InfinityMagicOven.class,
                SpaceOven.class);
        int typeComparison = ovenTypes.indexOf(this.getClass())
                - ovenTypes.indexOf(o.getClass());
        if (typeComparison != 0) {
            if (typeComparison < 0) {
                return -1;
            } else return 1;
        }

        //prioritize over number of created orbs
        int orbComparison = Integer.compare(this.getCreatedOrbsAmount(), o.getCreatedOrbsAmount());
        if (orbComparison != 0) {
            if (orbComparison < 0) {
                return 1;
            } else return -1;
            //return orbComparison;
        }

        //compare names
        int compareResult = this.getName().compareTo(o.getName());
        if (compareResult != 0) {
            return compareResult;
        }
        //this.name == oven.name
        return this.getName().compareTo(o.getName());
    }

}
