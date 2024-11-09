package ee.taltech.iti0202.exam;

import ee.taltech.iti0202.exam.exceptions.TransactionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShopClientTest {

    @Test
    void testBuyCandy() throws IOException, TransactionException {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);
        client.buyCandy(rubberGummy, 10);
        assertTrue(client.getBoughtCandies().containsKey(rubberGummy));
    }

    @Test
    void testBuyingUpdatesClientsMapAmount() throws TransactionException, IOException {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);
        client.buyCandy(rubberGummy, 10);
        assertEquals(10, client.getBoughtCandies().get(rubberGummy));
    }

    @Test
    void testAddCandie() {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        assertEquals(rubberGummy, candyShop.getProductList().get(0));
    }

    @Test
    void testBuyingProductReducesClientBudget() throws TransactionException, IOException {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);
        client.buyCandy(rubberGummy, 10);
        assertEquals( 280, client.getBudget());
    }

    @Test
    void testBuyingProductUpdatesShopOverviewFirstBuy() throws TransactionException, IOException {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);

        assertTrue(candyShop.getSoldProducts().isEmpty());
        client.buyCandy(rubberGummy, 10);
        assertEquals(10, candyShop.getSoldProducts().get(rubberGummy));
    }

    @Test
    void testBuyingProductUpdatesShopOverviewSecondBuy() throws TransactionException, IOException {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);

        client.buyCandy(rubberGummy, 10);
        assertEquals(10, candyShop.getSoldProducts().get(rubberGummy));

        client.buyCandy(rubberGummy, 10);
        assertEquals(20, candyShop.getSoldProducts().get(rubberGummy));
    }

    @Test
    void getAmount() throws TransactionException, IOException {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);

        assertTrue(candyShop.getSoldProducts().isEmpty());
        client.buyCandy(rubberGummy, 8);
        assertEquals(8, client.getAmount(rubberGummy));
    }

    @Test
    void getBudget() {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);
        assertEquals(300, client.getBudget());
    }

    @Test
    void setBudget() {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);
        client.setBudget(400);
        assertEquals(400, client.getBudget());
    }

    @Test
    void testGetBoughtCandiesListUpdates() throws TransactionException, IOException {
        CandyShop candyShop = new CandyShop();
        Product rubberGummy = new Product("rubber gummy", 2.0);
        Product steelGummy = new Product("rubber gummy", 2.0);

        candyShop.addProductToShop(rubberGummy);
        ShopClient client = new ShopClient("Client1", 300);

        assertTrue(candyShop.getSoldProducts().isEmpty());
        client.buyCandy(rubberGummy, 10);
        client.buyCandy(steelGummy, 10);
        assertEquals(2, candyShop.getSoldProducts().size());

    }


}