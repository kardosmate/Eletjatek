package game;
import java.io.Serializable;

/**
 * Az életjáték táblájának egy celláját reprezentáló osztály.
 * Minden cellának van pozíciója, élő státusza, valamint referencia egy grafikus gombra.
 */


public class Cell implements Serializable {

    private int row;            // A cella sorindexe.
    private int column;         // A cella oszlopindexe.
    private boolean alive;      // A cella aktuális élő státusza.
    private CellButton button;  // A cellához tartozó grafikus gomb referencia.
    private boolean should_live;// A jelző, hogy a cella élni fog-e a következő generációban.

    /**
     * Konstruál egy új Cell objektumot a megadott sor- és oszlopindexekkel.
     *
     * @param r A cella sorindexe.
     * @param c A cella oszlopindexe.
     */
    public Cell(int r, int c ){
        row = r;
        column = c;
        alive = false;
        should_live = false;
        button = null;
    }
    /**
     * Konstruál egy új Cell objektumot a megadott sor-, oszlopindexekkel és élő státusszal.
     *
     * @param r A cella sorindexe.
     * @param c A cella oszlopindexe.
     * @param b A cella kezdeti élő státusza.
     */
    public Cell(int r, int c, boolean b ){
        row = r;
        column = c;
        alive = b;
        should_live = false;
        button = null;
    }
    /**
     * Visszaadja a cella oszlopindexét.
     *
     * @return Az oszlopindex.
     */
    public int getColumn() {
        return column;
    }
    /**
     * Visszaadja a cellához tartozó grafikus gombot.
     *
     * @return A CellButton objektum.
     */
    public CellButton getButton() {
        return button;
    }
    /**
     * Visszaadja a cella sorindexét.
     *
     * @return A sorindex.
     */
    public int getRow() {
        return row;
    }
    /**
     * Ellenőrzi, hogy a cella jelenleg élő-e.
     *
     * @return True, ha a cella élő, különben false.
     */
    public boolean isAlive() {
        return alive;
    }
    /**
     * Beállítja a cella élő státuszát.
     *
     * @param alive Az új élő státusz.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    /**
     * Beállítja a cellához tartozó grafikus gombot.
     *
     * @param button A CellButton objektum.
     */
    public void setButton(CellButton button) {
        this.button = button;
    }
    /**
     * Ellenőrzi, hogy a cella élni fog-e a következő generációban.
     *
     * @return True, ha a cella élni fog, különben false.
     */
    public boolean isShould_live() {
        return should_live;
    }
    /**
     * Beállítja a cella sorsát, azaz hogy élni fog-e a következő generációban.
     *
     * @param fate Az új sorsa a cellának.
     */
    public void setFate(boolean fate){this.should_live = fate;}
}
