package strategy;

import ee.taltech.iti0202.sportsclub.exceptions.RegistrationException;
import org.junit.jupiter.api.Test;
import projbasetest.StrategyBaseTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class AvgParticipationStrategyTest extends StrategyBaseTest {

    @Test
    void testGetDiscountNoParticipation() {
        AvgParticipationStrategy strategy = new AvgParticipationStrategy(testClubs.get(1));
        BigDecimal discount = strategy.getDiscount(testMembers.get(5));
        assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    void testGetDiscountSameAverage() {
        AvgParticipationStrategy strategy = new AvgParticipationStrategy(testClubs.get(3));
        BigDecimal discount = strategy.getDiscount(testMembers.get(0));
        assertEquals(BigDecimal.ZERO, discount);

    }

    @Test
    void testGetDiscountCorrect() throws RegistrationException {
        //register member1 to training3 session 4 and training4 session 5
        testMembers.get(0).registerToTrainingSession(testSessions.get(0));
        testMembers.get(0).registerToTrainingSession(testSessions.get(1));
        AvgParticipationStrategy strategy = new AvgParticipationStrategy(testClubs.get(0));
        BigDecimal discount = strategy.getDiscount(testMembers.get(0));

        //8 / 5 = 1,6
        //1,6 / 5 = 0,32

        //3 / 5 = 0,6
        //0,6 - 0,32 = 0,28 -> 28% discount
        assertEquals(new BigDecimal("28.00"), discount);
    }

    @Test
    void testGetDiscountExceedsMaximum() throws RegistrationException {
        //register member1 to all club1 5 sessions.
        testMembers.get(0).registerToTrainingSession(testSessions.get(0));
        testMembers.get(0).registerToTrainingSession(testSessions.get(1));
        testMembers.get(0).registerToTrainingSession(testSessions.get(2));
        testMembers.get(0).registerToTrainingSession(testSessions.get(3));

        AvgParticipationStrategy strategy = new AvgParticipationStrategy(testClubs.get(0));
        BigDecimal discount = strategy.getDiscount(testMembers.get(0));
        assertEquals(new BigDecimal("60.00"), discount);
        //max 60%
    }

}