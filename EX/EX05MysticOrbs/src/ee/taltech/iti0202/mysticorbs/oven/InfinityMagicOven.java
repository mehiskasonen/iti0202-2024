package ee.taltech.iti0202.mysticorbs.oven;

import ee.taltech.iti0202.mysticorbs.orb.MagicOrb;
import ee.taltech.iti0202.mysticorbs.storage.ResourceStorage;
import ee.taltech.iti0202.mysticorbs.orb.Orb;

import java.util.Optional;

public class InfinityMagicOven extends MagicOven {

    private int numCrafted = 0;

    protected int craftedByInfMagicOven = 0;

    /**
     * Constructor for creating an InfinityMagicOven instance. Takes in oven name and reference to ResourceStorage.
     * This oven never breaks down.
     * @param name string.
     * @param resourceStorage object string?
     */
    public InfinityMagicOven(String name, ResourceStorage resourceStorage) {
        super(name, resourceStorage);

    }

    /**
     * Why is this method necessary?
     * @return boolean.
     */
    @Override
    public boolean isBroken() {
        return false;
    }

    /**
     * Creates an Orb if the owen is working and if there are enough resources. If by any chance an orb can
     * not be created, returns an empty Optional.
     * @return empty Optional.
     */
    @Override
    public Optional<Orb> craftOrb() {
        if (craftedByInfMagicOven % 2 == 1
                && getResourceStorage().hasEnoughResource("gold", 1)
                && getResourceStorage().hasEnoughResource("dust", 3)) {
            Orb orb = new MagicOrb(getName());
            orb.charge("gold", 1);
            orb.charge("dust", 3);
            getResourceStorage().takeResource("gold", 1);
            getResourceStorage().takeResource("dust", 3);
            createdOrbsTotal++;
            craftedByInfMagicOven++;
            System.out.println(orb);
            return Optional.of(orb);
        } else if (getResourceStorage().hasEnoughResource("gold", 1)
                && getResourceStorage().hasEnoughResource("dust", 3)) {
            Orb orb = new Orb(getName());
            orb.charge("gold", 1);
            orb.charge("dust", 1);
            getResourceStorage().takeResource("gold", 1);
            getResourceStorage().takeResource("dust", 3);
            craftedByInfMagicOven++;
            createdOrbsTotal++;
            numCrafted++;
            System.out.println(orb);
            //System.out.println("Silver: " + getResourceStorage().getResourceAmount("silver"));
            //System.out.println("Pearl: " + getResourceStorage().getResourceAmount("pearl"));
            return Optional.of(orb);
        } else {
            System.out.println("Failed to create magicOrb.");
            return Optional.empty();
        }
    }
}
