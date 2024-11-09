package ee.taltech.iti0202.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private List<Client> clients = new ArrayList<>();
    private List<Cruise> cruises = new ArrayList<>();
    public static int nextId;
    private static int getAndIncrementNextId() {
        return ++nextId;
    }

    private final int id;

    private final String origin;
    private final String destination;
    private final BigDecimal price;

    private final Cruise ship;

    public Session(String origin, String destination, BigDecimal price, Cruise ship) {
        this.id = getAndIncrementNextId();
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.ship = ship;
    }

    public Cruise getShip() {
        return ship;
    }

    public int getId() {
        return this.id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
