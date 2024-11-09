package ee.taltech.iti0202.delivery.strategy;

import ee.taltech.iti0202.delivery.Action;
import ee.taltech.iti0202.delivery.Courier;
import ee.taltech.iti0202.delivery.Location;
import ee.taltech.iti0202.delivery.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Strategy for courier to pick up packets from only one location and deliver them
 * to all other locations.
 * Check starting location. If starting location is servicing location:
 * then pick up packets,
 * otherwise start moving towards the servicing location.
 *
 */
public class OneLocationPickUpStrategy implements Strategy {

    private Map<Location, Action> locationActions = new HashMap<>();

    private List<Action> actions;

    private final Location servicingLocation;
    Action returnHome;
    private int currentIndex = 0;
    private Courier courier;

    /**
     * @param servicingLocation location being serviced.
     * @param actions list of actions.
     */
    public OneLocationPickUpStrategy(Location servicingLocation, List<Action> actions) {
        this.actions = new ArrayList<>(actions);
        this.servicingLocation = servicingLocation;
    }

    @Override
    public Action getAction() {
        if (!Objects.equals(getStartingLocation(courier), getServicingLocation())
        && courier.getCourierPackets().isEmpty()) {
            Iterator<Action> iterator = actions.iterator();
            while (iterator.hasNext()) {
                Action action = iterator.next();
                if (action.getLocation().equals(servicingLocation)) {
                    iterator.remove();
                    System.out.println(action);
                    return action;
                }
            }
        }

/*        Iterator<Action> iterator2 = actions.iterator();
        while (iterator2.hasNext()) {
            Action action = iterator2.next();
            if (action.getLocation().equals(courier.getLocation())) {
                iterator2.remove();
                return action;
            }
        }*/

        // logic to return rest of the actions.
        return returnHome;
    }

    /**
     * @param courier strategy belongs to.
     * @return starting location of courier.
     */
    public Location getStartingLocation(Courier courier) {
        return courier.getLocation().orElse(null);
    }

    /**
     * @return location being serviced with strategy.
     */
    public Location getServicingLocation() {
        return servicingLocation;
    }

}
