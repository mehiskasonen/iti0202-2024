package ee.taltech.iti0202.lotr;

import java.util.Objects;

public class Person {
    private final String personRace;
    private final String personName;
    private Ring ring;

    /**
     * Person main constructor, defined by race, name and ring.
     * @param race of person
     * @param name of person
     * @param ring object
     */
    public Person(String race, String name, Ring ring) {
        personRace = race;
        personName = name;
        this.ring = ring;
    }

    /**
     * Person alternate constructor without ring type.
     * @param race of person.
     * @param name of person.
     */
    public Person(String race, String name) {
        this.personRace = race;
        this.personName = name;
    }

    /**
     * Kui tegemist on õiguspärase Sauroniga, tagastab sõne Affirmative.
     * Kui persooni nimi on Sauron, sõrmuse tüüp on õige, aga materjal ei ole õige, tagastab No, the ring is fake!
     * Kui sõrmuse tüüp ja materjal on õiged, aga persooni nimi ei ole õige, tagastab No, he just stole the ring.
     * Kui persooni nimi on Sauron, aga sõrmuse tüüp ei ole õige või sõrmus on null, tagastab
     * No, but he's claiming to be.
     * Muudel juhtudel tagastab No.
     * @return String
     */
    public String isSauron() {
        if (this.ring != null) {
            if (Objects.equals(personName, "Sauron") && ring.getType() == Ring.Type.THE_ONE
                    && ring.getMaterial() == Ring.Material.GOLD) {
                return "Affirmative";
            }
            if (Objects.equals(personName, "Sauron") && ring.getType() == Ring.Type.THE_ONE
                    && ring.getMaterial() != Ring.Material.GOLD) {
                return "No, the ring is fake!";
            }
            if (ring.getType() == Ring.Type.THE_ONE && ring.getMaterial() == Ring.Material.GOLD
                    && !Objects.equals(personName, "Sauron")) {
                return "No, he just stole the ring";
            }
            if (Objects.equals(personName, "Sauron")
                    && (ring.getType() != Ring.Type.THE_ONE)) {
                return "No, but he's claiming to be";
            }
        } else if (Objects.equals(personName, "Sauron")) {
            return "No, but he's claiming to be";
        }
        return "No";
    }


    /**
     * Setter for setting ring to a person.
     * @param ring object.
     */
    public void setRing(Ring ring) {
        this.ring = ring;
    }
    /**
     * Get race of person object.
     * @return Object race.
     */
    public String getRace() {
        return personRace;
    }

    /**
     * Get name of person object.
     * @return Object name.
     */
    public String getName() {
        return personName;
    }

    /**
     * Get ring object.
     * @return ring.
     */
    public Ring getRing() {
        return ring;
    }
}
