import static org.junit.Assert.*;

import game.Rules;
import org.junit.Test;

public class RulesTest {

    @Test
    public void testParseRules() {
        Rules rules = new Rules("B3/S23");

        int[] birthRules = rules.getBirthRules();
        int[] survivalRules = rules.getSurvivalRules();

        assertNotNull(birthRules);
        assertNotNull(survivalRules);

        assertEquals(2, birthRules.length);
        assertEquals(3, survivalRules.length);

        assertEquals(3, birthRules[1]);
        assertEquals(2, survivalRules[1]);
        assertEquals(3, survivalRules[2]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRuleFormat() {
        Rules rules = new Rules("hahaha");
        rules.parseRules("InvalidRule");
    }

    @Test
    public void testParseRulePart() {
        Rules rules = new Rules("B3/S23");
        int[] ruleArray = rules.parseRulePart("S23");

        assertNotNull(ruleArray);
        assertEquals(3, ruleArray.length);
        assertEquals(2, ruleArray[1]);
        assertEquals(3, ruleArray[2]);
    }

    @Test
    public void testShouldLive() {
        Rules rules = new Rules("B3/S23");

        assertTrue(rules.shouldLive(true, 2)); // Survival
        assertTrue(rules.shouldLive(true, 3)); // Survival
        assertFalse(rules.shouldLive(true, 1)); // Underpopulation
        assertFalse(rules.shouldLive(true, 4)); // Overpopulation

        assertTrue(rules.shouldLive(false, 3)); // Reproduction
        assertFalse(rules.shouldLive(false, 2)); // Empty stays empty
    }
}
