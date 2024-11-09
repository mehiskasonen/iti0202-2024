package ee.taltech.iti0202.exam;

import ee.taltech.iti0202.exam.strategy.AgeStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @org.junit.jupiter.api.Test
    void testGetBudget() {
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), client.getBudget());
    }

    @org.junit.jupiter.api.Test
    void testSetBudget() {
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        client.setBudget(BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(200), client.getBudget());
    }

    @org.junit.jupiter.api.Test
    void getSessionCounterStartsAt0() {
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        assertEquals(0, client.getSessionCounter());
    }

    @org.junit.jupiter.api.Test
    void testCanSetStrategy() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        assertEquals(strategy1, system1.getStrategy());
    }

    @org.junit.jupiter.api.Test
    void getSessionCounterIncrements() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(30), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        client.buySession(system1, session1);
        assertEquals(1, client.getSessionCounter());
    }

    @org.junit.jupiter.api.Test
    void testBuySessionUpdatesClientsSessions() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(30), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        assertEquals(0, client.getTakenSessions().size());
        client.buySession(system1, session1);
        assertEquals(1, client.getTakenSessions().size());
    }

    @org.junit.jupiter.api.Test
    void testBuySessionSystemGetsClient() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(30), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        assertEquals(0, system1.getAllClients().size());
        client.buySession(system1, session1);
        assertEquals(1, system1.getAllClients().size());
    }

    @org.junit.jupiter.api.Test
    void testBuySessionUpdatesCruiseHistory() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(30), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        assertEquals(0, system1.getCruiseHistory().size());
        client.buySession(system1, session1);
        assertEquals(1, system1.getCruiseHistory().size());
    }

    @org.junit.jupiter.api.Test
    void testBuySessionIncrementsCruiseHistory() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(30), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        client.buySession(system1, session1);
        assertEquals(1, system1.getCruiseHistory().get(session1));

        client.buySession(system1, session1);
        assertEquals(2, system1.getCruiseHistory().get(session1));

    }

    @org.junit.jupiter.api.Test
    void testBuySessionCruiseCapacityIncreases() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(30), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(100));
        assertEquals(0, ship.getCurrentCapacity());
        client.buySession(system1, session1);
        assertEquals(1, ship.getCurrentCapacity());

    }

    @org.junit.jupiter.api.Test
    void testBuySessionBudgetDecreases() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(200), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(300));
        client.buySession(system1, session1);
        assertEquals(new BigDecimal("120.0"), client.getBudget());
    }

    @org.junit.jupiter.api.Test
    void testBuySessionBudgetDecreasesWithCumulativeDiscount() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(200), ship);
        Client client = new Client("Client1", 17, BigDecimal.valueOf(300));
        client.setSessionCounter(2);
        client.buySession(system1, session1);
        assertEquals(new BigDecimal("170.00"), client.getBudget());
    }

    @org.junit.jupiter.api.Test
    void testSearchSessions() {
        CruiseMe system1 = new CruiseMe();
        Cruise ship = new Cruise(400);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(200), ship);
        Session session2 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(300), ship);
        Session session3 = new Session("Tallinn", "Oslo", BigDecimal.valueOf(200), ship);
        system1.addSession(session1);
        system1.addSession(session2);
        system1.addSession(session3);
        List<Session> expected = new ArrayList<>();
        expected.add(session1);
        expected.add(session2);
        List<Session> actual = system1.searchSessions("Tallinn", "Helsinki");
        assertEquals(expected.size(), actual.size());
    }

    @org.junit.jupiter.api.Test
    void testBuyMultipleSessionManyTimesUpdatesSessionHistory() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session3 = new Session("Tallinn", "Oslo", BigDecimal.valueOf(200), ship);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(200), ship);
        Session session2 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(200), ship);
        system1.addSession(session1);
        system1.addSession(session2);
        system1.addSession(session3);

        Client client = new Client("Client1", 17, BigDecimal.valueOf(1000));
        client.buySession(system1, session1);
        client.buySession(system1, session1);

        assertEquals(1, system1.getCruiseHistory().size());
        assertEquals(2, system1.getCruiseHistory().get(session1));
    }

/*    @org.junit.jupiter.api.Test
    void testIncrementIdWords() {
        Cruise ship = new Cruise(400);
        Session session3 = new Session("Tallinn", "Oslo", BigDecimal.valueOf(200), ship);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(200), ship);
        //assertEquals(1, session3.getId());
        assertEquals(2, session1.getId());
    }*/

    @org.junit.jupiter.api.Test
    void testSortSessionsCorrectOrder() {
        CruiseMe system1 = new CruiseMe();
        Strategy strategy1 = new AgeStrategy();
        system1.setStrategy(strategy1);
        Cruise ship = new Cruise(400);
        Session session3 = new Session("Tallinn", "Oslo", BigDecimal.valueOf(200), ship);
        Session session1 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(200), ship);
        Session session2 = new Session("Tallinn", "Helsinki", BigDecimal.valueOf(300), ship);
        system1.addSession(session1);
        system1.addSession(session2);
        system1.addSession(session3);

        Map<Session, Integer> expected = new LinkedHashMap<>();
        expected.put(session1, 3);
        expected.put(session2, 2);
        expected.put(session3, 1);

        Client client = new Client("Client1", 17, BigDecimal.valueOf(10000));
        client.buySession(system1, session2);
        client.buySession(system1, session2);
        client.buySession(system1, session1);
        client.buySession(system1, session1);
        client.buySession(system1, session1);
        client.buySession(system1, session3);

        Map<Session, Integer> sortedMap = system1.sortByMostBought(system1.getCruiseHistory());
        assertEquals(expected, sortedMap);
    }

}