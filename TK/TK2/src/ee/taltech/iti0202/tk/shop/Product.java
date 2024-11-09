package ee.taltech.iti0202.tk.shop;

/**
 * Product class
 */
public class Product {

    private String name;
    private final int price;

    @Override
    public String toString() {
        if (name != null) {
            return name + " " + "(" + price + ")";
        } else {
            return "(" + price + ")";
        }
    }

    /**
     * Product class constructor.
     * @param name of product.
     * @param price of product.
     */
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Product class constructor with one value.
     * @param price of product.
     */
    public Product(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

}


