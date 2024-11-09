package ee.taltech.iti0202.delivery.examples;

import ee.taltech.iti0202.delivery.Action;
import ee.taltech.iti0202.delivery.Courier;
import ee.taltech.iti0202.delivery.Location;
import ee.taltech.iti0202.delivery.Packet;
import ee.taltech.iti0202.delivery.Strategy;
import ee.taltech.iti0202.delivery.World;
import ee.taltech.iti0202.delivery.strategy.GreedyStrategy;

import java.util.List;

public class GreedyStrategyExample {
    public static void main(String[] args) {
        World world = new World();

        Location a = world.addLocation("A", List.of(), List.of()).get();
        Location b = world.addLocation("B", List.of("A"), List.of(1)).get();
        Location c = world.addLocation("C", List.of("A", "B"), List.of(5, 2)).get();
        Location d = world.addLocation("D", List.of("A", "B", "C"), List.of(2, 3, 1)).get();

        Packet packetA1 = new Packet("toB", b);
        Packet packetA2 = new Packet("toC", c);
        Packet packetA3 = new Packet("toD", d);

        a.addPacket(packetA1);
        a.addPacket(packetA2);
        a.addPacket(packetA3);

        Action action1 = new Action(b);
        action1.addTake("toB");
        action1.addTake("toC");
        action1.addTake("toD");

        Action action2 = new Action(c);
        action2.addDeposit("toB");

        Action action3 = new Action(d);
        action3.addDeposit("toC");

        Action action4 = new Action(a);
        action4.addDeposit("toD");

        // Best routes based on starting location:
        //A - B -> C -> D
        //B - A -> D -> C
        //C - D -> A -> B
        //D - C -> B -> A

        //order of C-A-D-B
        Strategy greedyStrategy = new GreedyStrategy(d, List.of(action4, action1, action3, action2));
        Courier courier = world.addCourier("Courier", "D").get();    //Courier should start moving B
        world.giveStrategy("Courier", greedyStrategy);

        System.out.println(a);  // A   PACKETS: toB,toC1,toD
        System.out.println(courier); // Courier1 (D). PACKETS: []
        System.out.println(" ");
        System.out.println("TICK1----------------------------------------");
        world.tick();

        System.out.println(a);  // A   PACKETS:
        System.out.println(courier); // Courier1 (C). PACKETS: []

        world.tick();
        System.out.println(a);
        System.out.println(courier);

        world.tick();
        System.out.println(a);
        System.out.println(courier);
        world.tick();
        System.out.println(a);
        System.out.println(courier);
        world.tick();
        System.out.println(a);
        System.out.println(courier);
        world.tick();
        System.out.println(a);
        System.out.println(courier);
        world.tick();
        System.out.println(a);
        System.out.println(courier);
        world.tick();
        System.out.println(a);
        System.out.println(courier);
    }
}
