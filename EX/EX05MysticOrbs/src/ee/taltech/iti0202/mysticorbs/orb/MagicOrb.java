package ee.taltech.iti0202.mysticorbs.orb;

public class MagicOrb extends Orb {

    /**
     * Constructor that gives MagicOrb the Oven name that it was produced in.
     * @param creator string.
     */
    public MagicOrb(String creator) {
        super(creator);
        orbMap.put(this, 0);
    }

    /**
     * Same method that Orb class has, but the orb gets 2x more energy.
     * @param resource string.
     * @param amount integer.
     */
    @Override
    public void charge(String resource, int amount) {
        super.charge(resource, amount * 2);
    }

    /**
     * toString method that returns a line in the form of "MagicOrb by [creator]", where creator is the name of the
     * oven that has produced that orb.
     * @return string.
     */
    @Override
    public String toString() {
        return "MagicOrb by " + this.creator;
    }
}
