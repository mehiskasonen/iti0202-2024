package ee.taltech.iti0202.exam;

import ee.taltech.iti0202.exam.cafe.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {

    private List<Product> productList = new ArrayList<>();
    private Transaction transaction;
    private ShopClient client;

    /**
     * Constructor for Order class.
     * Creates a new order with a list of products and a customer.
     * @param products the list of products in the order.
     * @param client the name of the customer.
     */
    public Order(List<Product> products, ShopClient client) {
        this.productList = products;
        this.client = client;
    }

    /**
     * Gets the list of foods in the order.
     */
    public List<Product> getProducts() {
        return this.productList;
    }

    /**
     * Gets the name of the customer who placed the order.
     */
    public String getCustomerName() {
        return this.client.getName();
    }

    /**
     * Adds a food item to the order.
     */
    public void addFood(Product product) {
        productList.add(product);
    }

    /**
     * Removes a food item from the order if it exists.
     */
    public void removeFood(Product product) {
        productList.remove(product);
    }

    /**
     * Calculates the total price of the order by summing all food items in the order.
     * @return the total price of the order
     */
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product p : productList) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    /**
     * Returns the string representation of the order in the format:
     * "This order contains: {foods elements separated by comma}.".
     * For example: "This order contains: pie, coffee, yoghurt."
     * If there are no food items, return "This order is empty.".
     */
    @Override
    public String toString() {
        if (productList.isEmpty()) {
            return "This order is empty.";
        } else {
            //TODO Product with amount and total sum of order.
            StringBuilder builder = new StringBuilder();
            builder.append("This order contains: ");
            for (Product p : getProducts()) {
                builder.append(p.getName()).append(", ");
            }

            String string = builder.toString();
            String removeComma = string.substring(0, string.length() - 2);
            return removeComma + ".";
        }
    }

}
