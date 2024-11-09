package ee.taltech.iti0202.delivery;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @Test
    public void testWorldAddLocationFirstWithOtherLocation() {
        World world = new World();
        Optional<Location> result = world.addLocation("A", List.of("B"), List.of(1));
        assertTrue(world.getLocationMap().containsKey("A"));
        assertTrue(world.getLocationMap().containsKey("B"));
    }

/*    @Test
    public void testWorldAddLocationFirstWithOtherLocations() {
        World world = new World();
        Optional<Location> result = world.addLocation("A", List.of("B", "C"), List.of(1, 2));
        assertTrue(world.getLocationMap().containsKey("A"));
        //assertTrue(world.getLocationMap().containsKey("B"));
    }*/

    @Test
    public void testWorldAddLocationReturnsEmptyIfDistancesNotGivenForAll() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Optional<Location> result = world.addLocation("B", List.of(), List.of());
        assertFalse(result.isPresent());
        assertFalse(world.getLocationMap().containsKey("B"));
        assertTrue(world.getLocationMap().containsKey("A"));
    }

    @Test
    public void testWorldAddLocationFirstEmpty() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        assertTrue(world.getLocationMap().containsKey("A"));
    }

    @Test
    public void testWorldAddLocationNoSuchSecondOther() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Location B = world.addLocation("B", List.of("A", "C"), List.of(1, 2)).get();
        assertTrue(world.getLocationMap().containsKey("A"));
        assertTrue(world.getLocationMap().containsKey("B"));
        assertTrue(B.getDistanceMap().containsKey("A"));
        assertTrue(A.getDistanceMap().containsKey("B"));

        assertFalse(world.getLocationMap().containsKey("C"));
        assertFalse(A.getDistanceMap().containsKey("C"));
        assertFalse(B.getDistanceMap().containsKey("C"));

    }


