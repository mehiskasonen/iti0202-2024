package ee.taltech.iti0202.stock.product;
import ee.taltech.iti0202.stock.exceptions.StockException;

public class Product {

    private final String name;
    private final int price;
    private static int nextId = 1;
    private final int id;

    /**
     * Method to override default toString() method.
     * @return string representation of product and the price
     */
    public String toString() {
        return name + " " + price + " ";
    }

    /**
     * Create a new product with the given name and price.
     * <p>
     * If the price is negative, throw a StockException with a NEGATIVE_PRICE reason.
     * @param name Name of the product.
     * @param price Price of the product.
     * @throws StockException NEGATIVE_PRICE
     */
    public Product(String name, int price) throws StockException {
        if (price < 0) {
            throw new StockException(StockException.Reason.NEGATIVE_PRICE);
        }
        this.name = name;
        this.price = price;
        this.id = getNextId();
    }

    /**
     * Returns the next id.
     * This value has to increment with every call.
     * @return The next id.
     */
    public static int getNextId() {
        return Product.nextId++;
    }

    /**
     * Returns id of the product.
     * @return id of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the product.
     * @return the name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the product.
     * @return the price of the product.
     */
    public int getPrice() {
        return price;
    }
}
