package ee.taltech.iti0202.transportation.strategy;

import ee.taltech.iti0202.transportation.person.Person;
import ee.taltech.iti0202.transportation.ticket.Ticket;

import java.time.LocalDate;

/**
 * Strategy that gives 50% discount on even dates.
 * On odd dates discount % is equal to nr of chars in persons name.
 */
public class BusStrategy implements BookingStrategy {

    /**
     * Plane strategy method to book a ticket.
     * @param person booking the ticket.
     * @param date of booking.
     * @param price of booking.
     * @return ticket object.
     */
    @Override
    public Ticket bookTicket(Person person, LocalDate date, double price) {
        double finalPrice = price;
        if (isDateEven(date)) {
            finalPrice = price - (price * 0.5);
        } else {
            finalPrice = price - (price * (0.01 * getNameCharCount(person)));
        }
        return new Ticket(Math.round(finalPrice));
    }

    public boolean isDateEven(LocalDate date) {
        return date.getDayOfMonth() % 2 == 0;
    }

    public int getNameCharCount(Person person) {
        int charCount = 0;
        for (int i = 0; i < person.getName().length(); i++) {
            charCount++;
        }
        return charCount;
    }
}
