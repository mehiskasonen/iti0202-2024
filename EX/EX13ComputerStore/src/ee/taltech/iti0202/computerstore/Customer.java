package ee.taltech.iti0202.computerstore;

import ee.taltech.iti0202.computerstore.components.Component;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class Customer {
    private String name;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    private BigDecimal balance;

    public List<Component> getComponents() {
        return components;
    }

    private final List<Component> components = new ArrayList<>();

    public Customer(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public void addToComponents(Component component) {
        components.add(component);
    }
}
