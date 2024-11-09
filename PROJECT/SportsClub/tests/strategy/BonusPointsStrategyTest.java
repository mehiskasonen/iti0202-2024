package strategy;

import org.junit.jupiter.api.Test;
import projbasetest.StrategyBaseTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BonusPointsStrategyTest extends StrategyBaseTest {

    @Test
    void testNoParticipation() {
        BonusPointsStrategy strategy = new BonusPointsStrategy(testClubs.get(1));
        BigDecimal discount = strategy.getDiscount(testMembers.get(5));
        assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    void testGetDiscountSameAverageAsMemberBP() {
        BonusPointsStrategy strategy = new BonusPointsStrategy(testClubs.get(0));
        BigDecimal discount = strategy.getDiscount(testMembers.get(0)); //member1 has 4BP (full member, group training)
        //averageBP = totalBP(12) / clubMembersCount(3)
        assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    void testGetDiscountCorrectDiscount() {
        BonusPointsStrategy strategy = new BonusPointsStrategy(testClubs.get(2));
        //member1 has 24BP (full member, group training, personal training)
        BigDecimal discount = strategy.getDiscount(testMembers.get(0));

        //member has 24BP
        //averageBP = totalBP(32) / clubMembersCount(3) = 10.66
        //exceededBP = 24 - 10.66 = 13.3333334
        //discount = 0.25 * 13.33 = 3.3325
        System.out.println("Member1 has: " + testMembers.get(0).getBonusPoints().getPoints(testClubs.get(2)));
        System.out.println("Member2 has: " + testMembers.get(1).getBonusPoints().getPoints(testClubs.get(2)));
        System.out.println("Member3 has: " + testMembers.get(2).getBonusPoints().getPoints(testClubs.get(2)));
        assertEquals(BigDecimal.valueOf(3.33), discount);
    }

    @Test
    void testGetDiscountMemberBPExceedsMaximum() {
        BonusPointsStrategy strategy = new BonusPointsStrategy(testClubs.get(0));
        testMembers.get(0).getBonusPoints().addBonusPoints(testClubs.get(0), 116);
        BigDecimal discount = strategy.getDiscount(testMembers.get(0));
        //member has 140
        //averageBP = totalBP(148) / clubMembersCount(3) = 49.33
        //exceededBP = 140 - 49.33
        //0.25 * exceededBP >= 20;  exceeded = 80
        assertEquals(BigDecimal.valueOf(20.0), discount);

    }
}