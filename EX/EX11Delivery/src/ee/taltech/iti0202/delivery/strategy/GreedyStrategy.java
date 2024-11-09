package ee.taltech.iti0202.delivery.strategy;

import ee.taltech.iti0202.delivery.Action;
import ee.taltech.iti0202.delivery.Location;
import ee.taltech.iti0202.delivery.Strategy;

import java.util.ArrayList;
import java.util.List;

public class GreedyStrategy implements Strategy {
    private List<Action> actions;
    private Location initialLocation;

    /**
     * Class constructor for greedy strategy.
     * Makes the locally optimal choice by selecting the nearest unvisited location.
     * @param initialLocation location where courier starts from.
     * @param actions given to courier.
     */
    public GreedyStrategy(Location initialLocation, List<Action> actions) {
        this.actions = new ArrayList<>(actions);
        this.initialLocation = initialLocation;
    }

    @Override
    public Action getAction() {
        System.out.println(actions.size());
        if (actions.isEmpty()) {
            return null;
        }
        Action bestAction = null;
        int bestDistance = Integer.MAX_VALUE;
        int toRemove = 0;
        for (int i = 0; i < actions.size(); i++) {
            Location nextLocation = actions.get(i).getLocation();
            //remove also action matching starting location.
            int distance = initialLocation.getDistanceTo(nextLocation.getName());
            if (distance < bestDistance) {
                bestDistance = distance;
                bestAction = actions.get(i);
                toRemove = i;
            }


        }
        assert bestAction != null;
        initialLocation = bestAction.getLocation();
        bestDistance = Integer.MAX_VALUE;
        actions.remove(toRemove);
        return bestAction;
    }

    // takes an action
    // if actions location == currentlocation, skip
    // get actions location
    // get distance to location
    // if distance is smaller than current best options distance
    //   overwrite distance and replace action to return.
    // return best action and remove from actions.
}
