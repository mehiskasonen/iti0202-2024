package ee.taltech.iti0202.transportation.strategy;

import ee.taltech.iti0202.transportation.person.Person;
import ee.taltech.iti0202.transportation.ticket.Ticket;

import java.time.LocalDate;

public interface BookingStrategy {

    Ticket bookTicket(Person person, LocalDate date, double price);
}
