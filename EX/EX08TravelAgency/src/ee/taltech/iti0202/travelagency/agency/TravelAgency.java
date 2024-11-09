package ee.taltech.iti0202.travelagency.agency;

import ee.taltech.iti0202.travelagency.client.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TravelAgency {
    private static final Logger LOGGER = Logger.getLogger(TravelAgency.class.getName());
    private final String name;
    private List<Client> clients = new ArrayList<>();

    private List<TravelPacket> packages = new ArrayList<>();
    private HashMap<Client, List<TravelPacket>> clientTravelPacketMap = new HashMap<>();
    private HashMap<Client, Double> clientExpenditureMap = new HashMap<>();

    /**
     * @param name of the travel agency.
     * @throws IOException in case of logger API file related problem.
     */
    public TravelAgency(String name) throws IOException {
        this.name = name;
        FileHandler fileTxt = new FileHandler("Logging.txt");
        LOGGER.addHandler(fileTxt);
    }

    /**
     * @return list of packages that have been bought.
     */
    public List<TravelPacket> getPackages() {
        return packages;
    }

    /**
     * Popularity is determined how much money the client has spent.
     * If two clients are tied, then prefer the client who has bought
     * more packets.
     * @return HashMap of clients who have bought the most travel packets
     * where value is
     * in descending order.
     */
    public HashMap<Client, Double> getTopClients() {
        return clientExpenditureMap.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    double expenditureComparison = Double.compare(entry2.getValue(), entry1.getValue());
                    if (expenditureComparison == 0) {
                        int packetsBoughtComparison = Integer.compare(
                                clientTravelPacketMap.getOrDefault(entry2.getKey(), List.of()).size(),
                                clientTravelPacketMap.getOrDefault(entry1.getKey(), List.of()).size()
                        );
                        if (packetsBoughtComparison == 0) {
                            return Integer.compare(entry2.getKey().hashCode(), entry1.getKey().hashCode());
                        }
                        return packetsBoughtComparison;
                    }
                    return (int) Math.round(expenditureComparison);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue)
                        -> oldValue, LinkedHashMap::new));
    }

    /**
     * Popularity of travel packets is determined by the amount bought.
     * If popularity is tied, list them in alphabetical order.
     * @return Hashmap of most popular travel packets in descending order.
     */
    public HashMap<TravelPacket, Integer> getTopTravelPackets() {
        HashMap<TravelPacket, Integer> travelPacketStatistics = new HashMap<>();
        for (TravelPacket packet : packages) {
            int currentAmount = travelPacketStatistics.getOrDefault(packet, 0);
            int updatedAmount = currentAmount + 1;
            travelPacketStatistics.put(packet, updatedAmount);
        }
        return travelPacketStatistics.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int popularityComparison = Integer.compare(entry2.getValue(), entry1.getValue());
                    if (popularityComparison == 0) {
                        return entry1.getKey().getName().compareTo(entry2.getKey().getName());
                    }
                    return popularityComparison;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue)
                        -> oldValue, LinkedHashMap::new));
    }

    /**
     * @return hashmap of clients travel packets, where client is key and
     * value is list of travel packets that client has bought.
     */
    public HashMap<Client, List<TravelPacket>> getClientTravelPacketMap() {
        return clientTravelPacketMap;
    }

    /**
     * @return a map of clients and how much they have spent.
     */
    public HashMap<Client, Double> getClientExpenditureMap() {
        return clientExpenditureMap;
    }

    /**
     * Update sales statistics when a client buys a new packet.
     * @param packet The travel packet purchased by the client.
     * @param client The client who purchased the travel packet.
     */
    public void updateClientTravelPacketMap(TravelPacket packet, Client client) {
        if (clientTravelPacketMap.containsKey(client)) {
            clientTravelPacketMap.get(client).add(packet);
            LOGGER.info(packet + " was added to " + client + " travel packet map");
        } else {
            List<TravelPacket> packets = new ArrayList<>();
            packets.add(packet);
            clientTravelPacketMap.put(client, packets);
            LOGGER.info(client + " and " + packet + " was added to client travel packet map");
        }
    }

    /**
     * Adds a certain amount of money when client spends money on a travel packet
     * to clients expenditure map.
     * @param amountToAdd double value client spent to be added.
     * @param client whose expenditure is updated.
     */
    public void updateClientExpenditureMap(Double amountToAdd, Client client) {
        client.setExpenditure(client.getExpenditure() + amountToAdd);
        Double currentExpenditure = clientExpenditureMap.getOrDefault(client, 0.0);
        Double updatedExpenditure = currentExpenditure + amountToAdd;
        clientExpenditureMap.put(client, updatedExpenditure);
    }

}
