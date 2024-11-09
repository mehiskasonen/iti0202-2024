package ee.taltech.iti0202.transportation.strategy;

import ee.taltech.iti0202.transportation.person.Person;
import ee.taltech.iti0202.transportation.ticket.Ticket;

import java.time.LocalDate;

/**
 * Strategy that gives discount % equal to the age of person.
 * Discount can not be greater than 75%.
 */
public class PlaneStrategy implements BookingStrategy {

    /**
     * Plane strategy method to book a ticket.
     * @param person booking the ticket.
     * @param date of booking.
     * @param price of booking.
     * @return ticket object.
     */
    @Override
    public Ticket bookTicket(Person person, LocalDate date, double price) {
        double age = person.getAge();
        double finalPrice;
        if (age >= 75) {
            finalPrice = price - (price * 0.75);
        } else {
            finalPrice = price - (price * (age / 100));
        }
        return new Ticket(Math.round(finalPrice));
    }
}
