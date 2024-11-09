package ee.taltech.iti0202.travelagency;

import ee.taltech.iti0202.travelagency.agency.TravelAgency;
import ee.taltech.iti0202.travelagency.agency.TravelPacket;
import ee.taltech.iti0202.travelagency.client.Client;
import ee.taltech.iti0202.travelagency.client.ClientBuilder;
import ee.taltech.iti0202.travelagency.enums.Activity;
import ee.taltech.iti0202.travelagency.enums.Destination;
import ee.taltech.iti0202.travelagency.enums.TripType;
import ee.taltech.iti0202.travelagency.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ee.taltech.iti0202.travelagency.enums.ClientType.GOLD;
import static ee.taltech.iti0202.travelagency.enums.ClientType.REGULAR;
import static ee.taltech.iti0202.travelagency.enums.ClientType.SILVER;

public class BaseTest {
    public List<TravelPacket> travelPackets = new ArrayList<>();
    public List<Client> clients = new LinkedList<>();
    public List<TravelAgency> agencies = new ArrayList<>();
    public List<Transaction> transactions = new ArrayList<>();

    @BeforeEach
    void setUp() throws IOException {
        travelPackets = new ArrayList<>();
        clients = new LinkedList<>();
        agencies = new ArrayList<>();

        TravelPacket packet1 = new TravelPacket("Riga-Latvia", 1500.00, LocalDate.of(2024, 6, 15),
                LocalDate.of(2024, 6, 21),
                Destination.BARCELONA_SPAIN,
                TripType.BEACH_HOLIDAY,
                Activity.SUNBATHING);

        TravelPacket packet2 = new TravelPacket(
                "Reykjavik-Iceland",
                1000.0,
                LocalDate.of(2024, 12, 20),
                LocalDate.of(2024, 12, 23),
                Destination.REYKJAVIK_ICELAND,
                TripType.SKIING,
                Activity.SKIING);

        TravelPacket packet3 = new TravelPacket(
                "Berlin-Germany",
                800.0,
                LocalDate.of(2024, 03, 27),
                LocalDate.of(2024, 03, 29),
                Destination.BERLIN_GERMANY,
                TripType.LAST_MINUTE,
                Activity.MEETING);
        travelPackets.add(packet1);
        travelPackets.add(packet2);
        travelPackets.add(packet3);

        Client client1 = new ClientBuilder("Client1", "client1@gmail.com", 20)
                .setAddress("Jaagutee 2")
                .createClient();
        Client client2 = new ClientBuilder("Client2", "client2@mail.com", 21);
        Client client3 = new ClientBuilder("Client3", "client2@mail.com", 21);
        Client client4 = new ClientBuilder("Client4", "client2@mail.com", 21);

        Client regularClient = new ClientBuilder("Client2", "client2@mail.com", 21);
        Client silverClient = new ClientBuilder("Client2", "client2@mail.com", 21);
        Client goldClient = new ClientBuilder("Client2", "client2@mail.com", 21);

        client2.setBudget(4000.0);
        client3.setBudget(0.00);
        client4.setBudget(100.0);

        regularClient.setType(REGULAR);
        silverClient.setType(SILVER);
        goldClient.setType(GOLD);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(regularClient);
        clients.add(silverClient);
        clients.add(goldClient);

        TravelAgency agency1 = new TravelAgency("Agency1");
        agencies.add(agency1);
        Transaction transaction1 = new Transaction(client1, agency1);
        Transaction transaction2 = new Transaction(client4, agency1);
        transactions.add(transaction1);
        transactions.add(transaction2);

    }
}
