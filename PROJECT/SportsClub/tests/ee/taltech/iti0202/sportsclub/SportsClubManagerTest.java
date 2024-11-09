package ee.taltech.iti0202.sportsclub;

import ee.taltech.iti0202.sportsclub.sportsclub.SportsClub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SportsClubManagerTest {

    private SportsClubManager manager = new SportsClubManager();
    private List<SportsClub> clubsToAdd;

    @BeforeEach
    void setUp() {
        clubsToAdd = List.of(
                new SportsClub("Club 1"),
                new SportsClub("Club 2"),
                new SportsClub("Club 3")
        );
        manager.addManySportsClubs(clubsToAdd);
    }

    @Test
    void testAddSportsClub() {
        SportsClub club4 = new SportsClub("Club4");
        manager.addSportsClub(club4);
        assertEquals(club4, manager.getSportsClubs().get(3));
    }

    @Test
    void testAddSportsClubAlreadyContainsClub() {
        SportsClub club1 = new SportsClub("Club1");
        manager.addSportsClub(club1);
        assertFalse(manager.addSportsClub(club1));
    }

    @Test
    void testAddManySportsClubsCorrectSize() {
        SportsClub club4 = new SportsClub("Club4");
        SportsClub club5 = new SportsClub("Club5");
        List<SportsClub> clubsToAdd = List.of(club4, club5);
        manager.addManySportsClubs(clubsToAdd);
        assertEquals(5, manager.getSportsClubs().size());
    }

    @Test
    void testAddManySportsClubsCorrectPlacement() {
        SportsClub club4 = new SportsClub("Club4");
        SportsClub club5 = new SportsClub("Club5");
        List<SportsClub> clubsToAdd = List.of(club4, club5);
        manager.addManySportsClubs(clubsToAdd);
        assertEquals(club4, manager.getSportsClubs().get(3));
    }

    @Test
    void testAddManySportsClubsDontAddRepeating() {
        SportsClub club4 = new SportsClub("Club4");
        SportsClub club5 = new SportsClub("Club5");
        SportsClub club6 = new SportsClub("Club6");
        List<SportsClub> clubsToAdd = List.of(club4, club5, club4, club6);
        manager.addManySportsClubs(clubsToAdd);
        assertEquals(6, manager.getSportsClubs().size());
    }

    @Test
    void testRemoveOneSportsClub() {
        manager.removeOneSportsClub(manager.getSportsClubs().get(0));
        assertEquals(2, manager.getSportsClubs().size());
    }

    @Test
    void testRemoveOneSportsClubNotPresent() {
        SportsClub club4 = new SportsClub("Club4");
        assertFalse(manager.removeOneSportsClub(club4));
    }

    @Test
    void testRemoveManySportsClubsSize() {
        List<SportsClub> toRemove = List.of(manager.getSportsClubs().get(0), manager.getSportsClubs().get(1));
        manager.removeManySportsClubs(toRemove);
        assertEquals(1, manager.getSportsClubs().size());
    }

    @Test
    void testRemoveManySportsClubsCorrectClubLeft() {
        SportsClub expected = manager.getSportsClubs().get(2);
        List<SportsClub> toRemove = List.of(manager.getSportsClubs().get(0), manager.getSportsClubs().get(1));
        manager.removeManySportsClubs(toRemove);
        assertEquals(expected, manager.getSportsClubs().get(0));
    }
}
