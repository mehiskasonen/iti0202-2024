package ee.taltech.iti0202.delivery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A dummy implementation of the Strategy interface that provides a sequence of predefined actions.
 */
public class DummyStrategy implements Strategy {
    private Map<Location, Action> locationActions = new HashMap<>();

    private List<Action> actions;
    private int currentIndex = 0;

    /**
     * Constructs a DummyStrategy with a list of predefined actions.
     *
     * @param actions The list of actions to be used by the strategy.
     */
    public DummyStrategy(List<Action> actions) {
        this.actions = actions;
    }

    /**
     * Gets the next action in the sequence of predefined actions.
     *
     * @return The next action in the sequence.
     */
    @Override
    public Action getAction() {
        if (currentIndex >= actions.size()) {
            currentIndex = 0;
        }
        Action action = actions.get(currentIndex);
        currentIndex++;

        return action;
    }


}
