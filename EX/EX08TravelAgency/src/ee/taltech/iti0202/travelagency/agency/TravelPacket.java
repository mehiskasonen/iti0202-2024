package ee.taltech.iti0202.travelagency.agency;

import ee.taltech.iti0202.travelagency.enums.Activity;
import ee.taltech.iti0202.travelagency.enums.Destination;
import ee.taltech.iti0202.travelagency.enums.TripType;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

public class TravelPacket {

    private UUID id;
    private String name;
    private Double price;
    private LocalDate start;
    private LocalDate end;
    private final Enum<Destination> destination;
    private Enum<TripType> type;
    private Enum<Activity> activities;

    /**
     * Class constructor for a travel packet.
     * @param name of travel packet.
     * @param price of travel packet.
     * @param start date of the packet.
     * @param end date of the travel packet.
     * @param destination where the travel takes to.
     * @param type of travel packet, e.g. main activity.
     * @param activities that can be performed on the trip.
     */
    public TravelPacket(String name, Double price, LocalDate start, LocalDate end,
                        Enum<Destination> destination, Enum<TripType> type, Enum<Activity> activities) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.start = start;
        this.end = end;
        this.destination = destination;
        this.type = type;
        this.activities = activities;
    }

    /**
     * @return duration of travel packet.
     */
    public Duration getTravelPacketLength() {
        return Duration.between(start.atStartOfDay(), end.atStartOfDay());
    }

    /**
     * @return price of travel packet.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @return name of travel packet.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for travel packet price.
     * @param price to be set.
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
