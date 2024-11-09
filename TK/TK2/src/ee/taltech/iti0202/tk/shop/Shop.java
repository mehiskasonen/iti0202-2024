package ee.taltech.iti0202.tk.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Shop class.
 */
public class Shop {
    List<Product> productList;

    /**
     * Shop class constructor, initialises the list of products.
     */
    public Shop() {
        this.productList = new ArrayList<>();
    }

    /**
     * Function to add a product to the shop list.
     * @param product to be added.
     * @return boolean value.
     */
    public boolean addProduct(Product product) {
        if ((product.getPrice() >= 0) && productList.stream().noneMatch(p ->
                Objects.equals(p.getName(), product.getName())
                        && Objects.equals(p.getPrice(), product.getPrice()))) {
            productList.add(product);
            return true;
        }
        return false;
    }

    /**
     * Function to sell one product from the shop.
     * @param name of product to sell.
     * @param maxPrice the product can be priced at.
     * @return product that was sold or Optional empty value.
     */
    public Optional<Product> sellProduct(String name, int maxPrice) {
        Optional<Product> highestPriceProduct = Optional.empty();
        int highestPrice = Integer.MIN_VALUE;

        for (Product product : productList) {
            if (product.getName() != null && product.getName().equals(name)
                    && product.getPrice() <= maxPrice) {
                if (product.getPrice() > highestPrice) {
                    highestPriceProduct = Optional.of(product);
                    highestPrice = product.getPrice();
                }
            }
        }

        highestPriceProduct.ifPresent(product -> productList.remove(product));

        return highestPriceProduct;

    }

    public List<Product> getProducts() {
        return productList;
    }
}
