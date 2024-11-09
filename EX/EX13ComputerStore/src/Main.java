import ee.taltech.iti0202.computerstore.Customer;
import ee.taltech.iti0202.computerstore.components.Component;
import ee.taltech.iti0202.computerstore.database.Database;
import ee.taltech.iti0202.computerstore.exceptions.NotEnoughMoneyException;
import ee.taltech.iti0202.computerstore.exceptions.OutOfStockException;
import ee.taltech.iti0202.computerstore.exceptions.ProductAlreadyExistsException;
import ee.taltech.iti0202.computerstore.exceptions.ProductNotFoundException;
import ee.taltech.iti0202.computerstore.store.Store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, ProductAlreadyExistsException, OutOfStockException, NotEnoughMoneyException, ProductNotFoundException {

        Database db = Database.getInstance();
        Component component = new Component("component name3", Component.Type.RAM, BigDecimal.valueOf(80.0), "Corsair", 4, 1);
        Component component4 = new Component("component name4", Component.Type.FAN, BigDecimal.valueOf(20.0), "Corsair", 1, 2);

        db.saveComponent(component);
        db.saveComponent(component4);
        System.out.println("Component 1 id: " + component.getId());
        System.out.println("Component 4 id: " + component4.getId());


//        component.setAmount(0);
//        System.out.println(component.getAmount());

        Store store = new Store("Store1", BigDecimal.valueOf(0.0), BigDecimal.valueOf(1.3));
        Customer customer = new Customer("Customer1", BigDecimal.valueOf(105.0));
        store.setStoreDb(db);

        store.purchaseComponent(1, customer);

        //db.saveToFile("/Users/mehiskasonen/IdeaProjects/iti0202-2024/EX/EX13ComputerStore/src/ee/taltech/iti0202/computerstore/database/");
        db.loadFromFile("/Users/mehiskasonen/IdeaProjects/iti0202-2024/EX/EX13ComputerStore/src/ee/taltech/iti0202/computerstore/database/example.txt");
        System.out.println(db.getComponents());
        System.out.println(store.getAvailableComponents());
    }
}