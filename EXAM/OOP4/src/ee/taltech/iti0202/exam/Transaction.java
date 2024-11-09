package ee.taltech.iti0202.exam;

import ee.taltech.iti0202.exam.exceptions.TransactionException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class Transaction {
    private ShopClient shopClient;
    private Product product;
    private Integer amount;
    private HashMap<Product, Integer> products = new HashMap<>();

    public Transaction(ShopClient shopClient, Product product, Integer amount) {
        this.shopClient = shopClient;
        this.product = product;
        this.amount = amount;
    }

    public Transaction(ShopClient shopClient, HashMap<Product, Integer> products) {
        this.shopClient = shopClient;
        this.products = products;
    }

    /**
     * Apply discount.
     * For Silver clients apply 5% discount if trip length is >= 5 days.
     * Gold clients get 10% discount on same terms.
     * @return boolean if discount was applied.
     */

/*    public double getDiscount(Product product) {
        Enum<ClientType> type = calculateType(client);
        if (packet.getTravelPacketLength().toDays() >= 5 && type == SILVER
                || (packet.getTravelPacketLength().toDays() >= 5 && type == GOLD)) {
            return type == SILVER ? ZERO_POINT_ZERO_FIVE : ZERO_POINT_TEN;
        }
        return 0.00;
    }*/


    public boolean finishBuying() throws TransactionException {
        Validator validator = new Validator(shopClient, products, amount);
        if (validator.canBuyProducts()) {
            //BigDecimal discount = validator.getDiscount();
            shopClient.subtractFromBudget(validator.getTotalPrice());
                    //.subtractFromMemberWallet(calculateMembershipCostWithDiscount(membership, discount));
            product.getShop().addClient(shopClient);
            shopClient.addCandie(product, amount);
            product.getShop().updateOverview(product, amount);
            return true;
        }
        return false;
    }

}
