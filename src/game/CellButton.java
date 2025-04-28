package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Egy gombot reprezentáló osztály a játék celláihoz.
 * A gombot a cellához rendelt szín és állapot alapján frissíti.
 */
public class CellButton extends JButton implements Serializable {
    private Cell cell;  // A gombhoz tartozó cella objektum.
    private Color alive_c = Color.WHITE;
    private Color dead_c = Color.BLACK;
    /**
     * Konstruktor, inicializálja a CellButton objektumot a megadott cella alapján.
     *
     * @param cell A gombhoz rendelt Cell objektum.
     */
    public CellButton(Cell cell) {
        this.cell = cell;
        updateAppearance();


        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cell.setAlive(!cell.isAlive());
                updateAppearance();
            }
        });
    }

    /**
     * Frissíti a gomb megjelenését a cella állapota alapján.
     */
    public void updateAppearance() {
        if (cell.isAlive()) {
            setBackground(alive_c);
        } else {
            setBackground(dead_c);
        }
        setOpaque(true);
        setBorderPainted(false);
    }
    /**
     * Visszaadja a gombhoz tartozó Cell objektumot.
     *
     * @return A gombhoz rendelt Cell objektum.
     */
    public Cell getCell() {
        return cell;
    }

    public Color getAlive_c() {
        return alive_c;
    }

    public Color getDead_c() {
        return dead_c;
    }

    public void setAlive_c(Color alive_c) {
        this.alive_c = alive_c;
    }

    public void setDead_c(Color dead_c) {
        this.dead_c = dead_c;
    }
}