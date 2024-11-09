package ee.taltech.iti0202.transportation.travelagency;

import ee.taltech.iti0202.transportation.booking.Booking;
import ee.taltech.iti0202.transportation.ticket.Ticket;

public class TravelAgency {

    private double defaultPrice;

    /**
     * Travel agency class constructor.
     *
     * @param defaultPrice to which discounts are applied to.
     */
    public TravelAgency(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    /**
     * Sets a new base price.
     *
     * @param price to be set as new base price.
     */
    public void setPrice(double price) {
        this.defaultPrice = price;
    }

    /**
     * Method for buying a ticket.
     * Takes a booking object as a parameter.
     * @param booking object contains Person object and local date.
     * @return ticket object that was booked.
     */
    public Ticket bookTicket(Booking booking) {
        //System.out.println(booking.getStrategy().bookTicket(booking.getPerson(), booking.getDate(), defaultPrice));
        return booking.getStrategy().bookTicket(booking.getPerson(), booking.getDate(), defaultPrice);

        /*if (booking.getStrategy() instanceof BusStrategy) {
            ticket = booking.getStrategy().bookTicket(booking.getPerson(), booking.getDate(), defaultPrice);
            //bookTicket(booking.getPerson(), booking.getDate(), defaultPrice);
            return ticket;
                    //new booking.getStrategy().bookTicket(booking.getPerson(), booking.getDate(), defaultPrice));
                    //busstrategy -> bookTicket(booking.getPerson(), booking.getDate(), defaultPrice));
            //strategy -> ticket(price)
            //booking.getStrategy().bookTicket(booking.getPerson(), booking.getDate(), defaultPrice);
            //return new Ticket()
        }
        if (booking.getStrategy() instanceof PlaneStrategy) {
            ticket = booking.getStrategy().bookTicket(booking.getPerson(), booking.getDate(), defaultPrice);
            return ticket;
        }
        if (booking.getStrategy() instanceof TrainStrategy) {
            ticket = booking.getStrategy().bookTicket(booking.getPerson(), booking.getDate(), defaultPrice);
            return ticket;
        }
        return new Ticket()*/
    }

    /**
     * @return string representation of an agency.
     */
    @Override
    public String toString() {
        return "TravelAgency{" +
                "defaultPrice=" + defaultPrice +
                '}';
    }
}
