package ee.taltech.iti0202.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CandyShop {

    public List<Strategy> getShopStrategies() {
        return shopStrategies;
    }

    private final List<Strategy> shopStrategies = new ArrayList<>();
    private final List<ShopClient> clientList = new ArrayList<>();
    private final List<Product> productList = new ArrayList<>();
    public HashMap<Product, Integer> getSoldProducts() {
        return soldProducts;
    }
    private final HashMap<Product, Integer> soldProducts = new HashMap<>();


    private Product getProductByName(String searchTerm) {
        for (Product product : productList) {
            if (Objects.equals(product.getName(), searchTerm)) {
                return product;
            }
        }
        return null;
    }

    private Product getMostPopularProduct() {
        int highest = 0;
        Product mostPopular;
        for (Product p : soldProducts.keySet())
            if (soldProducts.get(p) > highest) {
                highest = soldProducts.get(p);
            }
        return null;
    }

    public List<ShopClient> getClientList() {
        return clientList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public boolean addClient(ShopClient client) {
        if (!clientList.contains(client)) {
            clientList.add(client);
            return true;
        }
        return false;
    }

    public boolean addProductToShop(Product candy) {
        if (!productList.contains(candy)) {
            productList.add(candy);
            candy.setShop(this);
            return true;
        }
        return false;
    }

    public void updateOverview(Product product, Integer amount) {
        if (!soldProducts.containsKey(product)) {
            soldProducts.put(product, amount);
        } else {
            soldProducts.put(product, soldProducts.get(product) + amount);
        }
    }

    public void addStrategy(Strategy strategy) {
        if (!shopStrategies.contains(strategy)) {
            shopStrategies.add(strategy);
        }
    }


}
