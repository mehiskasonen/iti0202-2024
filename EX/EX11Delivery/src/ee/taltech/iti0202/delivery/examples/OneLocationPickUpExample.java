package ee.taltech.iti0202.delivery.examples;

import ee.taltech.iti0202.delivery.Action;
import ee.taltech.iti0202.delivery.Courier;
import ee.taltech.iti0202.delivery.Location;
import ee.taltech.iti0202.delivery.Packet;
import ee.taltech.iti0202.delivery.Strategy;
import ee.taltech.iti0202.delivery.World;
import ee.taltech.iti0202.delivery.strategy.OneLocationPickUpStrategy;

import java.util.ArrayList;
import java.util.List;

public class OneLocationPickUpExample {
    public static void main(String[] args) {
        World world = new World();

        Location a = world.addLocation("A", new ArrayList<>(), new ArrayList<>()).get();
        Location b = world.addLocation("B", List.of("A"), List.of(1)).get();
        Location c = world.addLocation("C", List.of("A", "B"), List.of(4, 3)).get();

        Packet packetA1 = new Packet("toB1", b);
        Packet packetA2 = new Packet("toB2", b);
        Packet packetB1 = new Packet("toA1", a);
        Packet packetB2 = new Packet("toA2", a);
        Packet packetB3 = new Packet("toC1", c);
        Packet packetB4 = new Packet("toC2", c);

        a.addPacket(packetA1);      // a has 2 packets that need to be taken to B
        a.addPacket(packetA2);
        b.addPacket(packetB1);
        b.addPacket(packetB2);
        b.addPacket(packetB3);
        b.addPacket(packetB4);

        Action action1Courier1 = new Action(b);
        action1Courier1.addTake("toB1");  //this action should be taken.
        action1Courier1.addTake("toB2");

        Action action2Courier1 = new Action(a);
        action2Courier1.addDeposit("toB1");  //this action should also be taken.
        action2Courier1.addDeposit("toB2");

        Action action3Courier1 = new Action(c);
        action3Courier1.addTake("toC1");    //this action should not be taken.

        Strategy strategyCourier1 = new OneLocationPickUpStrategy(a, List.of(action1Courier1, action2Courier1));

        Courier courier1 = world.addCourier("Courier1", "B").get();    //Courier should start moving B
        world.giveStrategy("Courier1", strategyCourier1);
        //strategyCourier1.setCourier(courier1);

        System.out.println(a);  // A   PACKETS: toB1,toB2
        System.out.println(b);    // B   PACKETS: toA1,toA2, toC2
        System.out.println(courier1); // Courier1 (B). PACKETS: []
        System.out.println(" ");
        System.out.println("TICK1----------------------------------------");
        world.tick();

        System.out.println(a);  // A   PACKETS: toB1, toB2
        System.out.println(b);    // B   PACKETS: toA1, toA2, toC2
        System.out.println(courier1); // Courier1 (null). PACKETS: []
        System.out.println(courier1.getDistanceFromLocation());  // 1
        System.out.println(" ");
        System.out.println("TICK2----------------------------------------");
        world.tick();


        System.out.println(courier1);  // Courier1 (null) PACKETS: []
        //System.out.println(courier1.getDistanceFromLocation());  // same

        System.out.println(" ");
        System.out.println("TICK3----------------------------------------");
        world.tick();

        System.out.println(a);         // A   PACKETS: []
        System.out.println(b);         // B   PACKETS: [toA1, toA2, toC2]
        System.out.println(courier1);  // Courier1 (A). PACKETS: toB1, toB2

        System.out.println(" ");
        System.out.println("TICK4----------------------------------------");
        world.tick();

        System.out.println(courier1);  // Courier1 (null). PACKETS: toA1, toA2

        world.tick();
        world.tick();

        System.out.println(courier1); // Courier1 (A)
        System.out.println(a);   // A   PACKETS: toA1, toA2
        System.out.println(b);     // B   PACKETS:
    }
}
