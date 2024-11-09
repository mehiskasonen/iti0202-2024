package ee.taltech.iti0202.exam;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Client {

    private final String name;
    private final Integer age;
    private BigDecimal budget;

    public List<Session> getTakenSessions() {
        return takenSessions;
    }

    private List<Session> takenSessions = new LinkedList<>();
    private Integer sessionCounter = 0;

    public Client(String name, Integer age, BigDecimal budget) {
        this.name = name;
        this.age = age;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getSessionCounter() {
        return sessionCounter;
    }

    public boolean buySession(CruiseMe system, Session session) {
        int discount = system.getStrategy().calculateDiscount(this);

        BigDecimal discountPercentage = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = session.getPrice().multiply(discountPercentage);
        BigDecimal sessionPrice = session.getPrice()
                .subtract(discountAmount);
        if (this.getBudget().compareTo(sessionPrice) > 0) {
            if (session.getShip().getCurrentCapacity() <= session.getShip().getCapacity()) {
                addTakenSession(session);
                system.addClient(this);
                system.updateCruiseHistory(session);
                sessionCounter++;
                session.getShip().setCurrentCapacity(session.getShip().getCurrentCapacity() + 1);
                this.setBudget(this.getBudget().subtract(sessionPrice));
                return true;
            }
        }
        return false;
    }

    public void addTakenSession(Session session) {
        takenSessions.add(session);
    }

    public void setSessionCounter(int newSessionCounter) {
        this.sessionCounter = newSessionCounter;
    }
}
