package ee.taltech.iti0202.exam.strategy;
import ee.taltech.iti0202.exam.Client;
import ee.taltech.iti0202.exam.Strategy;

public class AgeStrategy implements Strategy {

    @Override
    public Integer calculateDiscount(Client client) {
        int discount = 0;
        if (client.getAge() <= 6) {
            return 100;
        }
        if ((client.getAge() >= 7 || client.getAge() <= 19) || client.getAge() >= 65) {
            discount += 10;
        }
        if (client.getSessionCounter() != 0 && client.getSessionCounter() % 2 == 0) {
            discount += 25;
        }
        return discount;
    }
}
