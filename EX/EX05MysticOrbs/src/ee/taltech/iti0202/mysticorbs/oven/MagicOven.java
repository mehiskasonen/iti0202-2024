package ee.taltech.iti0202.mysticorbs.oven;

import ee.taltech.iti0202.mysticorbs.exceptions.CannotFixException;
import ee.taltech.iti0202.mysticorbs.factory.OrbFactory;
import ee.taltech.iti0202.mysticorbs.orb.MagicOrb;
import ee.taltech.iti0202.mysticorbs.storage.ResourceStorage;
import ee.taltech.iti0202.mysticorbs.orb.Orb;

import java.util.Optional;

public class MagicOven extends Oven implements Fixable {
    private int timesFixedMagicOven = 0;
    protected int nrOfMagicOrbsCrafted = 0;

    public static final int RESOURCE_AMOUNT_25 = 25;


    /**
     * Constructor for creating an MagicOven instance. Takes in oven name and reference to ResourceStorage.
     * @param name string.
     * @param resourceStorage object string?
     */
    public MagicOven(String name, ResourceStorage resourceStorage) {
        super(name, resourceStorage);
    }

    /**
     * Return true if the MagicOven is broken, and return true if it is working.
     * @return boolean.
     */
    @Override
    public boolean isBroken() {
        return craftedByMagicOven >= 5;
    }

    /**
     * Creates an Orb if the owen is working and if there are enough resources. If by any chance an orb can
     * not be created, returns an empty Optional.
     * @return empty Optional.
     */
    @Override
    public Optional<Orb> craftOrb() {
        if (craftedByMagicOvenEvenOrNot % 2 == 1
                && getResourceStorage().hasEnoughResource("gold", 1)
                && getResourceStorage().hasEnoughResource("dust", 3)
                && (!isBroken())) {
            Orb orb = new MagicOrb(getName());
            createdOrbsTotal++;
            craftedByMagicOvenEvenOrNot++;
            craftedByMagicOven++;
            nrOfMagicOrbsCrafted++;
            orb.charge("gold", 1);
            orb.charge("dust", 3);
            getResourceStorage().takeResource("gold", 1);
            getResourceStorage().takeResource("dust", 3);
            System.out.println(orb);
            return Optional.of(orb);
        } else {
            return super.craftOrb();
        }
    }

    @Override
    public void fix() throws CannotFixException {
        if (!isBroken()) {
            throw new CannotFixException(this, CannotFixException.Reason.IS_NOT_BROKEN);
        }
        if (getTimesFixed() >= 10) {
            throw new CannotFixException(this, CannotFixException.Reason.FIXED_MAXIMUM_TIMES);
        }
        if (!getResourceStorage().hasEnoughResource("freezing powder", 100
                + (100 * getTimesFixed()))) {
            throw new CannotFixException(this, CannotFixException.Reason.NOT_ENOUGH_RESOURCES);
        }
        if (!getResourceStorage().hasEnoughResource("clay", RESOURCE_AMOUNT_25
                + (RESOURCE_AMOUNT_25 * getTimesFixed()))) {
            throw new CannotFixException(this, CannotFixException.Reason.NOT_ENOUGH_RESOURCES);
        }
        getResourceStorage().takeResource("freezing powder", 100 + (100 * getTimesFixed()));
        getResourceStorage().takeResource("clay", RESOURCE_AMOUNT_25 + (RESOURCE_AMOUNT_25 * getTimesFixed()));
        nrOfMagicOrbsCrafted = 0;
        craftedByMagicOven = 0;
        timesFixedMagicOven++;
        OrbFactory.getOvensThatCannotBeFixed().remove(this);
        System.out.println("MagicOven fixed, timesFixed = " + this.timesFixedMagicOven);
    }

    @Override
    public int getTimesFixed() {
        return timesFixedMagicOven;
    }

    @Override
    public int compareTo(Oven o) {
        return super.compareTo(o);
    }

}
