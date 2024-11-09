package ee.taltech.iti0202.exam.cafe;

import java.util.List;

public class Order {

    private final List<Food> foods;
    private final String customerName;

    /**
     * Constructor for Order class.
     * Creates a new order with a list of foods and a customer name.
     * @param foods the list of foods in the order.
     * @param customerName the name of the customer.
     */
    public Order(List<Food> foods, String customerName) {
        this.foods = foods;
        this.customerName = customerName;
    }

    /**
     * Gets the list of foods in the order.
     */
    public List<Food> getFoods() {
        return this.foods;
    }

    /**
     * Gets the name of the customer who placed the order.
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * Adds a food item to the order.
     */
    public void addFood(Food food) {
        foods.add(food);
    }

    /**
     * Removes a food item from the order if it exists.
     */
    public void removeFood(Food food) {
        foods.remove(food);
    }

    /**
     * Calculates the total price of the order by summing all food items in the order.
     * @return the total price of the order
     */
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Food f : foods) {
            totalPrice += f.getPrice();
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
        if (foods.isEmpty()) {
            return "This order is empty.";
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("This order contains: ");
            for (Food f : getFoods()) {
                builder.append(f.getName()).append(", ");
            }

            String string = builder.toString();
            String removeComma = string.substring(0, string.length() - 2);
            return removeComma + ".";
        }
    }
}
