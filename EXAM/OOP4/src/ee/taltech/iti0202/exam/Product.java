package ee.taltech.iti0202.exam;

import java.math.BigDecimal;

public class Product {

    private String name;
    private double price;

    private CandyShop shop;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setShop(CandyShop shop) {
        this.shop = shop;
    }

    public CandyShop getShop() {
        return shop;
    }
}
