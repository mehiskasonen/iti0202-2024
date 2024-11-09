package ee.taltech.iti0202.delivery.strategy;

import ee.taltech.iti0202.delivery.Action;
import ee.taltech.iti0202.delivery.Courier;
import ee.taltech.iti0202.delivery.Location;
import ee.taltech.iti0202.delivery.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BruteForceStrategy implements Strategy {
    private List<Location> destinations = new ArrayList<>();
    private Location initialLocation;
    private Location locationTracker;
    private int counter = 0;
    private List<Action> bestActions = new ArrayList<>();
    private int bestDistance = Integer.MAX_VALUE;
    private List<Action> actions;
    private Courier courier;

    /**
     * Constructor for brute force strategy.
     * Initializes the strategy with initial location and list of actions.
     * @param initialLocation courier starts at.
     * @param actions for courier to perform.
     */
    public BruteForceStrategy(Location initialLocation, List<Action> actions) {
        this.initialLocation = initialLocation;
        this.actions = new ArrayList<>(actions);
        getDestinations(actions);
    }

    /**
     * Determines the next action for the courier to perform based on the brute-force solution
     * to the Traveling Salesman Problem (TSP). The method finds all permutations of locations,
     * calculates the total distance for each permutation, and selects the permutation with the
     * shortest distance.
     * @return the next best action for the courier to take.
     */
    @Override
    public Action getAction() {
        if (actions.isEmpty()) {
            return null;
        }

        List<List<Location>> permutations = generatePermutationsFromStartingLocation();
        System.out.println(permutations);
        for (List<Location> permutation : permutations) {
            int totalDistance = calculateTotalDistance(permutation);
            if (totalDistance < bestDistance) {
                bestDistance = totalDistance;
                bestActions = getActionsForPermutation(permutation);
            }
        }

        if (!bestActions.isEmpty()) {
            return bestActions.remove(0);
        } else {
            return null; // No more actions available
        }
    }


    /**
     * Calculates the total distance for a given permutation of locations, starting and ending
     * at the initial location.
     * @param permutation list of locations in the order to be visited
     * @return the total distance for the permutation
     */
    private int calculateTotalDistance(List<Location> permutation) {
        int totalDistance = 0;
        Location currentLocation = initialLocation;

        for (Location nextLocation : permutation) {
            int distanceToNext = currentLocation.getDistanceTo(nextLocation.getName());
            if (distanceToNext != Integer.MAX_VALUE) {
                totalDistance += distanceToNext;
            }
            currentLocation = nextLocation;

        }
        int distanceToInitial = currentLocation.getDistanceTo(initialLocation.getName());
        if (distanceToInitial != Integer.MAX_VALUE) {
            totalDistance += distanceToInitial;
        }
        return totalDistance;
    }

    /**
     * Extracts the destinations from the list of actions and stores them in the destinations list.
     * @param actions to be taken.
     */
    private void getDestinations(List<Action> actions) {
        for (Action action : actions) {
            destinations.add(action.getLocation());
        }
    }

    /**
     * Generates all permutations of the locations, starting from the initial location.
     * @return nested list of all permutations of locations.
     */
    private List<List<Location>> generatePermutationsFromStartingLocation() {
        List<List<Location>> permutations = new ArrayList<>();
        List<Location> remainingLocations = new ArrayList<>(destinations);
        remainingLocations.remove(initialLocation);
        generatePermutationsHelper(initialLocation, remainingLocations, new ArrayList<>(), permutations);
        return permutations;
    }

    /**
     * Helper method for generating permutations. Recursively generates all permutations of the
     * remaining locations, adding them to the list of permutations.
     *
     * @param currentLocation current location in the permutation.
     * @param remainingLocations list of remaining locations to be permuted.
     * @param permutation currently constructed permutation
     * @param permutations nested list of all generated permutations.
     */
    private void generatePermutationsHelper(Location currentLocation,
                                            List<Location> remainingLocations,
                                            List<Location> permutation,
                                            List<List<Location>> permutations) {
        permutation.add(currentLocation);

        if (remainingLocations.isEmpty()) {
            // Add a copy of the permutation to the list of permutations
            permutations.add(new ArrayList<>(permutation));
        } else {
            for (Location nextLocation : remainingLocations) {
                generatePermutationsHelper(nextLocation, remainingLocations
                        .stream().filter(l -> !l.equals(nextLocation))
                        .collect(Collectors.toList()), permutation, permutations);
            }
        }
        permutation.removeLast(); // Backtrack
    }

    /**
     * Creates a list of actions corresponding to the given permutation of locations.
     * @param permutation list of locations in the order to be visited.
     * @return list of actions corresponding to the permutation.
     */
    private List<Action> getActionsForPermutation(List<Location> permutation) {
        List<Action> actionsForPermutation = new ArrayList<>();

        // Start from the initial location
        Location currentLocation = permutation.get(0);

        // Iterate over each location in the permutation (excluding the initial location)
        for (int i = 1; i < permutation.size(); i++) {
            Location nextLocation = permutation.get(i);

            // Find the action that corresponds to moving from the current location to the next location
            Action action = findAction(currentLocation, nextLocation);

            // If an action is found, add it to the list of actions for the permutation
            if (action != null) {
                actionsForPermutation.add(action);
            }

            // Update the current location to the next location for the next iteration
            currentLocation = nextLocation;
        }

        return actionsForPermutation;
    }

    /**
     * Method to find the action that corresponds to moving from the current location
     * to the next location.
     * @param currentLocation the current location.
     * @param nextLocation the next location.
     * @return the action that moves from the current location to the next location, or
     * null if not found.
     */
    private Action findAction(Location currentLocation, Location nextLocation) {
        for (Action action : actions) {
            if (action.getLocation().equals(nextLocation)) {
                return action;
            }
        }
        return null;
    }


}
