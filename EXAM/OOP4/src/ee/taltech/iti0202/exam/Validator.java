package ee.taltech.iti0202.exam;

import ee.taltech.iti0202.exam.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Validator {

    private ShopClient shopClient;
    private Product product;
    private Integer amount;
    private HashMap<Product, Integer> products = new HashMap<>();

    public double getTotalPrice() {
        return totalPrice;
    }

    private double totalPrice;

    public BigDecimal returnDiscount() {
        return discount;
    }

    private BigDecimal discount;

    public Validator(ShopClient shopClient, Product product, Integer amount) {
        this.shopClient = shopClient;
        this.product = product;
        this.amount = amount;
    }

    public Validator(ShopClient shopClient, HashMap<Product, Integer> products, Integer amount) {
        this.shopClient = shopClient;
        this.products = products;
        this.amount = amount;
    }

/*    public boolean canBuyProduct() throws TransactionException {
        double totalPrice = amount * product.getPrice();
        if ((shopClient.getBudget() - totalPrice) > 0) {
            this.totalPrice = totalPrice;
            return true;
        } else {
            throw new TransactionException(TransactionException.TransactionReason.NOT_ENOUGH_FUNDS);
        }
    }*/

    public boolean canBuyProducts() throws TransactionException {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        CandyShop shop = getFirstKey(products).getShop();

        for (Strategy strategy : shop.getShopStrategies()) {
            //discount = BigDecimal.valueOf(strategy.getDiscount(totalPrice, shopClient.getOrderCount()));
        }
    }


    /**
     * @return discount to be applied when buying or renewing membership to sports club.
     */
    public BigDecimal getDiscount() {
        CandyShop shop = getFirstKey(products).getShop();
        discount = shop.getShopStrategies().stream()
                .map(strategy -> strategy.getDiscount(totalPrice, shopClient.getOrderCount()));
        return discount;
    }

    public static <K, V> K getFirstKey(HashMap<K, V> map) {
        Iterator<K> iterator = map.keySet().iterator();
        return iterator.next();
    }

}
