package ee.taltech.iti0202.computerstore.store;

import ee.taltech.iti0202.computerstore.Customer;
import ee.taltech.iti0202.computerstore.components.Component;
import ee.taltech.iti0202.computerstore.database.Database;
import ee.taltech.iti0202.computerstore.exceptions.NotEnoughMoneyException;
import ee.taltech.iti0202.computerstore.exceptions.OutOfStockException;
import ee.taltech.iti0202.computerstore.exceptions.ProductNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Store {
    private String name;
    private BigDecimal balance;
    private BigDecimal profitMargin;

    public void setStoreDb(Database storeDb) {
        this.storeDb = storeDb;
    }

    private Database storeDb = Database.getInstance();

    public Store(String name, BigDecimal balance, BigDecimal profitMargin) {
        int comparison = BigDecimal.valueOf(1.0).compareTo(profitMargin);
        if (comparison > 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.balance = balance;
        this.profitMargin = profitMargin;

    }

    public Component purchaseComponent(int id, Customer customer) throws OutOfStockException,
            ProductNotFoundException,
            NotEnoughMoneyException {
        BigDecimal price = storeDb.getComponents().get(id).getPrice().multiply(profitMargin);
        int comparison = price.compareTo(customer.getBalance());
        if (!storeDb.getComponents().containsKey(id)) {
            throw new ProductNotFoundException();
        }
        if (comparison > 0) {
            throw new NotEnoughMoneyException();
        }
        if (storeDb.getComponents().get(id).getAmount() == 0) {
            throw new OutOfStockException();
        } else {
            storeDb.getComponents().get(id).setAmount(storeDb.getComponents().get(id).getAmount() - 1);
            customer.setBalance(customer.getBalance().subtract(price));
            this.setBalance(this.getBalance().add(price));
            customer.addToComponents(storeDb.getComponents().get(id));
        }
        return storeDb.getComponents().get(id);
    }

    public List<Component> getAvailableComponents() {
        List<Component> storeComponents = new ArrayList<>();
        for (Component comp : storeDb.getComponents().values()) {
            if (comp.getAmount() != 0) {
                storeComponents.add(comp);
            }
        }
        return storeComponents;
    }

    public List<Component> getComponentsSortedByAmount() {
        List<Component> componentsByAmount = new ArrayList<>(storeDb.getComponents().values());
        Comparator<Component> byAmount = Comparator.comparingInt(Component::getAmount);
        componentsByAmount.sort(byAmount);
        return componentsByAmount;
    }

    public List<Component> getComponentsSortedByName() {
        List<Component> componentsByName = new ArrayList<>(storeDb.getComponents().values());
        Comparator<Component> byName = Comparator.comparing(Component::getName);
        componentsByName.sort(byName);
        return componentsByName;
    }

    public List<Component> getComponentsSortedByPrice() {
        List<Component> componentsByPrice = new ArrayList<>(storeDb.getComponents().values());
        Comparator<Component> byPrice = Comparator.comparing(Component::getPrice);
        componentsByPrice.sort(byPrice);
        return componentsByPrice;
    }

    public List<Component> filterByType(Component.Type type) {
        List<Component> components = new ArrayList<>(storeDb.getComponents().values());
        return components.stream()
                .filter(component -> component.getType() == type)
                .toList();
    }

    public BigDecimal getInventoryValue() {
        BigDecimal inventoryValueSum = BigDecimal.ZERO;
        for (Component comp : storeDb.getComponents().values()) {
            BigDecimal compPrice = comp.getPrice();
            BigDecimal profitMargin = this.profitMargin;
            BigDecimal amount = BigDecimal.valueOf(comp.getAmount());

            BigDecimal priceMarginMultiplier = compPrice.multiply(profitMargin);
            BigDecimal componentsValue = amount.multiply(priceMarginMultiplier);
            inventoryValueSum = inventoryValueSum.add(componentsValue);
        }
        inventoryValueSum = inventoryValueSum.setScale(2, RoundingMode.HALF_UP);

        return inventoryValueSum;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getProfitMargin() {
        return this.profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        int comparison = BigDecimal.valueOf(1.0).compareTo(profitMargin);
        if (comparison > 0) {
            throw new IllegalArgumentException();
        }
        this.profitMargin = profitMargin;
    }
}
