package ee.taltech.iti0202.exam.cafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cafe {
    private final String name;
    private Order order;
    private List<Order> orders = new ArrayList<>();

    /**
     * Constructor for Cafe class.
     * Creates a new cafe with a name.
     * @param name the name of the cafe.
     */
    public Cafe(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the cafe.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the list of orders in the cafe.
     */
    public List<Order> getOrders() {
        return this.orders;
    }

    /**
     * Takes a new order and adds it to the list of orders.
     * If order was already taken, when nothing happens.
     * @param order the order to take.
     */
    public void takeOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
        }
    }

  /**
   * Finds an order by the customer's name.
   * Return the order, or Optional.empty() if no order with given client´s name exists.
   */
  public Optional<Order> findOrderByName(String customerName) {
      for (Order o : getOrders()) {
          if (o.getCustomerName().equals(customerName)) {
              return Optional.of(o);
          }
      }
        return Optional.empty();
    }

    /**
     * Calculates the total income of the cafe by summing total income of all orders in the cafe.
     * @return the total income from all orders.
     */
    public double calculateTotalIncome() {
        double totalIncome = 0;
        for (Order o : getOrders()) {
            totalIncome += o.getTotalPrice();
        }
        return totalIncome;
    }

    /**
     * Calculates the average price of the orders.
     * @return the average order price.
     */
    public double getAverageOrderPrice() {
        if (getOrders().isEmpty()) {
            return 0;
        } else {
            double avg;
            int orders = getOrders().size();
            avg = calculateTotalIncome() / orders;
            return avg;
        }
    }

  /**
   * Finds the most popular food item name in the cafe.
   * Returns name of the most popular food item name from all the orders in the cafe.
   * If no orders have been made returns Optional.empty().
   * @return the most popular food.
   */
  public Optional<String> findMostPopularFood() {
      HashMap<String, Integer> foodNames = new HashMap<>();
      if (getOrders().isEmpty()) {
          return Optional.empty();
      } else {
          for (Order o : getOrders()) {
              for (Food f : o.getFoods()) {
                  if (!foodNames.containsKey(f.getName())) {
                      foodNames.put(f.getName(), 1);
                  } else {
                      foodNames.put(f.getName(), foodNames.get(f.getName()) + 1);
                  }
              }
          }
      }

      HashMap<String, Integer> foodNames2 = foodNames.entrySet().stream()
              .sorted((entry1, entry2) -> {
                  int popularityComparison = Integer.compare(entry2.getValue(), entry1.getValue());
                  if (popularityComparison == 0) {
                      return entry1.getKey().compareTo(entry2.getKey());
                  }
                  return popularityComparison;
              })
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue)
                      -> oldValue, LinkedHashMap::new));

      String result = "";
      for (Map.Entry item : foodNames2.entrySet()) {
          result = String.valueOf(item.getKey());
          break;
      }
    return Optional.of(result);
    }
}
