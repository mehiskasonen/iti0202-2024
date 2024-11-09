package ee.taltech.iti0202.exam;

import java.util.*;
import java.util.stream.Collectors;

public class CruiseMe {
    public HashMap<Session, Integer> getCruiseHistory() {
        return cruiseHistory;
    }
    private final HashMap<Session, Integer> cruiseHistory = new HashMap<>();

    public List<Client> getAllClients() {
        return allClients;
    }
    private final List<Client> allClients = new LinkedList<>();
    public List<Session> cruiseSessions = new LinkedList<>();
    public Strategy strategy;

    /**
     * Method to search cruises
     * @param origin to search for.
     * @param destination to search for.
     * @return cruise matching criteria.
     */
    public List<Session> searchSessions(String origin, String destination) {
        return cruiseSessions.stream()
                .filter(s -> s.getOrigin().equals(origin))
                .filter(s -> s.getDestination().equals(destination))
                .collect(Collectors.toList());
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void addSession(Session session) {
        if (!cruiseSessions.contains(session)) {
            cruiseSessions.add(session);
        }
    }

    public void addClient(Client client) {
        if (!allClients.contains(client)) {
            allClients.add(client);
        }
    }

    public void updateCruiseHistory(Session session) {
        if (!cruiseHistory.containsKey(session)) {
            cruiseHistory.put(session, 1);
        } else {
            cruiseHistory.put(session, cruiseHistory.get(session) + 1);
        }
    }

    public Map<Session, Integer> sortByMostBought(Map<Session, Integer> map) {
        List<Map.Entry<Session, Integer>> list = new ArrayList<>(map.entrySet());
        System.out.println(list.size());
        list.sort(Map.Entry.comparingByValue());

        Map<Session, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Session, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }



}
