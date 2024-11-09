package ee.taltech.iti0202.sportsclub.sportsclub.bonuspoints;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import ee.taltech.iti0202.sportsclub.users.Member;

import java.util.HashMap;

/**
 * Represents the bonus points system for a member in different sports clubs.
 * Bonus points can be added, subtracted, and retrieved for each sport club.
 */
public class BonusPoints {
    private Member member;
    private final HashMap<SportsClub, Integer> bonusPoints;

    /**
     * Constructor for the BonusPoints class.
     * Each member has BonusPoints that has a map of sports clubs accompanied by integer
     * amount of points a member has for that club.
     *
     * @param bonusPoints The initial bonus points for the member in each sports club.
     */
    public BonusPoints(HashMap<SportsClub, Integer> bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    /**
     * Adds bonus points to the specified sports club for the member,
     * If member does not have any points with that club, updates the map.
     * If member has points with the club, adds the additional points to map keys value.
     *
     * @param club   The sports club to which bonus points are added.
     * @param amount The amount of bonus points to be added.
     */
    public void addBonusPoints(SportsClub club, int amount) {
        if (bonusPoints.containsKey(club)) {
            int currentPoints = bonusPoints.get(club);
            int updatedPoints = currentPoints + amount;
            bonusPoints.put(club, updatedPoints);
        } else {
            bonusPoints.put(club, amount);
        }
    }

    /**
     * Subtracts bonus points from the specified sports club for the member.
     *
     * @param club   The sports club from which bonus points are subtracted.
     * @param amount The amount of bonus points to be subtracted.
     * @throws IllegalArgumentException If there are not enough bonus points to subtract.
     */
    public void subtractBonusPoints(SportsClub club, int amount) {
        if (bonusPoints.containsKey(club)) {
            int currentPoints = bonusPoints.get(club);
            if (currentPoints >= amount) {
                int updatedPoints = currentPoints - amount;
                bonusPoints.put(club, updatedPoints);
            } else {
                throw new IllegalArgumentException("Not enough bonus points");
            }
        } else {
            throw new IllegalArgumentException("Can not subtract. Club not in users Bonus Point system.");
        }
    }

    /**
     * Retrieves the bonus points for the specified sports club.
     *
     * @param club The sports club for which bonus points are retrieved.
     * @return The bonus points for the specified sports club.
     */
    public int getPoints(SportsClub club) {
         return bonusPoints.getOrDefault(club, 0);
    }

}
