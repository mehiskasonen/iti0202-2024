package ee.taltech.iti0202.delivery.examples;

import ee.taltech.iti0202.delivery.Action;
import ee.taltech.iti0202.delivery.Courier;
import ee.taltech.iti0202.delivery.Location;
import ee.taltech.iti0202.delivery.Strategy;
import ee.taltech.iti0202.delivery.World;
import ee.taltech.iti0202.delivery.strategy.BruteForceStrategy;

import java.util.ArrayList;
import java.util.List;

public class BruteForceStrategyExample {
    private static final int DISTANCE_SEVEN = 7;
    private static final int DISTANCE_EIGHT = 8;
    private static final int DISTANCE_SIX = 6;
    private static final int DISTANCE_FOUR = 4;
    private static final int DISTANCE_TWO = 2;

    private static final int DISTANCE_THREE = 3;
    private static final int DISTANCE_FIVE = 5;

    private static final int INDEX_POSITION_16 = 16;
    private static final int INDEX_POSITION_14 = 14;
    private static final int INDEX_POSITION_6 = 6;
    private static final int INDEX_POSITION_2 = 2;
    private static final int INDEX_POSITION_0 = 0;

    public static void main(String[] args) {
        World world = new World();
        Location tallinn = world.addLocation("Tallinn", new ArrayList<>(), new ArrayList<>()).get();
        Location narva = world.addLocation("Narva", List.of("Tallinn"), List.of(DISTANCE_TWO)).get();
        Location tartu = world.addLocation("Tartu", List.of("Tallinn", "Narva"),
                List.of(DISTANCE_THREE, DISTANCE_FIVE)).get();
        Location parnu = world.addLocation("Pärnu", List.of("Tallinn", "Tartu", "Narva"),
                List.of(DISTANCE_EIGHT, DISTANCE_FOUR, DISTANCE_SIX)).get();
        Location viljandi = world.addLocation("Viljandi", List.of("Tallinn", "Tartu", "Pärnu", "Narva"),
                List.of(DISTANCE_FOUR, DISTANCE_THREE, DISTANCE_TWO, DISTANCE_SEVEN)).get();

        Action action1 = new Action(tallinn);
        action1.addDeposit("toTallinn");

        Action action2 = new Action(tartu);
        action2.addDeposit("toTartu");

        Action action3 = new Action(parnu);
        action3.addDeposit("toPärnu");

        Action action4 = new Action(narva);
        action4.addDeposit("toNarva");

        Action action5 = new Action(viljandi);
        action5.addDeposit("toViljandi");

        List<Action> actions = List.of(action1, action2, action3, action4, action5);

        Strategy bruteForceStrategy = new BruteForceStrategy(tallinn, actions);

        Courier courier = world.addCourier("Courier", "Tallinn").get();
        world.giveStrategy("Courier", bruteForceStrategy);

        System.out.println("Courier starts at: " + courier.getLocation());
        // Optimal path: Tallinn-Tartu-Viljandi-Pärnu-Narva
        // 3 + 3 + 2 + 6 + 2 = 16

        for (int i = INDEX_POSITION_0; i <= INDEX_POSITION_16; i++) {
            world.tick();
            if (i == INDEX_POSITION_2 || i == INDEX_POSITION_6 || i == INDEX_POSITION_14 || i == INDEX_POSITION_16) {
                System.out.println(courier);
            }
        }
        System.out.println("Finished all actions.");
    }

}
