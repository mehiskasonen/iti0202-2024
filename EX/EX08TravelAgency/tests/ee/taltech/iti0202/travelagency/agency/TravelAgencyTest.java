package ee.taltech.iti0202.travelagency.agency;

import ee.taltech.iti0202.travelagency.BaseTest;
import ee.taltech.iti0202.travelagency.client.Client;
import ee.taltech.iti0202.travelagency.client.ClientBuilder;
import ee.taltech.iti0202.travelagency.exceptions.transaction_exceptions.TransactionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TravelAgencyTest extends BaseTest {

    @Test
    void testGetTopClientsNormal() {
        agencies.get(0).getClientExpenditureMap().put(clients.get(0), 1000.0);
        agencies.get(0).getClientExpenditureMap().put(clients.get(1), 2000.0);
        LinkedHashMap<Client, Double> expectedResult = new LinkedHashMap<>();
        expectedResult.put(clients.get(1), 2000.0);
        expectedResult.put(clients.get(0), 1000.0);
        assertEquals(expectedResult, agencies.get(0).getTopClients());
    }

    @Test
    void testGetTopClientsSameNrTravelPackets() throws TransactionException, IOException {
        clients.get(1).addToBoughtPackets(travelPackets.get(0));
        clients.get(2).addToBoughtPackets(travelPackets.get(1));
        clients.get(3).addToBoughtPackets(travelPackets.get(2));

        agencies.get(0).getClientExpenditureMap().put(clients.get(2), 1000.0);
        agencies.get(0).getClientExpenditureMap().put(clients.get(3), 800.0);
        agencies.get(0).getClientExpenditureMap().put(clients.get(1), 1500.0);

        LinkedHashMap<Client, Double> expectedClientSpendatureMap = new LinkedHashMap<>();
        expectedClientSpendatureMap.put(clients.get(1), 1500.0);
        expectedClientSpendatureMap.put(clients.get(2), 1000.0);
        expectedClientSpendatureMap.put(clients.get(3), 800.0);

        assertEquals(expectedClientSpendatureMap, agencies.get(0).getTopClients());
    }

    @Test
    void testGetTopTravelPacketsNormal() {
        agencies.get(0).getPackages().add(travelPackets.get(0));
        agencies.get(0).getPackages().add(travelPackets.get(1));
        agencies.get(0).getPackages().add(travelPackets.get(0));
        LinkedHashMap<TravelPacket, Integer> expectedResult = new LinkedHashMap<>();
        expectedResult.put(travelPackets.get(0), 2);
        expectedResult.put(travelPackets.get(1), 1);
        assertEquals(expectedResult, agencies.get(0).getTopTravelPackets());
    }

    @Test
    void testGetTopTravelPacketsSameAmountOfPurchases() {
        agencies.get(0).getPackages().add(travelPackets.get(0));
        agencies.get(0).getPackages().add(travelPackets.get(1));
        agencies.get(0).getPackages().add(travelPackets.get(0));
        agencies.get(0).getPackages().add(travelPackets.get(1));
        LinkedHashMap<TravelPacket, Integer> expectedResult = new LinkedHashMap<>();
        expectedResult.put(travelPackets.get(1), 2); //"Reykjavik-Iceland" Should be first.
        expectedResult.put(travelPackets.get(0), 2); //"Riga-Latvia"
        TravelPacket expectedfirstPacket = expectedResult.keySet().iterator().next();
        TravelPacket firstPacket = agencies.get(0).getTopTravelPackets().keySet().iterator().next();
        assertEquals(expectedfirstPacket, firstPacket);
    }

}
