package game;
/**
 * Az életjáték szabályait reprezentáló osztály.
 * A szabályok két részből állnak: születési (birth) és túlélési (survival) szabályok.
 */
public class Rules {

    private int[] birthRules;   // A születési szabályokat tartalmazó tömb.
    private int[] survivalRules;    // A túlélési szabályokat tartalmazó tömb.

    /**
     * Konstruktor, inicializálja az osztályt a megadott szabálysztring alapján.
     *
     * @param ruleString A szabályokat tartalmazó sztring. Például: "23/3"
     * @throws IllegalArgumentException Ha a szabálysztring érvénytelen formátumú.
     */
    public Rules(String ruleString) {
        parseRules(ruleString);
    }
    /**
     * Feldolgozza a szabályokat tartalmazó sztringet és inicializálja a születési és túlélési szabályokat.
     *
     * @param ruleString A szabályokat tartalmazó sztring. Például: "23/3"
     * @throws IllegalArgumentException Ha a szabálysztring érvénytelen formátumú.
     */
    public void parseRules(String ruleString) {
        String[] rules = ruleString.split("/");
        if (rules.length == 2) {
            birthRules = parseRulePart(rules[0]);
            survivalRules = parseRulePart(rules[1]);
        } else {
            throw new IllegalArgumentException("Invalid rule format: " + ruleString);
        }
    }
    /**
     * Feldolgozza a szabályrészt és inicializálja a szabályokat egy tömbben.
     *
     * @param rulePart A szabályrészt tartalmazó sztring. Például: "23"
     * @return Az inicializált szabályokat tartalmazó tömb.
     */
    public int[] parseRulePart(String rulePart) {
        int[] ruleArray = new int[rulePart.length()];
        for (int i = 0; i < rulePart.length(); i++) {
            char digit = rulePart.charAt(i);
            ruleArray[i] = Character.getNumericValue(digit);
        }
        return ruleArray;
    }
    /**
     * Meghatározza, hogy egy cella él-e a játékszabályok alapján.
     *
     * @param currentState   A cella aktuális állapota (életben van vagy halott).
     * @param liveNeighbors  A cella élő szomszédjainak száma.
     * @return True, ha a cella élni fog a játékszabályok szerint, különben false.
     */
    public boolean shouldLive(boolean currentState, int liveNeighbors) {
        if (!currentState) { // Dead cell
            for (int birthRule : birthRules) {
                if (liveNeighbors == birthRule) {
                    return true;
                }
            }
        } else {
            for (int survivalRule : survivalRules) {
                if (liveNeighbors == survivalRule) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Visszaadja a születési szabályokat tartalmazó tömböt.
     *
     * @return A születési szabályokat tartalmazó tömb.
     */
    public int[] getBirthRules() {
        return birthRules;
    }
    /**
     * Visszaadja a túlélési szabályokat tartalmazó tömböt.
     *
     * @return A túlélési szabályokat tartalmazó tömb.
     */
    public int[] getSurvivalRules() {
        return survivalRules;
    }
}
