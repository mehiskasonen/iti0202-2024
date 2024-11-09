package ee.taltech.iti0202.transportation.ticket;

public class Ticket {

    private final double price;

    /**
     * Class constructor for ticket.
     *
     * @param price of ticket.
     */
    public Ticket(double price) {
        this.price = price;

    }

    /**
     * @return price of ticket.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return string representation of a ticket.
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "price=" + price +
                '}';
    }
}
