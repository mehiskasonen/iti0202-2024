package ee.taltech.iti0202.delivery;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an action that a courier can perform at a specific location.
 */
public class Action {
    public Location getLocation() {
        return location;
    }

    private Location location;
    private List<String> take = new ArrayList<>();
    private List<String> deposit = new ArrayList<>();

    /**
     * Creates a new action with the specified location.
     *
     * @param location The location associated with the action.
     */
    //When creating a new action, it is assigned a location.
    public Action(Location location) {
        this.location = location;
    }

    /**
     * Gets the list of packets that the courier should leave at the current location.
     * When receiving an action, the courier should first leave the packages in the list to its location.
     * @return The list of packets to be deposited.
     */
    public List<String> getDeposit() {
        return deposit;
    }

    /**
     * Gets the list of packets that the courier should take from the current location.
     *
     * @return The list of packets to be taken.
     */
    public List<String> getTake() {
        return take;
    }

    /**
     * Adds a packet to the list of packets to be left at or picked up from the location.
     *
     * @param packetName The name of the packet to be added.
     */
    public void addDeposit(String packetName) {
        deposit.add(packetName);
    }

    /**
     * Adds a packet to the list of packets to be left at or picked up from the location.
     *
     * @param packetName The name of the packet to be added.
     */
    public void addTake(String packetName) {
        take.add(packetName);
    }

    public Location getGoTo() {
        return location;
    }

    public void setGoTo(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Action{" + "location=" + location + ", take=" + take
                + ", deposit=" + deposit + '}';
    }
}
