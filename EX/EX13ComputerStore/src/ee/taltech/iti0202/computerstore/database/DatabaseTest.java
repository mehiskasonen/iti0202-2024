package ee.taltech.iti0202.computerstore.database;

import ee.taltech.iti0202.computerstore.store.Store;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    public void testLoadFromFileWithoutStoreDb() {
        // Create a Store instance without setting storeDb
        //Store store = new Store("StoreName", new BigDecimal("1000"), new BigDecimal("0.2"));
        Database database = Database.getInstance();

        String result = database.loadFromFile("/Users/mehiskasonen/IdeaProjects/iti0202-2024/EX/EX13ComputerStore/src/ee/taltech/iti0202/computerstore/database/example.txt");

        assertNotNull(result);
    }
}