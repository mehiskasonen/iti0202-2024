package ee.taltech.iti0202.transportation.strategy;

import ee.taltech.iti0202.transportation.person.Person;
import ee.taltech.iti0202.transportation.ticket.Ticket;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Strategy that gives 30% discount on work days.
 * No discount on weekends.
 */
public class TrainStrategy implements BookingStrategy {

    /**
     * Train strategy method to book a ticket.
     * @param person booking the ticket.
     * @param date of booking.
     * @param price of booking.
     * @return ticket object.
     */
    @Override
    public Ticket bookTicket(Person person, LocalDate date, double price) {
        double finalPrice = price;
        if (isWorkDay(date)) {
            finalPrice = price - (price * 0.3);
        }
        return new Ticket(Math.round(finalPrice));
    }

    /**
     * @param date of booking.
     * @return true if date is a work day, false if it saturday or sunday.
     */
    public static boolean isWorkDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }
}
