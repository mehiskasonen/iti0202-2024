package strategy;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import org.junit.jupiter.api.Test;
import projbasetest.StrategyBaseTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CombinedStrategyTest extends StrategyBaseTest {

    @Test
    void testNoParticipation() {
        CombinedStrategy strategy = new CombinedStrategy(testClubs.get(1));
        BigDecimal discount = strategy.getDiscount(testMembers.get(6));
        assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    void testGetDiscount() throws RegistrationException {
        CombinedStrategy strategy = new CombinedStrategy(testClubs.get(0));
        //register member1 to training3 session 4 and training4 session 5
        testMembers.get(0).registerToTrainingSession(testSessions.get(0));
        testMembers.get(0).registerToTrainingSession(testSessions.get(1));
        //avg discount = 28.00
        System.out.println("Member1 BPs: " + testMembers.get(0).getBonusPoints().getPoints(testClubs.get(0)));
        //BPs total = 32
        //32 / 5 = 6,4
        // 12 - 6,4 = 5,6
        // 5,6 * 0,25 = 1,4
        // 1,4
        System.out.println("Member2 BPs: " + testMembers.get(1).getBonusPoints().getPoints(testClubs.get(0)));
        System.out.println("Member3 BPs: " + testMembers.get(2).getBonusPoints().getPoints(testClubs.get(0)));
        System.out.println("Member4 BPs: " + testMembers.get(3).getBonusPoints().getPoints(testClubs.get(0)));
        System.out.println("Member5 BPs: " + testMembers.get(4).getBonusPoints().getPoints(testClubs.get(0)));

        //BP discount = 1,4
        // avg discount + BPs discount = 28.00 + 1,4 =  29.40

        //combined = 3 sports (5%) + 29.40 = 34,40%     6,68%
        BigDecimal discount = strategy.getDiscount(testMembers.get(0));
        assertEquals(new BigDecimal("34.40"), discount);
    }

    @Test
    void testGetDiscountOverMaximum() throws RegistrationException {
        CombinedStrategy strategy = new CombinedStrategy(testClubs.get(0));
        //register member1 to all club1 5 sessions.
        testMembers.get(0).registerToTrainingSession(testSessions.get(0));
        testMembers.get(0).registerToTrainingSession(testSessions.get(1));
        testMembers.get(0).registerToTrainingSession(testSessions.get(2));
        testMembers.get(0).registerToTrainingSession(testSessions.get(3));
        testMembers.get(0).getBonusPoints().addBonusPoints(testClubs.get(0), 116);

        BigDecimal discount = strategy.getDiscount(testMembers.get(0));
        System.out.println(discount);
        assertEquals(new BigDecimal("90.00"), discount);
    }
}
