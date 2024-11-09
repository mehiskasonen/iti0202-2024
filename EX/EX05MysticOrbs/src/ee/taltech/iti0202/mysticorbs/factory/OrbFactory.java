package ee.taltech.iti0202.mysticorbs.factory;

import ee.taltech.iti0202.mysticorbs.exceptions.CannotFixException;
import ee.taltech.iti0202.mysticorbs.orb.Orb;
import ee.taltech.iti0202.mysticorbs.oven.MagicOven;
import ee.taltech.iti0202.mysticorbs.oven.Oven;
import ee.taltech.iti0202.mysticorbs.oven.SpaceOven;
import ee.taltech.iti0202.mysticorbs.storage.ResourceStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrbFactory {

    private static ResourceStorage resourceStorage;
    private List<Oven> ovens = new ArrayList<>();
    private List<Oven> workingOvens = new ArrayList<>();

    private static List<Oven> canNotFixOvens = new ArrayList<>();
    private List<Optional<Orb>> orbsProduced = new ArrayList<Optional<Orb>>();

    private int producedOrbs = 0;


    /**
     * Class constructor that gets a reference to resourceStorage.
     *
     */
    public OrbFactory(ResourceStorage resourceStorage) {
        OrbFactory.resourceStorage = resourceStorage;
    }

    /**
     * Adds an oven to the list of ovens that can not be fixed.
     * @param o oven to be added.
     */
    public static void addToCanNotFixOvens(Oven o) {
        if (!canNotFixOvens.contains(o)) {
            canNotFixOvens.add(o);
        }
    }

    /**
     * Add an oven to the factory list of ovens. Only ovens can be added that share the same ResourceStorage that the
     * factory has and ovens that are not already in the list.
     *
     * @param oven object.
     */
    public void addOven(Oven oven) {
        if (oven.getResourceStorage() == resourceStorage && !ovens.contains(oven)) {
            ovens.add(oven);
        }
    }

    /**
     * Return a list that has all working oven of this factory. List has to keep an order by insertion.
     *
     * @return list of ovens.
     */
    public List<Oven> getOvens() {
        return ovens;
    }


    /**
     * Return and empty the list that holds all the produced orbs.
     * If new orbs have not been produced, returns an empty list.
     *
     * @return list.
     */
    public List<Orb> getAndClearProducedOrbsList() {
        List<Orb> orbs = orbsProduced
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        orbsProduced.clear();
        return orbs;
    }

    /**
     * Activates all the ovens one time. Ovens have to work in the same order as they were added.
     * All the produced orbs have to be put into a list that can be called by getAndClearProducedOrbsList().
     * Orbs have to be in the same order as was the order of ovens working.
     * Return the number of produced orbs.
     *
     * @return integer.
     */
    public int produceOrbs() {
        for (Oven oven : ovens) {
            if (oven.isBroken() && oven instanceof MagicOven) {
                try {
                    ((MagicOven) oven).fix();
                } catch (CannotFixException e) {
                    if (e.getReason() == CannotFixException.Reason.FIXED_MAXIMUM_TIMES) {
                        OrbFactory.addToCanNotFixOvens(oven);
                    }
                    System.out.println("Cannot fix MagicOven: " + e.getReason());
                }
            }
            if (oven.isBroken() && oven instanceof SpaceOven) {
                if (((SpaceOven) oven).getTimesFixed() < 5) {
                    try {
                        ((SpaceOven) oven).fix();
                    } catch (CannotFixException e) {
                        System.out.println("Cannot fix SpaceOven: " + e.getReason());
                    }
                }
            }
            if (oven.isBroken() && (!(oven instanceof SpaceOven) && !(oven instanceof MagicOven))) {
                OrbFactory.addToCanNotFixOvens(oven);
            } else {
                Optional<Orb> newOrb = oven.craftOrb();
                if (newOrb.isPresent()) {
                    producedOrbs++;
                    orbsProduced.add(newOrb);
                }
                System.out.println("Number of orbs produced" + oven.getName() + ": " + producedOrbs);
            }
        }
        getRidOfOvensThatCannotBeFixed();
        return producedOrbs;

    }

    /**
     * Runs produceOrbs() method 'cycles' times.
     *
     * @param cycles integer.
     * @return integer.
     */
    public int produceOrbs(int cycles) {
        for (int i = 0; i < cycles; i++) {
            produceOrbs();
        } return producedOrbs;
    }

    /**
     * @return list of ovens that can not be fixed.
     */
    public static List<Oven> getOvensThatCannotBeFixed() {
        return canNotFixOvens;
    }

    /**
     * Remove unfixable ovens from factories ovens list.
     * Ovens that have been fixed maximum amount of times are unfixable:
     * e.g. regular ovens and magic ovens.
     */
    public void getRidOfOvensThatCannotBeFixed() {
        ovens.removeAll(canNotFixOvens);
        canNotFixOvens.clear();
    }

    /**
     * Organises the ovens so that ovens with higher priority are first.
     * After reorganisation the ovens have to work in the new order.
     * Hint (sorting algorithm does not need to be implemented. Ovens can be
     * comparable to each other).
     */
    public void optimizeOvensOrder() {
        Collections.sort(ovens, Collections.reverseOrder());
    }
}
