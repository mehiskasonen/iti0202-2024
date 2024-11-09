package ee.taltech.iti0202.sportsclub;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The SportsClubManager class manages a collection of sports clubs.
 * It provides methods for adding and removing individual sports clubs
 * or multiple sports clubs at once.
 */
public class SportsClubManager {

    private List<SportsClub> sportsClubs;

    /**
     * Constructs a new SportsClubManager with an empty list of sports clubs.
     */
    public SportsClubManager() {
        this.sportsClubs = new ArrayList<>();
    }


    /**
     * @return list of sports clubs.
     */
    public List<SportsClub> getSportsClubs() {
        return sportsClubs;
    }

    /**
     * Adds a single sports club to the manager's collection.
     *
     * @param club The sports club to add.
     * @return {@code true} if the sports club was added successfully,
     *         {@code false} if the sports club is already in the collection.
     */
    public boolean addSportsClub(SportsClub club) {
        if (!sportsClubs.contains(club)) {
            sportsClubs.add(club);
            return true;
        }
        return false;
    }

    /**
     * Adds multiple sports clubs to the manager's collection.
     *
     * @param clubs The collection of sports clubs to add.
     * @return {@code true} if any sports club was added successfully,
     *         {@code false} if all sports clubs are already in the collection.
     */
    public boolean addManySportsClubs(Collection<SportsClub> clubs) {
        boolean added = false;
        for (SportsClub club : clubs) {
            if (addSportsClub(club)) {
                added = true;
            }
        }
        return added;
    }

    /**
     * Removes a single sports club from the manager's collection.
     *
     * @param club The sports club to remove.
     * @return {@code true} if the sports club was removed successfully,
     *         {@code false} if the sports club is not in the collection.
     */
    public boolean removeOneSportsClub(SportsClub club) {
        if (!sportsClubs.contains(club)) {
            return false;
        }
        sportsClubs.remove(club);
        return true;
    }

    /**
     * Removes multiple sports clubs from the manager's collection.
     *
     * @param clubs The collection of sports clubs to remove.
     * @return {@code true} if any sports club was removed successfully,
     *         {@code false} if none of the sports clubs are in the collection.
     */
    public boolean removeManySportsClubs(Collection<SportsClub> clubs) {
        boolean removed = false;
        Iterator<SportsClub> iterator = sportsClubs.iterator();
        while (iterator.hasNext()) {
            SportsClub club = iterator.next();
            if (clubs.contains(club)) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }


}
