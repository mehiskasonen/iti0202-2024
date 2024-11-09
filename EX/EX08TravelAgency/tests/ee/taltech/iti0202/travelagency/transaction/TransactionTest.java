package ee.taltech.iti0202.travelagency.transaction;

import ee.taltech.iti0202.travelagency.BaseTest;
import ee.taltech.iti0202.travelagency.agency.TravelPacket;
import ee.taltech.iti0202.travelagency.client.Client;
import ee.taltech.iti0202.travelagency.exceptions.transaction_exceptions.TransactionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ee.taltech.iti0202.travelagency.enums.ClientType.GOLD;
import static ee.taltech.iti0202.travelagency.enums.ClientType.REGULAR;
import static ee.taltech.iti0202.travelagency.enums.ClientType.SILVER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest extends BaseTest {

    @Test
    void testBuyPackageClientHasBoughtTravelPacket() throws TransactionException, IOException {
        clients.get(1).buyPackage(travelPackets.get(0), agencies.get(0));
        assertEquals(1, clients.get(1).getBoughtTravelPackets().size());
    }

    @Test
    void testBuyPackageAgencyHasSoldTravelPacket() throws TransactionException, IOException {
        Client client = clients.get(1);
        client.buyPackage(travelPackets.get(0), agencies.get(0));
        assertEquals(1, agencies.get(0).getClientTravelPacketMap().get(client).size());
    }

    @Test
    void testBuyPackageClientHasCorrectMoneyAfterPurchase() throws TransactionException, IOException {
        Client client = clients.get(1);
        client.buyPackage(travelPackets.get(0), agencies.get(0));
        double expected = 2500.0;
        assertEquals(expected, client.getBudget());
    }

    @Test
    void testBuyPackageThrowsExceptionNotEnoughFunds() {
        Client noFundsClient = clients.get(2);
        assertThrows(TransactionException.class, () -> {
            noFundsClient.buyPackage(travelPackets.get(0), agencies.get(0));
        });
    }

    @Test
    void testSellPacketNoDiscountSufficientFundsSuccess() throws TransactionException, IOException {
        Client newClient = clients.get(1);
        TravelPacket packet = travelPackets.get(0);
        Transaction transaction = new Transaction(newClient, agencies.get(0));
        boolean saleSuccessful = transaction.sellPacket(packet);
        assertTrue(saleSuccessful);
    }

    @Test
    void testSellPacketDiscountSufficientFundsSuccess() throws TransactionException, IOException {
        Client newClient = clients.get(1);
        TravelPacket packet = travelPackets.get(0);
        Transaction transaction = new Transaction(newClient, agencies.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(1));
        newClient.getBoughtTravelPackets().add(travelPackets.get(1));
        newClient.getBoughtTravelPackets().add(travelPackets.get(1));
        boolean saleSuccessful = transaction.sellPacket(packet);
        assertTrue(saleSuccessful);
    }

    @Test
    void testGetDiscountForRegularClient() throws IOException {
        Client regularClient = clients.get(4);
        Transaction transactionNoDiscount = new Transaction(regularClient, agencies.get(0));
        assertEquals(0.00, transactionNoDiscount.getDiscount(travelPackets.get(0)));
    }

    @Test
    void testGetDiscountForSilverClient() throws IOException {
        Client silverClient = clients.get(5);
        Transaction transactionNoDiscount = new Transaction(silverClient, agencies.get(0));
        assertEquals(0.05, transactionNoDiscount.getDiscount(travelPackets.get(0)));
    }

    @Test
    void testGetDiscountForGoldClient() throws IOException {
        Client goldClient = clients.get(6);
        Transaction transactionNoDiscount = new Transaction(goldClient, agencies.get(0));
        assertEquals(0.10, transactionNoDiscount.getDiscount(travelPackets.get(0)));
    }

    @Test
    void testCalculateTypeNewClient() throws IOException {
        Client newClient = clients.get(0);
        Transaction transaction = new Transaction(newClient, agencies.get(0));
        assertEquals(0.00, transaction.getDiscount(travelPackets.get(0)));
    }

    @Test
    void testCalculateTypeRegularClientNoChange() throws TransactionException, IOException {
        Client newClient = clients.get(1);
        Transaction transaction = new Transaction(newClient, agencies.get(0));
        transaction.buyPackage(travelPackets.get(0));
        assertEquals(REGULAR, transaction.calculateType(newClient));
    }

    @Test
    void testCalculateTypeSilverClientNoChange() throws TransactionException, IOException {
        Client newClient = clients.get(1);
        Transaction transaction = new Transaction(newClient, agencies.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(1));
        newClient.getBoughtTravelPackets().add(travelPackets.get(2));
        transaction.buyPackage(travelPackets.get(0));
        assertEquals(SILVER, transaction.calculateType(newClient));
    }

    @Test
    void testCalculateTypeGoldClientNoChange() throws TransactionException, IOException {
        Client newClient = clients.get(1);
        Transaction transaction = new Transaction(newClient, agencies.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(1));
        newClient.getBoughtTravelPackets().add(travelPackets.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(1));
        newClient.getBoughtTravelPackets().add(travelPackets.get(0));
        transaction.buyPackage(travelPackets.get(0));
        assertEquals(GOLD, transaction.calculateType(newClient));
    }

    @Test
    void testCalculateTypeRegularClientToSilver() throws TransactionException, IOException {
        Client newClient = clients.get(1);
        Transaction transaction = new Transaction(newClient, agencies.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(0));
        newClient.getBoughtTravelPackets().add(travelPackets.get(1));
        transaction.buyPackage(travelPackets.get(0));
        assertEquals(SILVER, transaction.calculateType(newClient));
    }

    @Test
    void testCalculateTypeSilverClientToGold() throws TransactionException, IOException {
        Client silverClient = clients.get(1);
        Transaction transaction = new Transaction(silverClient, agencies.get(0));
        silverClient.getBoughtTravelPackets().add(travelPackets.get(0));
        silverClient.getBoughtTravelPackets().add(travelPackets.get(1));
        silverClient.getBoughtTravelPackets().add(travelPackets.get(0));
        silverClient.getBoughtTravelPackets().add(travelPackets.get(1));
        transaction.buyPackage(travelPackets.get(0));
        assertEquals(GOLD, transaction.calculateType(silverClient));
    }

    @Test
    void testAddFundsToClientBudget() {
        transactions.get(0).addFundsToClientBudget(clients.get(2), 5000);
        assertEquals(5000.0,  clients.get(2).getBudget());
    }

    @Test
    void testSubtractFundsFromClientBudget() throws TransactionException {
        transactions.get(1).subtractFundsFromClientBudget(40.0);
        assertEquals(60.0,  clients.get(3).getBudget());
    }
}
