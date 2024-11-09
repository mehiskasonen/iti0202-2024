package ee.taltech.iti0202.travelagency.client;

import ee.taltech.iti0202.travelagency.agency.TravelAgency;
import ee.taltech.iti0202.travelagency.agency.TravelPacket;
import ee.taltech.iti0202.travelagency.enums.ClientType;
import ee.taltech.iti0202.travelagency.exceptions.transaction_exceptions.TransactionException;
import ee.taltech.iti0202.travelagency.logging.TravelAgencyLogger;
import ee.taltech.iti0202.travelagency.transaction.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static ee.taltech.iti0202.travelagency.enums.ClientType.REGULAR;

public class Client {

    private final UUID id;
    private final String name;
    private final String email;
    private final int age;
    private Double budget;
    private Enum<ClientType> type;
    private List<Transaction> transactions = new ArrayList<>();
    private Double expenditure = 0.0;
    private static final Logger LOGGER = TravelAgencyLogger.getLogger();

    private List<TravelPacket> boughtPackets = new ArrayList<>();

    /**
     * Class constructor for client.
     * @param name of client.
     * @param email of client.
     * @param age of client.
     */
    public Client(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.id = UUID.randomUUID();
    }

    /**
     * @return UUID of client.
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return name of client.
     */
    public String getName() {
        return name;
    }

    /**
     * @return email of client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return age of client.
     */
    public int getAge() {
        return age;
    }

    /**
     * Client can not have a negative budget.
     * @return clients current budget.
     */
    public Double getBudget() {
        return budget;
    }

    /**
     * If a new client has no purchases then type is set to regular.
     * @return type client currently has.
     */
    public Enum<ClientType> getType() {
        if (type == null) {
            setType(REGULAR);
        }
        return type;
    }

    /**
     * @param type of client to be set.
     */
    public void setType(Enum<ClientType> type) {
        this.type = type;
    }

    /**
     * @return amount of funds client has spent.
     */
    public Double getExpenditure() {
        return expenditure;
    }

    /**
     * Sets the amount of funds a client has spent.
     * @param expenditure amount of funds the client has spent.
     */
    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    /**
     * Client can only buy a packet if budget allows it.
     * Initiates the buying process on the clients end by creating a new transaction.
     * @return true, buying a travel packet was successful.
     */
    public boolean buyPackage(TravelPacket packet, TravelAgency travelAgency) throws TransactionException, IOException {
        Transaction transaction = new Transaction(this, travelAgency);
        LOGGER.info("Client initiated buying packet: " + packet);
        return transaction.buyPackage(packet);
    }

    /**
     * @param budget for client to be set.
     */
    public void setBudget(Double budget) {
        this.budget = budget;
        LOGGER.info("Budget was set to: " + budget);
    }

    /**
     * If the client has bought no packets, should return an empty list.
     * @return list of travel packets the client has purchased.
     */
    public List<TravelPacket> getBoughtTravelPackets() {
        return boughtPackets;
    }

    /**
     * @param packet to be added to clients bought travel packets list.
     */
    public void addToBoughtPackets(TravelPacket packet) {
        boughtPackets.add(packet);
        LOGGER.info(packet + " was added to bought packets.");
    }
}
