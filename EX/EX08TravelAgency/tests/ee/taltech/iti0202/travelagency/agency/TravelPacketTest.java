package ee.taltech.iti0202.travelagency.agency;

import ee.taltech.iti0202.travelagency.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TravelPacketTest extends BaseTest {

    @Test
    void getTravelPacketLength() {
        int expected = 6;
        assertEquals(expected, travelPackets.get(0).getTravelPacketLength().toDays());
    }

    @Test
    void getPrice() {
        double expected = 1500.00;
        assertEquals(expected, travelPackets.get(0).getPrice());
    }

    @Test
    void setPrice() {
        double expected = 1600;
        travelPackets.get(0).setPrice(1600.0);
        assertEquals(expected, travelPackets.get(0).getPrice());
    }
}
