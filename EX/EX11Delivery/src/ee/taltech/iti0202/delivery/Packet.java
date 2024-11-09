package ee.taltech.iti0202.delivery;

/**
 * Represents a packet that needs to be delivered to a specific location.
 */
public class Packet {
    private String name;
    private Location target;

    /**
     * Constructs a new packet with the specified name and target location.
     *
     * @param name   The name of the packet.
     * @param target The target location for delivery.
     */
    public Packet(String name, Location target) {

        this.name = name;
        this.target = target;
    }

    /**
     * Returns a string representation of the packet, which is its name.
     *
     * @return A string representation of the packet.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the name of the packet.
     *
     * @return The name of the packet.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the target location of the packet.
     *
     * @return The target location of the packet.
     */
    public Location getTarget() {
        return target;
    }
}
