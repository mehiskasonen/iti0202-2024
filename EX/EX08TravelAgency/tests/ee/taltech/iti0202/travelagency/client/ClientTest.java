package ee.taltech.iti0202.travelagency.client;

import ee.taltech.iti0202.travelagency.BaseTest;
import ee.taltech.iti0202.travelagency.agency.TravelPacket;
import ee.taltech.iti0202.travelagency.exceptions.transaction_exceptions.TransactionException;

import java.io.IOException;
import java.util.List;

import static ee.taltech.iti0202.travelagency.enums.ClientType.REGULAR;
import static ee.taltech.iti0202.travelagency.enums.ClientType.SILVER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTest extends BaseTest {

    @org.junit.jupiter.api.Test
    void testSetBudget() {
        double expected = 3000.0;
        clients.get(0).setBudget(3000.0);
        assertEquals(expected, clients.get(0).getBudget());
    }

    @org.junit.jupiter.api.Test
    void testGetBudget() {
        clients.get(0).setBudget(3000.0);
        double expected = 3000.0;
        assertEquals(expected, clients.get(0).getBudget());
    }

    @org.junit.jupiter.api.Test
    void testGetType() {
        assertEquals(REGULAR, clients.get(0).getType());
    }

    @org.junit.jupiter.api.Test
    void testSetType() {
        clients.get(0).setType(SILVER);
        assertEquals(SILVER, clients.get(0).getType());
    }

    @org.junit.jupiter.api.Test
    void testBuyTravelPacketEnoughFundsSuccess() throws TransactionException, IOException {
        Client client1 = clients.get(1);
        client1.buyPackage(travelPackets.get(0), agencies.get(0));
        assertTrue(true, String.valueOf(client1.getBoughtTravelPackets().contains(travelPackets.get(0))));
    }

    @org.junit.jupiter.api.Test
    void testGetBoughtTravelPacketsHasOnePacket() {
        clients.get(0).getBoughtTravelPackets().add(travelPackets.get(0));
        assertEquals(1, clients.get(0).getBoughtTravelPackets().size());
    }

    @org.junit.jupiter.api.Test
    void testGetBoughtTravelPacketsHasNoPackets() {
        List<TravelPacket> expected = List.of();
        assertEquals(expected, clients.get(0).getBoughtTravelPackets());
    }
}
