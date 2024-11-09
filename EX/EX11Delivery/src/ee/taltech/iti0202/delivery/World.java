package ee.taltech.iti0202.delivery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Represents the world in which courier actions and movements are simulated.
 */
public class World {
    public Map<String, Location> getLocationMap() {
        return locationMap;
    }

    private Map<String, Location> locationMap = new HashMap<>();
    private Map<String, Courier> courierMap = new HashMap<>();
    private Map<Courier, String> courierLocationMap = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(World.class.getName());

    /**
     * Adds a location to the world.
     *
     * @param name            The name of the location.
     * @param otherLocations The names of other locations.
     * @param distances       The distances to other locations.
     * @return An optional containing the added location.
     */
    public Optional<Location> addLocation(String name, List<String> otherLocations, List<Integer> distances) {
        if (locationMap.containsKey(name)) {
            LOGGER.warning("World already has location " + name);
            return Optional.empty();
        }
        if (!locationMap.isEmpty() && (otherLocations.size() != distances.size())) {
            LOGGER.warning("Different amount of other locations and distances.");
            return Optional.empty();
        }
        if (!locationMap.isEmpty() && (otherLocations.isEmpty())) {
            LOGGER.warning("No other locations given.");
            return Optional.empty();
        }

        Iterator<String> locationIterator = otherLocations.iterator();
        Iterator<Integer> distanceIterator = distances.iterator();

        while (locationIterator.hasNext() && distanceIterator.hasNext()) {
            String otherLocation = locationIterator.next();
            if (!locationMap.containsKey(otherLocation)) {
                locationIterator.remove();
                distanceIterator.next();
                distanceIterator.remove();
            } else {
                distanceIterator.next();
            }
        }

        if (locationMap.isEmpty() && (otherLocations.isEmpty() && distances.isEmpty())) {
            Location location = new Location(name);
            locationMap.put(name, location);
            LOGGER.info("Initial location" + location + " added to world.");
            return Optional.of(location);
        } else {
            if (!locationMap.isEmpty() && otherLocations.size() < locationMap.size()) {
                LOGGER.warning("Not enough other locations given.");
                return Optional.empty();
            }
/*            Optional<Boolean> validation = validateLocationAndDistance(name, otherLocations, distances);
            if (validation.isEmpty()) {
                return Optional.empty();
            }*/
            if (locationMap.isEmpty() && otherLocations.size() == 1) {
                //Create self location
                Location locationSelf = new Location(name);
                locationSelf.addDistance(otherLocations.get(0), distances.get(0));
                locationMap.put(name, locationSelf);

                //Create other location
                Location locationOther = new Location(otherLocations.get(0));
                locationOther.addDistance(name, distances.get(0));
                locationMap.put(locationOther.getName(), locationOther);
                LOGGER.info("Initial two locations added to world. Bi-directional maps created.");
                return Optional.of(locationSelf);
            }
            int addedLocations = 0;
            Location location = new Location(name);
            for (int i = 0; i < otherLocations.size(); i++) {
                String otherLocation = otherLocations.get(i);
                int distance = distances.get(i);
                location.addDistance(otherLocation, distance);
                locationMap.get(otherLocation).addDistance(name, distance);
                locationMap.put(name, location);
                addedLocations++;
            }
            LOGGER.info("Location (" + location.getName() + ") added successfully to world.");
            locationMap.put(name, location);
            return Optional.of(location);
        }
    }


    private Optional<Boolean> validateLocationAndDistance(String name, List<String> otherLocations,
                                                          List<Integer> distances) {
/*        if (otherLocations.isEmpty() && distances.isEmpty()
                && locationMap.isEmpty()) {
            LOGGER.info("Validation successful - locationMap was empty.");
            return Optional.of(true);
        }*/

        LOGGER.info("Validation successful - locationMap was not empty.");
        return Optional.of(true);
    }

