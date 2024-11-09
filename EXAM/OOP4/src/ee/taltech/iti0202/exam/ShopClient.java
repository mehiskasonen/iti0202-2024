package ee.taltech.iti0202.exam;
import ee.taltech.iti0202.exam.exceptions.TransactionException;
import java.util.HashMap;

public class ShopClient {
    private String name;
    private Double budget;

    public Integer getOrderCount() {
        return orderCount;
    }

    private Integer orderCount = 0;

    private final HashMap<Product, Integer> products = new HashMap<>();

        HashMap<Product, Integer> boughtCandies = new HashMap<>();

        public ShopClient(String name, double budget) {
            this.name = name;
            this.budget = budget;
        }

        public void buyCandy(Product product, Integer amount) throws TransactionException {
            //Validator validator = new Validator(this, product, amount);
            //if (validator.canBuyProduct()) {
                if (!products.containsKey(product)) {
                    products.put(product, amount);
                } else {
                    products.put(product, products.get(product) + amount);
                }
            //}
        }

        public boolean finishBuy() throws TransactionException {
            Transaction transaction = new Transaction(this, products);
            return transaction.finishBuying();
        }


    public void addCandie(Product candy, Integer sum) {
        if (!boughtCandies.containsKey(candy)) {
            boughtCandies.put(candy, sum);
        }
        if (boughtCandies.containsKey(candy)) {
            boughtCandies.put(candy, boughtCandies.get(candy) + sum);
        }
    }

    public Integer getAmount(Product candy) {
        return boughtCandies.get(candy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public HashMap<Product, Integer> getBoughtCandies() {
        return boughtCandies;
    }

    public void subtractFromBudget(double amount) {
        this.setBudget(this.getBudget() - amount);
    }

}
