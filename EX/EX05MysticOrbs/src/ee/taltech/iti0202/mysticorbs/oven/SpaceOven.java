package ee.taltech.iti0202.mysticorbs.oven;

import ee.taltech.iti0202.mysticorbs.exceptions.CannotFixException;
import ee.taltech.iti0202.mysticorbs.factory.OrbFactory;
import ee.taltech.iti0202.mysticorbs.orb.SpaceOrb;
import ee.taltech.iti0202.mysticorbs.storage.ResourceStorage;
import ee.taltech.iti0202.mysticorbs.orb.Orb;

import java.util.Optional;

public class SpaceOven extends Oven implements Fixable {

    public static final int NUM_CRAFTED_25 = 25;
    public static final int RESOURCE_AMOUNT_15 = 15;
    public static final int RESOURCE_AMOUNT_40 = 40;


    private boolean spaceOvenIsBroken;
    private int timesFixedSpaceOven = 0;
    private int numCrafted = 0;

    /**
     * Unique oven that produces SpaceOrbs. If it is broken or there is not enough resources to produce SpaceOrbs,
     * then it produces regular Orbs.
     * If there are not enough resources to produce regular Orbs, then it can do nothing.
     * It breaks down after creating its 25th orb.
     * @param name string
     * @param resourceStorage reference to object?
     */
    public SpaceOven(String name, ResourceStorage resourceStorage) {

        super(name, resourceStorage);
        this.spaceOvenIsBroken = false;
    }

    /**
     * Creates an SpaceOrb if the owen is working and if there are enough resources. If by any chance an orb can
     * not be created, returns an empty Optional.
     * @return empty Optional.
     */
    @Override
    public Optional<Orb> craftOrb() {
        if (getResourceStorage().hasEnoughResource("meteorite stone", 1)
                && getResourceStorage().hasEnoughResource("star fragment", RESOURCE_AMOUNT_15)
                && (!isBroken())) {
            Orb orb = new SpaceOrb(getName());
            createdOrbsTotal++;
            numCrafted++;
            getResourceStorage().takeResource("meteorite stone", 1);
            getResourceStorage().takeResource("star fragment", RESOURCE_AMOUNT_15);
            System.out.println(orb);
            return Optional.of(orb);
        } else {
            return createNormalOrb();
        }

    }

    /**
     * Helper function to craft a normal orb.
     * @return normal orb or empty.
     */
    private Optional<Orb> createNormalOrb() {
        if (getResourceStorage().hasEnoughResource("pearl", 1)
                && getResourceStorage().hasEnoughResource("silver", 1)) {
            Orb orb = new Orb(getName());
            orb.charge("pearl", 1);
            orb.charge("silver", 1);
            getResourceStorage().takeResource("silver", 1);
            getResourceStorage().takeResource("pearl", 1);
            createdOrbsTotal++;
            numCrafted++;
            System.out.println("Crafted " + orb);
            return Optional.of(orb);
        } else {
            System.out.println("Failed to create spaceOrb.");
            return Optional.empty();
        }
    }


    /**
     * Override Oven class isBroken method.
     * SpaceOven breaks after producing 25 SpaceOrbs.
     * @return boolean.
     */
    public boolean isBroken() {
        if (getTimesFixed() >= 5) {
            return false;
        }
        return numCrafted >= NUM_CRAFTED_25;
    }

    @Override
    public void fix() throws CannotFixException {
        if (!isBroken()) {
            throw new CannotFixException(this, CannotFixException.Reason.IS_NOT_BROKEN);
        }
        if (getTimesFixed() >= 10) {
            throw new CannotFixException(this, CannotFixException.Reason.FIXED_MAXIMUM_TIMES);
        }
        if (!getResourceStorage().hasEnoughResource("liquid silver", RESOURCE_AMOUNT_40
                )
                && (!getResourceStorage().hasEnoughResource("star essence", 10
                ))) {
            throw new CannotFixException(this, CannotFixException.Reason.NOT_ENOUGH_RESOURCES);
        }
        chooseResourceToUse();
        numCrafted = 0;
        timesFixedSpaceOven++;
        OrbFactory.getOvensThatCannotBeFixed().remove(this);

    }

    private void chooseResourceToUse() {
        if (getResourceStorage().hasEnoughResource("liquid silver", RESOURCE_AMOUNT_40)) {
            getResourceStorage().takeResource("liquid silver", RESOURCE_AMOUNT_40);
        } else if (getResourceStorage().hasEnoughResource("star essence", 10)) {
            getResourceStorage().takeResource("star essence", 10);
        }
    }

    @Override
    public int getTimesFixed() {
        return timesFixedSpaceOven;
    }

}