    /**
     * Simulates the passage of time in the world.
     * If courier is moving between two locations, he must be moved one unit towards his destination.
     * If courier is starting from a location, he must:
     *      1) ask strategy for a new action
     *      2) exchange packages with his current location
     *      3) move one unit towards his new location.
     */
    public void tick() {
        for (Courier courier: courierMap.values()) {
            courier.getLocation().ifPresentOrElse(
                    location -> {
                        Action action = courier.getStrategy().getAction();
                        LOGGER.info("Courier " + courier.getName() + " at location " + location.getName()
                                + " got action to move to " + action.getGoTo().getName());
                        courier.setNextLocation(action.getGoTo());
                        for (String packetName : action.getDeposit()) {
                            Optional<Packet> packet = courier.getCourierPackets().stream()
                                    .filter(p -> p.getName().equals(packetName))
                                    .findFirst();
                            packet.ifPresent(courier.getCourierPackets()::remove);
                            packet.ifPresent(location::addPacket);
                            LOGGER.info("Courier " + courier.getName() + " deposited packet " + packetName
                                    + " at location " + location.getName());
                        }
                        for (String packetName : action.getTake()) {
                            Optional<Packet> packet = location.getPacket(packetName);
                            packet.ifPresent(courier.getCourierPackets()::add);
                            LOGGER.info("Courier " + courier.getName() + " took packet " + packetName
                                    + " from location " + location.getName());
                        }
                        moveCourier(courier, action);
                    },
                    () -> {
                        LOGGER.info("Courier " + courier.getName() + " is not at any location. Moving");
                        simpleMove(courier, courier.getNextLocation());
                    });
        }
    }

    /**
     * @param courier to move
     * @param location to move to.
     */
    public void simpleMove(Courier courier, Location location) {
        Location lastLocation = getCourierLastLocation(courier);
        int distanceToNextLocation = lastLocation.getDistanceTo(location.getName());
        if (distanceToNextLocation != courier.getDistanceFromLocation()) {
            courier.setHasStartedmoving(true);
            courier.setLocation(null);
            int distance = courier.getDistanceFromLocation() + 1;
            LOGGER.info("Courier " + courier.getName() + " moved by " + distance + " towards "
                    + location.getName());
            courier.setDistanceFromLocation(distance);
        }
        if (distanceToNextLocation == courier.getDistanceFromLocation()) {
            courier.setHasStartedmoving(false);
            courier.setLocation(location);
            courier.setDistanceFromLocation(0);
            LOGGER.info("Courier " + courier.getName() + " arrived at location " + location.getName());
        }
    }


    /**
     * get next strategy location
     * get the distance
     * if distance is not 1:
     *     decrement counter to distance
     * if decrement result is 0:
     *     set new location name to current location
     * @param courier to move
     */
    public void moveCourier(Courier courier, Action action) {
        Location lastLocation = getCourierLastLocation(courier);
        Location nextLocation = action.getGoTo();
        int distanceToNextLocation = lastLocation.getDistanceTo(nextLocation.getName());
        if (distanceToNextLocation != courier.getDistanceFromLocation()) {
            courier.setHasStartedmoving(true);
            courier.setLocation(null);
            int distance = courier.getDistanceFromLocation() + 1;
            LOGGER.info("Courier " + courier.getName() + " moved by " + distance + " towards "
                    + nextLocation.getName());
            courier.setDistanceFromLocation(distance);
        }
        if (distanceToNextLocation == courier.getDistanceFromLocation()) {
            courier.setHasStartedmoving(false);
            courier.setLocation(nextLocation);
            courier.setDistanceFromLocation(0);
            LOGGER.info("Courier " + courier.getName() + " arrived at location " + nextLocation.getName());
        }
    }

    /**
     * Adds a strategy to an appropriate courier with the given name.
     *
     * @param name     The name of the courier.
     * @param strategy The strategy to be added.
     * @return True if the strategy is successfully added, false otherwise.
     */
    public boolean giveStrategy(String name, Strategy strategy) {
        if (name == null || !courierMap.containsKey(name) || name.isEmpty()) {
            return false;
        } else {
            courierMap.get(name).setStrategy(strategy);
            return true;
        }
    }

    /**
     * Adds a courier to the world.
     * If distance to every other destination is not given or destination
     * with name already exists, return Optional.empty()
     * @param name The name of the courier.
     * @param to   The destination for the courier.
     * @return An optional containing the added courier.
     */
    public Optional<Courier> addCourier(String name, String to) {
        if (courierMap.containsKey(name) || !locationMap.containsKey(to)) {
            return Optional.empty();
        } else {
            Courier courier = new Courier(name);
            courier.setLocation(locationMap.get(to));
            courierMap.put(name, courier);
            courierLocationMap.put(courier, to);
            return Optional.of(courier);
        }
    }

    /**
     * @param courier to track
     * @return couriers last location.
     */
    public Location getCourierLastLocation(Courier courier) {
        String lastLocation = courierLocationMap.get(courier);
        return locationMap.get(lastLocation);
    }

}
