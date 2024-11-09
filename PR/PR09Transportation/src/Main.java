import ee.taltech.iti0202.transportation.booking.Booking;
import ee.taltech.iti0202.transportation.person.Person;
import ee.taltech.iti0202.transportation.strategy.BusStrategy;
import ee.taltech.iti0202.transportation.strategy.PlaneStrategy;
import ee.taltech.iti0202.transportation.ticket.Ticket;
import ee.taltech.iti0202.transportation.travelagency.TravelAgency;

import java.time.LocalDate;

class Main {
    public static void main(String[] args) {
        Person person = new Person("Mati", 24);
        TravelAgency agency = new TravelAgency(100);
        System.out.println(agency);
        Booking booking = new Booking(person, LocalDate.of(2024, 5, 3));
        System.out.println(booking);
        PlaneStrategy planeStrategy = new PlaneStrategy();
        booking.setStrategy(planeStrategy);
        System.out.println(booking);
        Ticket ticket = agency.bookTicket(booking);
        System.out.println(ticket);
        System.out.println(ticket.getPrice());   // -> 76.0 (24 years old means 24% off)

        //BusStrategyEvenDate
        //expected [350.5] but found [351.0]
        System.out.println("BusStrategyEvenDate");
        Person person2 = new Person("Kati", 24);
        TravelAgency agency2 = new TravelAgency(355.5);
        System.out.println(agency2);
        Booking booking2 = new Booking(person2, LocalDate.of(2024, 5, 3));
        System.out.println(booking2);
        BusStrategy busStrategy = new BusStrategy();
        booking2.setStrategy(busStrategy);
        System.out.println(booking2);
        Ticket ticket2 = agency2.bookTicket(booking2);
        System.out.println(ticket2);
        System.out.println(ticket2.getPrice()); //341.28


        //testPlaneStrategyYoungPeople
        //expected [128.02] but found [128.0]
        System.out.println("testPlaneStrategyYoungPeople");
        Person person3 = new Person("Kati", 24);
        TravelAgency agency3 = new TravelAgency(355.5);
        System.out.println(agency3);
        Booking booking3 = new Booking(person3, LocalDate.of(2024, 5, 3));
        System.out.println(booking3);
        PlaneStrategy planeStrategy2 = new PlaneStrategy();
        booking3.setStrategy(planeStrategy2);
        System.out.println(booking3);
        Ticket ticket3 = agency3.bookTicket(booking3);
        System.out.println(ticket3);
        System.out.println(ticket3.getPrice());

        //testBusStrategyOddDate
        //expected [-532.0] but found [128.0]
        System.out.println("testBusStrategyOddDate");
        Person person4 = new Person("Mati", 24);
        TravelAgency agency4 = new TravelAgency(10);
        System.out.println(agency4);
        Booking booking4 = new Booking(person4, LocalDate.of(2024, 5, 4));
        System.out.println(booking4);
        BusStrategy busStrategy4 = new BusStrategy();
        booking4.setStrategy(busStrategy4);
        System.out.println(booking4);
        Ticket ticket4 = agency4.bookTicket(booking4);
        System.out.println(ticket4);
        System.out.println(ticket4.getPrice());
    }
}