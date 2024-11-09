package ee.taltech.iti0202.delivery;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a location in the delivery system where packets can be stored and retrieved.
 */
public class Location {
    private Map<String, Integer> distanceMap = new HashMap<>();
    private Map<String, Packet> packets = new HashMap<>();
    private String name;

    /**
     * Constructs a new location with the specified name.
     *
     * @param name The name of the location.
     */
    public Location(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the location, including its name and the packets it contains.
     *
     * @return A string representation of the location.
     */
    @Override
    public String toString() {
        return name + " " + packets;
    }

    /**
     * Gets the name of the location.
     *
     * @return The name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the distance to the set location.
     * If there is no location with the given name, returns Integer.MAX_VALUE.
     *
     * @param name The name of the target location.
     * @return The distance to the target location.
     */
    public Integer getDistanceTo(String name) {
        return distanceMap.getOrDefault(name, Integer.MAX_VALUE);
    }

    /**
     * Adds a packet to the location.
     *
     * @param packet The packet to be added.
     */
    public void addPacket(Packet packet) {
        packets.put(packet.getName(), packet);
    }

    /**
     * Returns a packet with the given name and removes it from the location.
     * If there is no such packet, returns Optional.empty().
     *
     * @param name The name of the packet to retrieve.
     * @return An optional containing the retrieved packet.
     */
    public Optional<Packet> getPacket(String name) {
        if (!packets.containsKey(name)) {
            return Optional.empty();
        }
        Packet resultPacket = packets.get(name);
        packets.remove(name);
        return Optional.ofNullable(resultPacket);
    }

    /**
     * Determines (adds) the distance to a location with a given name.
     *
     * @param location The name of the target location.
     * @param distance The distance to the target location.
     */
    public void addDistance(String location, int distance) {
        if (distance != 0) {
            distanceMap.put(location, distance);
        }
    }

    /**
     * @return distance map.
     */
    public Map<String, Integer> getDistanceMap() {
        return distanceMap;
    }

}
