package ee.taltech.iti0202.delivery;

/**
 * Represents a strategy that determines the action a courier should take.
 */
public interface Strategy {

    /**
     * Gets the action that a courier should perform based on the implemented strategy.
     *
     * @return The action determined by the strategy.
     */
    Action getAction();

/*
    *//**
     * Setter for making a many-one connection between courier and strategy.
     * Used in OneLocationPickUpStrategy.
     * @param courier assigned to strategy.
     *//*
    void setCourier(Courier courier);*/
}
