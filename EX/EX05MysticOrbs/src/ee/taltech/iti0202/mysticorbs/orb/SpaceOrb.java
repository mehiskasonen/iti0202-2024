package ee.taltech.iti0202.mysticorbs.orb;

public class SpaceOrb extends Orb {

    // Nõue: Energia väärtuse hoidmiseks ei tohi uut globaalset muutujat teha,
    // vaid on vaja muuta ülemklassi muutuja väärtus.
    // Kuna sellel on nähtavus private, siis seda alamklassis muuta ei saa.
    // Võib olla võiks panna sellele kõrgem nähtavus?
    // Et näiteks kõik klassid selles paketis saaks seda muutujat näha/muuta.

    /**
     * Constructor that gives orb the Oven name that it was produced in.
     * Energy gets value of 100.
     *
     * @param creator string.
     */
    public SpaceOrb(String creator) {
        super(creator);
        orbMap.put(this, 100);
    }

    /**
     * This method does nothing. This type of Orb (SpaceOrb) can not be loaded with energy.
     *
     * @param resource string.
     * @param amount   integer.
     */
    @Override
    public void charge(String resource, int amount) {

    }

    /**
     * toString method that returns a line in the form of "SpaceOrb by [creator]", where creator is the name of the oven
     * that has produced that orb.
     *
     * @return string.
     */
    @Override
    public String toString() {
        return "SpaceOrb by " + creator;
    }

    /**
     * Method that absorbs orb.
     * Orb can only be absorbed if the orb has less energy than the absorber.
     * Energy of the Orb that has been absorbed goes to 0.
     * Absorber energy increases by the amount of energy absorbed.
     * Return true if orb is absorbed, else return false.
     *
     * @param orb object.
     * @return boolean.
     */
    public boolean absorb(Orb orb) {
        int absorbEnergy = this.getEnergy();
        int orbEnergy = orb.getEnergy();
        if (absorbEnergy > orbEnergy) {
            orbMap.put(orb, 0);
            orbMap.put(this, orbEnergy + absorbEnergy);
            return true;
        } else {
            return false;
        }
    }
}
