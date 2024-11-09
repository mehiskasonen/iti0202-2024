package ee.taltech.iti0202.transportation.booking;

import ee.taltech.iti0202.transportation.person.Person;
import ee.taltech.iti0202.transportation.strategy.BookingStrategy;

import java.time.LocalDate;

public class Booking {
    private final Person person;
    private final LocalDate date;

    private BookingStrategy bookingStrategy;

    /**
     * Booking class constructor.
     * @param person doing the booking.
     * @param date booking is assigned to.
     */
    public Booking(Person person, LocalDate date) {
        this.person = person;
        this.date = date;
        //this.bookingStrategy = bookingStrategy;
    }

    /**
     * @return person object that is doing the booking;
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @return local date to which booking is for.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Determines which kind of strategy will be used, e.g. which kind of ticket will be bought.
     *
     * @param strategy (discount calculation) used when booking a ticket.
     */
    public void setStrategy(BookingStrategy strategy) {
        this.bookingStrategy = strategy;
    }

    /**
     * @return strategy that is being used.
     */
    public BookingStrategy getStrategy() {
        return bookingStrategy;
    }

    /**
     * @return string representation of booking object.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "person=" + person +
                ", date=" + date +
                ", bookingStrategy=" + bookingStrategy +
                '}';
    }
}
