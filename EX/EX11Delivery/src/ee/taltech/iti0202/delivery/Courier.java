package ee.taltech.iti0202.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a courier in the delivery system that can move between locations and execute delivery strategies.
 */
public class Courier {

    private String name;
    private Location location;

    public Location getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(Location nextLocation) {
        this.nextLocation = nextLocation;
    }

    private Location nextLocation;
    private Strategy strategy;
    private List<Packet> courierPackets = new ArrayList<>();

    private boolean hasStartedMoving = false;

    private int distanceFromLocation = 0;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private World world;


    /**
     * Constructs a new courier with the specified name.
     *
     * @param name The name of the courier.
     */
    public Courier(String name) {
        this.name = name;
    }


    /**
     * Gets the list of packets currently held by the courier.
     *
     * @return The list of packets held by the courier.
     */
    public List<Packet> getCourierPackets() {
        return courierPackets;
    }

    /**
     * Sets the list of packets held by the courier.
     *
     * @param courierPackets The new list of packets to be held by the courier.
     */
    public void setCourierPackets(List<Packet> courierPackets) {
        this.courierPackets = courierPackets;
    }


    /**
     * Returns a string representation of the courier, including its name, current location, and held packets.
     *
     * @return A string representation of the courier.
     */
    @Override
    public String toString() {
        if (location == null) {
            return name + "(null) " + "PACKETS: " + getCourierPackets();
        }
        return name + "(" + location.getName() + ") " + "PACKETS: " + getCourierPackets();
    }

    /**
     * Sets the current location of the courier.
     *
     * @param location The new location of the courier.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns an optional containing the current location of the courier.
     * If the courier is not at any location, returns Optional.empty().
     *
     * @return An optional containing the current location of the courier.
     */
    public Optional<Location> getLocation() {
        if (location == null) {
            return Optional.empty();
        } else {
            return Optional.of(location);
        }
    }

    /**
     * Sets the strategy for the courier to follow during its movements.
     *
     * @param strategy The strategy to be set for the courier.
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the strategy currently appointed to the courier.
     *
     * @return The strategy appointed to the courier.
     */
    public Strategy getStrategy() {
        return strategy;
    }

    public String getName() {
        return name;
    }

    public int getDistanceFromLocation() {
        return distanceFromLocation;
    }

    public void setDistanceFromLocation(int distanceFromLocation) {
        this.distanceFromLocation = distanceFromLocation;
    }

    public void setHasStartedmoving(boolean hasStartedMoving) {
        this.hasStartedMoving = hasStartedMoving;
    }

    public boolean isHasStartedMoving() {
        return hasStartedMoving;
    }

}
