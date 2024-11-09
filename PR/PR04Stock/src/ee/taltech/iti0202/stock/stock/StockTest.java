package ee.taltech.iti0202.stock.stock;

import ee.taltech.iti0202.stock.exceptions.StockException;
import ee.taltech.iti0202.stock.product.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class StockTest {

    public static final int PRICE_6 = 6;
    public static final int PRICE_9 = 9;
    public static final int SUM_12 = 12;

    /**
     * Method to verify that the {@code addProduct()} method in the {@code Stock} class
     * @throws StockException {@code STOCK_ALREADY_CONTAINS_PRODUCT} when attempting to add a
     * product that already exists in the stock.
     */
    @org.junit.Test
    public void testAddProductExceptionProductAlreadyInStock() throws StockException {
        Stock fruitStock = new Stock("fruit-stock-1", 4);
        Product cheapApple = new Product("apple", 3);
        fruitStock.addProduct(cheapApple);
        try {
            fruitStock.addProduct(cheapApple);
        } catch (StockException e) {
            assertEquals(StockException.Reason.STOCK_ALREADY_CONTAINS_PRODUCT, e.getReason());
        }
    }

    /**
     * Method to verify that the {@code addProduct()} method in the {@code Stock} class
     * @throws StockException {@code STOCK_IS_FULL} Stock is full and further items can not be added.
     * when attempting to add a product to a full stock.
     */
    @org.junit.Test
    public void testAddProductExceptionStockIsFull() throws StockException {
        Stock fruitStock = new Stock("fruit-stock-1", 2);
        Product cheapApple = new Product("apple", 3);
        Product mango = new Product("mango", PRICE_6);
        Product pear = new Product("pear", 4);
        fruitStock.addProduct(cheapApple);
        fruitStock.addProduct(mango);
        try {
            fruitStock.addProduct(pear);
        } catch (StockException e) {
            assertEquals(StockException.Reason.STOCK_IS_FULL, e.getReason());
        }
    }

    /**
     * The test case expects that the {@code getProduct()} method will correctly
     * retrieve a product with the specified name
     * from the fruit stock and return an optional containing the expected cheap apple product.
     * @throws StockException if any unexpected exception occurs during the test.
     */
    @org.junit.Test
    public void testGetProduct() throws StockException {
        Stock fruitStock = new Stock("fruit-stock-1", 4);
        Product cheapApple = new Product("apple", 3);
        Product expensiveApple = new Product("apple", PRICE_9);
        Product orange = new Product("orange", 5);
        Product mango = new Product("mango", PRICE_6);
        fruitStock.addProduct(expensiveApple);
        fruitStock.addProduct(cheapApple);
        fruitStock.addProduct(orange);
        fruitStock.addProduct(mango);

        Optional<Product> actual = fruitStock.getProduct("apple");
        Optional<Product> expected = Optional.of(cheapApple);
        assertEquals(expected, actual);
    }

    /**
     * The test case expects that the {@code removeProduct()} method will correctly remove the specified product
     * from the fruit stock and return an optional containing the removed product, if found. It also expects the
     * resulting stock to be updated accordingly after each removal operation.
     * @throws StockException if any unexpected exception occurs during the test.
     */
    @org.junit.Test
    public void testRemoveProduct() throws StockException {
        Stock fruitStock = new Stock("fruit-stock-1", 5);
        Product mostcheapestApple = new Product("apple", 3);
        Product cheapApple = new Product("apple", 3);
        Product expensiveApple = new Product("apple", PRICE_9);
        Product orange = new Product("orange", 5);
        Product mango = new Product("mango", PRICE_6);
        fruitStock.addProduct(mostcheapestApple);
        fruitStock.addProduct(expensiveApple);
        fruitStock.addProduct(cheapApple);
        fruitStock.addProduct(orange);
        fruitStock.addProduct(mango);
        Optional<Product> actual = fruitStock.removeProduct("mango");
        Optional<Product> expected = Optional.of(mango);
        assertEquals(expected, actual);
        Optional<Product> actual2 = fruitStock.removeProduct("apple");
        Optional<Product> expected2 = Optional.of(mostcheapestApple);
        assertEquals(expected2, actual2);
        List<Product> actual3 = fruitStock.getProducts();
        List<Product> expected3 = Arrays.asList(expensiveApple, cheapApple, orange);
        assertEquals(expected3, actual3);
    }

    /*@org.junit.Test
    public void getProducts() throws StockException {
        Stock fruitStock = new Stock("fruit-stock-1", 4);

        Product cheapApple = new Product("apple", 3);
        Product expensiveApple = new Product("apple", PRICE_9);

        fruitStock.addProduct(expensiveApple);
        fruitStock.addProduct(cheapApple);
        List<Product> actual = fruitStock.getProducts();
        List<Product> expected = Arrays.asList(expensiveApple, cheapApple);
        assertEquals(expected, actual);
        //System.out.println(fruitStock.getProducts()); // [expensiveApple, cheapApple]
    }*/

    /**
     * The test case expects that the {@code getProducts()} method will correctly retrieve a list of products with
     * the specified name from the fruit stock and return a list containing both cheap and expensive apples.
     * @throws StockException if any unexpected exception occurs during the test.
     */
    @org.junit.Test
    public void testGetProducts() throws StockException {
        Stock fruitStock = new Stock("fruit-stock-1", 4);
        Product cheapApple = new Product("apple", 3);
        Product expensiveApple = new Product("apple", PRICE_9);
        Product orange = new Product("orange", 5);
        Product mango = new Product("mango", PRICE_6);
        fruitStock.addProduct(expensiveApple);
        fruitStock.addProduct(cheapApple);
        fruitStock.addProduct(orange);
        fruitStock.addProduct(mango);
        List<Product> actual = fruitStock.getProducts("apple");  //cheapApple, expensiveApple
        List<Product> expected = Arrays.asList(cheapApple, expensiveApple);
        assertEquals(expected, actual);
    }

    /**
     * The test case expects that the {@code getTotalPrice()} method will correctly calculate the total price
     * of products within the fruit stock and return {@code SUM_12}.
     * @throws StockException if any unexpected exception occurs during the test.
     */
    @org.junit.Test
    public void testGetTotalPrice() throws StockException {
        Stock fruitStock = new Stock("fruit-stock-1", 4);
        Product cheapApple = new Product("apple", 3);
        Product expensiveApple = new Product("apple", PRICE_9);
        fruitStock.addProduct(cheapApple);
        fruitStock.addProduct(expensiveApple);
        assertEquals(SUM_12, fruitStock.getTotalPrice());
    }
}
