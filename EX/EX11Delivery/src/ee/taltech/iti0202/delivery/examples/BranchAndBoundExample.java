package ee.taltech.iti0202.delivery.examples;

import ee.taltech.iti0202.delivery.Location;
import ee.taltech.iti0202.delivery.Packet;
import ee.taltech.iti0202.delivery.World;

import java.util.List;

public class BranchAndBoundExample {
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;

    public static void main(String[] args) {
        World world = new World();

        Location a = world.addLocation("A", List.of(), List.of()).get();
        Location b = world.addLocation("B", List.of("A"), List.of(3)).get();
        Location c = world.addLocation("C", List.of("A", "B"), List.of(1, SIX)).get();
        Location d = world.addLocation("D", List.of("A", "B", "C"), List.of(5, SEVEN, 4)).get();
        Location e = world.addLocation("E", List.of("A", "B", "C", "D"), List.of(EIGHT, NINE, 2, 3)).get();

        Packet packetA1 = new Packet("toB", b);
        Packet packetA2 = new Packet("toC", c);
        Packet packetA3 = new Packet("toD", d);
        Packet packetA4 = new Packet("toD", d);
        Packet packetA5 = new Packet("toD", d);

        d.addPacket(packetA1);
        d.addPacket(packetA2);
        d.addPacket(packetA3);
        d.addPacket(packetA4);
        d.addPacket(packetA5);

        /*
        1-st level
        A lb = [[(1 + 3) + (3 + 6) + (1 + 2) + (3 + 4) + (2 + 3)] / 2] = 14
        AB lb = [[(3 + 6) + (2 + 4) + (3 + 4) + (2 + 3) = 13,5 ~ 14
                    B       C           D          E
        AC lb = not considered
        AD lb = [[(5 + 1) + (3 + 6) + (1 + 2) + (5 + 3) + (2 + 3)] / 2] = 15,5 ~ 16
        AE lb = [[(8 + 1) + (3 + 6) + (1 + 2) + (4 + 3) + (8 + 2)] / 2] = 19

        2-nd level
        AB->C lb = [[(1 + 3) + (6 + 3) + (6 + 1) + (4 + 3) + (2 + 3)] / 2] = 16 choose
        AB->D lb = [[(1 + 3) + (7 + 3) + (1 + 2) + (7 + 3) + (2 + 3)] / 2] = 16 choose
        AB->E lb = [[(1 + 3) + (9 + 3) + (1 + 2) + (4 + 3) + (9 + 2)] / 2] = 18,5 ~19

        3rd level
        if last 2 are same, meaning D & E == E & D or C & E == E & C, can already calculate full path.
        ABC->D->E-->A = 24

        ABC->E->D-->A = 19

        ABD->C->E-->A = 24

        ABD->E->C-->A = 16
         */
    }
}
