package ee.taltech.iti0202.travelagency.transaction;

import ee.taltech.iti0202.travelagency.agency.TravelAgency;
import ee.taltech.iti0202.travelagency.agency.TravelPacket;
import ee.taltech.iti0202.travelagency.client.Client;
import ee.taltech.iti0202.travelagency.enums.ClientType;
import ee.taltech.iti0202.travelagency.exceptions.transaction_exceptions.TransactionException;
import ee.taltech.iti0202.travelagency.logging.TravelAgencyLogger;

import java.io.IOException;
import java.util.logging.Logger;

import static ee.taltech.iti0202.travelagency.enums.ClientType.GOLD;
import static ee.taltech.iti0202.travelagency.enums.ClientType.SILVER;

public class Transaction {
    private static final Logger LOGGER = TravelAgencyLogger.getLogger();
    private static final double ZERO_POINT_ZERO_FIVE = 0.05;
    private static final double ZERO_POINT_TEN = 0.10;
    private final Client client;
    private final TravelAgency travelAgency;

    /**
     * Class constructor for transaction.
     * @param client participating in transaction.
     * @param travelAgency participating in transaction.
     * @throws IOException in case of logger API file related exception.
     */
    public Transaction(Client client, TravelAgency travelAgency) throws IOException {
        this.client = client;
        this.travelAgency = travelAgency;
    }

    /**
     * Logic for the client to buy a package
     * This method might involve checking client's budget,
     * applying discounts, updating client's travel history, etc.
     * It might also involve updating agency's records, such as
     * tracking sales and inventory management
     * @return boolean true if buying was successful.
     */
    public boolean buyPackage(TravelPacket packet) throws TransactionException {
        return sellPacket(packet);
    }

    /**
     * Finalizes the transaction by recording it in the travel agency's statistics.
     * @param packet The travel packet purchased by the client.
     */
    public void finalizeTransaction(TravelPacket packet, Double packetprice) {
        travelAgency.updateClientTravelPacketMap(packet, client);
        travelAgency.updateClientExpenditureMap(packetprice, client);
    }

    /**
     * Logic for the travel agency to sell a package to a client
     * This method might involve validating the purchase,
     * updating client's budget, applying discounts, etc.
     * It might also involve updating agency's records, such as
     * tracking sales and inventory management
     * @return boolean true if selling was successful.
     */
    public boolean sellPacket(TravelPacket packet) throws TransactionException {
        double packetPrice = packet.getPrice();
        double discount = getDiscount(packet);
        if (discount != 0.00) {
            packetPrice = packetPrice * discount;
            try {
                subtractFundsFromClientBudget(packetPrice);
                client.setExpenditure(client.getExpenditure() + packetPrice);
                client.addToBoughtPackets(packet);
                finalizeTransaction(packet, packetPrice);
                LOGGER.info(packet + " sold to " + client + " with discount" + discount);
                return true;
            } catch (TransactionException e) {
                LOGGER.warning("Failed to sell package: " + e.getReason());
            }
        } else try {
            subtractFundsFromClientBudget(packetPrice);
            client.setExpenditure(client.getExpenditure() + packetPrice);
            client.addToBoughtPackets(packet);
            finalizeTransaction(packet, packetPrice);
            LOGGER.info(packet + " sold to " + client + " without discount");
            return true;
        } catch (TransactionException e) {
            LOGGER.warning("Failed to sell package: " + e.getReason());
            throw new TransactionException(TransactionException.Reason.FUND_WOULD_BE_NEGATIVE);
        }
        return false;
    }

    /**
     * Apply discount.
     * For Silver clients apply 5% discount if trip length is >= 5 days.
     * Gold clients get 10% discount on same terms.
     * @return boolean if discount was applied.
     */
    public double getDiscount(TravelPacket packet) {
        Enum<ClientType> type = calculateType(client);
        if (packet.getTravelPacketLength().toDays() >= 5 && type == SILVER
                || (packet.getTravelPacketLength().toDays() >= 5 && type == GOLD)) {
            return type == SILVER ? ZERO_POINT_ZERO_FIVE : ZERO_POINT_TEN;
        }
        return 0.00;
    }

    /**
     * Calculates clients status(type) and changes it, if it is different then before.
     * Silver - has bought 3 packets.
     * Gold - has bought 5 packets.
     */
    public Enum<ClientType> calculateType(Client client) {
        if (client.getBoughtTravelPackets().size() >= 3
                && client.getBoughtTravelPackets().size() < 5) {
            client.setType(SILVER);
        }
        if (client.getBoughtTravelPackets().size() >= 5) {
            client.setType(GOLD);
        }
        return client.getType();
    }

    /**
     * In the future could be used for refunds.
     * @param client to who's budget.
     * @param amount of money is added.
     */
    public void addFundsToClientBudget(Client client, double amount) {
        client.setBudget(client.getBudget() + amount);
    }

    /**
     * Clients budget can not become negative. In that case, should catch and return an
     * exception (CannotCompleteTransaction - reason FUND_WOULD_BE_NEGATIVE)
     *
     * @param amount of money is subtracted.
     */
    public void subtractFundsFromClientBudget(double amount) throws TransactionException {
        double remainingFunds = client.getBudget() - amount;
        if ((remainingFunds) < 0) {
            LOGGER.warning(client + " does not have enough funds.");
            throw new TransactionException(TransactionException.Reason.FUND_WOULD_BE_NEGATIVE);
        }
        client.setBudget(remainingFunds);
        LOGGER.info(client + " budget was set to " + client.getBudget());
    }

}