/*    @Test
    public void testWorldAddLocationEmptyMapNoSuchOtherLocations() {
        World world = new World();
        Optional<Location> result = world.addLocation("C", List.of("V", "G", "H"), List.of(1, 2, 3));
        assertTrue(result.isPresent());
        assertTrue(world.getLocationMap().containsKey("C"));
    }*/

    @Test
    public void testWorldAddLocationNoSuchOtherLocationsButHasLast() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Optional<Location> result = world.addLocation("C", List.of("V", "G", "H", "A"), List.of(1, 2, 3, 4));

        assertTrue(result.isPresent());
        assertTrue(world.getLocationMap().containsKey("C"));
        assertTrue(A.getDistanceMap().containsKey("C"));
        assertTrue(result.get().getDistanceMap().containsKey("A"));
    }

    @Test
    public void testWorldAddLocationNoSuchOtherLocationsButHasFirst() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Optional<Location> result = world.addLocation("C", List.of("A", "G", "H"), List.of(1, 2, 3));

        assertTrue(result.isPresent());
        assertTrue(world.getLocationMap().containsKey("C"));
        assertTrue(A.getDistanceMap().containsKey("C"));
        assertTrue(result.get().getDistanceMap().containsKey("A"));
    }

    @Test
    public void testWorldAddLocationNoSuchOtherLocationsWorldMapHasOne() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Optional<Location> result = world.addLocation("C", List.of("V", "G", "H"), List.of(1, 2, 3));

        assertFalse(result.isPresent());
        assertFalse(world.getLocationMap().containsKey("C"));
        assertFalse(A.getDistanceMap().containsKey("C"));
        assertTrue(world.getLocationMap().containsKey("A"));
    }

    @Test
    public void testWorldAddLocationGiveMoreLocationsThanExists__NonExistentShouldBeIgnored1() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Location B = world.addLocation("B", List.of("A"), List.of(2)).get();
        Optional<Location> result = world.addLocation("C", List.of("A", "D", "B"), List.of(2, 3, 2));

        assertFalse(world.getLocationMap().containsKey("D"));
        assertTrue(A.getDistanceMap().containsKey("B"));
        assertTrue(result.isPresent());
        assertTrue(world.getLocationMap().containsKey("C"));
        assertTrue(A.getDistanceMap().containsKey("C"));
        assertFalse(result.get().getDistanceMap().containsKey("D"));
    }


    @Test
    public void testWorldAddLocationOtherLocationsDontMatch() {
        World world = new World();
        Location A = world.addLocation("A", new ArrayList<>(), new ArrayList<>()).get();
        Optional<Location> location = world.addLocation("B", List.of("C"), List.of(1));
        assertFalse(location.isPresent());
        assertFalse(A.getDistanceMap().containsKey("B"));
        assertFalse(world.getLocationMap().containsKey("B"));
    }

    @Test
    public void testWorldAddLocationReturnsEmptyIfDistancesAreMissing() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Location B = world.addLocation("B", List.of("A"), List.of(2)).get();
        Optional<Location> result = world.addLocation("C", List.of("A", "B", "D"), List.of(2, 3));
        assertFalse(result.isPresent());
        assertFalse(A.getDistanceMap().containsKey("D"));
        assertTrue(A.getDistanceMap().containsKey("B"));
    }

    @Test
    public void testWorldAddLocationOtherLocationsDontMatchDoneAlready() {
        World world = new World();
        Location A = world.addLocation("A", new ArrayList<>(), new ArrayList<>()).get();
        assertFalse(world.addLocation("B", List.of("C"), List.of(1)).isPresent());
        assertFalse(A.getDistanceMap().containsKey("B"));
        assertFalse(world.getLocationMap().containsKey("B"));
    }

    @Test
    public void testWorldAddLocationGiveMoreLocationsThanExists__NonExistentShouldBeIgnored() {
        World world = new World();
        Location A = world.addLocation("A", new ArrayList<>(), new ArrayList<>()).get();
        Location B = world.addLocation("B", List.of("A"), List.of(2)).get();
        assertTrue(world.addLocation("C", List.of("A", "B", "D"), List.of(2, 3, 2)).isPresent());
        assertFalse(world.getLocationMap().containsKey("D"));
        assertFalse(A.getDistanceMap().containsKey("D"));
        assertFalse(B.getDistanceMap().containsKey("D"));
    }

    @Test
    public void testWorldAddLocationSameNameAsExisting() {
        World world = new World();
        Location A = world.addLocation("A", new ArrayList<>(), new ArrayList<>()).get();
        Optional<Location> result = world.addLocation("A", List.of("B"), List.of(1));
        assertFalse(result.isPresent());
        assertTrue(world.getLocationMap().containsKey("A"));
        assertEquals(0, A.getDistanceMap().size());
    }

    @Test
    public void testWorldAddLocationNoDistanceToEveryOtherLocation() {
        World world = new World();
        Optional<Location> resultA = world.addLocation("A", List.of(), List.of());
        Optional<Location> resultB = world.addLocation("B", List.of("A"), List.of(1));
        Optional<Location> resultC = world.addLocation("C", List.of("A"), List.of(2)); //Can't add because dist to B is missing.
        assertTrue(resultA.isPresent());

        assertTrue(resultB.isPresent());
        assertFalse(resultC.isPresent());
        assertEquals(2, world.getLocationMap().size()); // Only A should be added

        Location locationA = world.getLocationMap().get("A");
        assertEquals(1, locationA.getDistanceTo("B")); // Distance to B should be 1
        assertTrue(locationA.getDistanceMap().containsKey("B"));
    }

    @Test
    public void testWorldAddLocationOtherLocationsDontMatch2() {
        World world = new World();
        Location A = world.addLocation("A", new ArrayList<>(), new ArrayList<>()).get(); // Add initial location

        // Attempt to add location "B" with a dependency "C" that doesn't exist in the world
        Optional<Location> locationB = world.addLocation("B", List.of("C"), List.of(1));

        assertFalse(locationB.isPresent()); // The location should not be added
        assertFalse(A.getDistanceMap().containsKey("B"));
        assertFalse(world.getLocationMap().containsKey("B"));
    }

    @Test
    public void testWorldAddLocationCircularDependencies() {
        World world = new World();
        Optional<Location> resultA = world.addLocation("A", List.of("B"), List.of(1));
        Optional<Location> resultB = world.addLocation("B", List.of("C"), List.of(1)); //Can't add because B already created.
        Optional<Location> resultC = world.addLocation("C", List.of("A"), List.of(1)); //Can't add because dist to B is missing.
        assertTrue(resultA.isPresent());

        assertFalse(resultB.isPresent());
        assertFalse(resultC.isPresent());
        assertEquals(2, world.getLocationMap().size()); // Only A should be added

        Location locationA = world.getLocationMap().get("A");
        assertEquals(1, locationA.getDistanceTo("B")); // Distance to B should be 1
        assertTrue(locationA.getDistanceMap().containsKey("B"));
    }

    @Test
    public void testWorldAddLocationGiveMoreLocationsThanExists__NonExistentShouldBeIgnored2() {
        World world = new World();
        Location locationA = world.addLocation("A", List.of(), List.of()).get(); // Add initial location
        Optional<Location> result = world.addLocation("B", List.of("A", "C", "D"), List.of(2, 3, 4));
        assertTrue(result.isPresent());
        assertFalse(world.getLocationMap().containsKey("D"));
        assertFalse(world.getLocationMap().containsKey("C"));

    }

    @Test
    public void testWorldAddLocationGiveMoreLocationsThanExists__NonExistentShouldBeIgnored3() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Optional<Location> resultB = world.addLocation("B", List.of("C"), List.of(1));
        Optional<Location> resultC = world.addLocation("C", List.of("D"), List.of(1));
        assertFalse(resultB.isPresent()); // Location B should not be added due to non-existent dependency
        assertFalse(resultC.isPresent()); // Location C should not be added due to non-existent dependency
    }

    @Test
    public void testWorldAddLocationGiveMoreLocationsThanExists__NonExistentShouldBeIgnored4() {
        World world = new World();
        Location A = world.addLocation("A", List.of(), List.of()).get();
        Location B = world.addLocation("B", List.of("A"), List.of(1)).get();
        Optional<Location> resultC = world.addLocation("C", List.of("A", "B", "D"), List.of(1, 1, 1));
        assertTrue(resultC.isPresent()); // Location C should still be added even with non-existent dependency
        assertTrue(world.getLocationMap().containsKey("C")); // Location C should be in the world's location map
    }

    @Test
    public void testCourierCanFollowGoToCommand2() {
        World world = new World();
        Location start = world.addLocation("start", new ArrayList<>(), new ArrayList<>()).get();
        Location end = world.addLocation("end", Collections.singletonList("start"), Collections.singletonList(3)).get();
        Courier courier = world.addCourier("Courier", "start").get();

        LinkedList<Action> actions = new LinkedList<>();
        actions.add(new Action(end));
        actions.add(new Action(start));
        Strategy strategy = new DummyStrategy(actions);
        world.giveStrategy("Courier", strategy);

        Optional<Location> location1 = courier.getLocation();
        assertTrue(location1.isPresent());
        assertEquals(start, location1.get());

        world.tick();
        Optional<Location> location2 = courier.getLocation();
        assertTrue(location2.isEmpty());

        world.tick();
        Optional<Location> location3 = courier.getLocation();
        assertTrue(location3.isEmpty());

        world.tick();
        Optional<Location> location4 = courier.getLocation();
        assertTrue(location4.isPresent());
        assertEquals(end, location4.get());
    }

    @Test
    public void testCourierCanFollowGoToCommand() {
        World world = new World();
        Location locationA = world.addLocation("A", List.of(), List.of()).get();
        Location locationB = world.addLocation("B", List.of("A"), List.of(2)).get();
        Courier courier = new Courier("Courier");
        courier.setLocation(locationA);

        Packet packetTallinn1 = new Packet("tal1", locationA);

        Action goToAction = new Action(locationA);
        goToAction.setGoTo(locationB);
        Strategy strategy = new DummyStrategy(List.of(goToAction, goToAction));

        // Set the courier's action to the goTo action
        world.giveStrategy("Courier", strategy);

        // Tick the courier to execute the action
        world.tick();

        // Assert that the courier's location is now locationB
        assertEquals(locationA, courier.getLocation().get());
    }
}