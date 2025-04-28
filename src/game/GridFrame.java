package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;


/**
 * A játékteret reprezentáló és irányító keret (frame) osztály.
 * Ezen keret tartalmazza a cellákat és azokat kezelő vezérlőket.
 */

public class GridFrame extends JFrame {
    private CellMap map;    // A játékteret reprezentáló CellMap objektum.
    private JPanel gridPanel;   // A cellák megjelenítéséért felelős panel.
    private JPanel controlPanel;    // A vezérlőelemeket tartalmazó panel.
    private JTextField sizeTextField = new JTextField("Size");    // Szövegmező a játékter méretének beállításához.
    private JTextField ruleTextField;   // Szövegmező a játékszabályok beállításához.
    private boolean running;    // Flag, ami jelzi, hogy éppen fut-e a szimuláció.
    private Thread simulationThread;    // Szimulációt futtató szál.

    /**
     * Konstruktor, inicializálja a GridFrame objektumot és beállítja a keret tulajdonságait.
     */
    public GridFrame() {
        setTitle("Eletjatek");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLayout(new BorderLayout());

        // Grid panel létrehozása
        gridPanel = new JPanel(new GridLayout());
        map = new CellMap();


        sizeTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sizeTextField.getText().equals("Size")) {
                    sizeTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (sizeTextField.getText().isEmpty()) {
                    sizeTextField.setText("Size");
                }
            }
        });

        // A méret beállító szövegmező eseménykezelője
        sizeTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateGrid();
            }
        });

        ruleTextField = new JTextField("B3/S23");   // Alapértelmezett játékszabályok
        ruleTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Rules r = new Rules(ruleTextField.getText());
                }
                catch (IllegalArgumentException q){
                    JOptionPane.showMessageDialog(controlPanel,q.getMessage());
                }
            }
        });

        // Control panel létrehozása
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        // Vezérlőelemek hozzáadása a control panelhez
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton colorChooserButton_alive = new JButton("Alive color");
        JButton colorChooserButton_dead = new JButton("Dead color");



        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(sizeTextField);
        controlPanel.add(ruleTextField);
        controlPanel.add(colorChooserButton_alive);
        controlPanel.add(colorChooserButton_dead);

        // Gombok eseménykezelői
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Rules r = new Rules(ruleTextField.getText());
                }
                catch (IllegalArgumentException q){
                    JOptionPane.showMessageDialog(controlPanel,q.getMessage());
                }
                running = false;
                startSimulation();

            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Enter the filename to save:");
                if (filename != null && !filename.isEmpty()) {
                    map.saveToFile(filename);
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Enter the filename to load:");
                if (filename != null && !filename.isEmpty()) {
                    map = CellMap.loadFromFile(filename);


                    load_Grid();
                }
            }
        });

        ActionListener colorChooserListener_a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(controlPanel, "Alive color", Color.white);
                if (selectedColor != null) {
                    // Set the background color of the buttons
                    for(Cell cell : map.cellmap.values()){
                        cell.getButton().setAlive_c(selectedColor);
                        cell.getButton().updateAppearance();
                    }
                    revalidate();
                    repaint();
                }
            }
        };
        colorChooserButton_alive.addActionListener(colorChooserListener_a);

        ActionListener colorChooserListener_d = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(controlPanel, "Dead Color", Color.black);
                if (selectedColor != null) {
                    // Set the background color of the buttons
                    for(Cell cell : map.cellmap.values()){
                        cell.getButton().setDead_c(selectedColor);
                        cell.getButton().updateAppearance();
                    }
                    revalidate();
                    repaint();
                }
            }
        };
        colorChooserButton_dead.addActionListener(colorChooserListener_d);



        // Panel hozzáadása a kerethez
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Betölti a játékteret a beolvasott fájl objektum alapján.
     */
    private void load_Grid() {
        int size = map.countCellsInRow(1);
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(size, size));
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                String key = row + "-" + column;
                Cell cell = map.cellmap.get(key);
                CellButton cellButton = cell.getButton();
                cell.setButton(cellButton);
                gridPanel.add(cellButton);
            }
        }
        sizeTextField.setText(String.valueOf(map.countCellsInRow(1)));

        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    /**
     * Generálja a játékteret a méretet beállító szövegmező alapján.
     */
    void generateGrid() {
        String sizeText = sizeTextField.getText();

        try {
            int size = Integer.parseInt(sizeText);

            gridPanel.setLayout(new GridLayout(size, size));

            gridPanel.removeAll();
            map.clearCells();
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {
                    String key = row + "-" + column;
                    Cell cell = map.cellmap.get(key);
                    if (cell == null) {
                        cell = new Cell(row, column);
                        map.cellmap.put(key, cell);
                    }
                    CellButton cellButton = new CellButton(cell);
                    cell.setButton(cellButton);
                    gridPanel.add(cellButton);
                }
            }


            pack();
            setLocationRelativeTo(null);
            revalidate();
            repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.");
        }
    }

    /**
     * Elindítja a játék szimulációját a megadott szabályok alapján.
     */
    private void startSimulation() {
        Rules rules = new Rules(ruleTextField.getText());
        running = true;
        ArrayList<Boolean> bools = new ArrayList<Boolean>();

        simulationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            for (Component component : gridPanel.getComponents()) {
                                if (component instanceof CellButton) {
                                    CellButton cellButton = (CellButton) component;
                                    Cell cell = cellButton.getCell();
                                    // Apply the rules to the cell state
                                    boolean nextState = rules.shouldLive(cell.isAlive(), map.count_live_neighbors(map, String.valueOf(cell.getRow()) + '-' + String.valueOf(cell.getColumn())));
                                    cell.setFate(nextState);
                                    cellButton.updateAppearance();
                                }
                            }
                            for (Component component : gridPanel.getComponents()) {
                                if (component instanceof CellButton) {
                                    CellButton cellButton = (CellButton) component;
                                    Cell cell = cellButton.getCell();
                                    cell.setAlive(cell.isShould_live());
                                    cellButton.updateAppearance();
                                }
                            }
                        }
                    });

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        simulationThread.start();
    }

    /**
     * Megállítja a játék szimulációját.
     */
    private void stopSimulation() {
        running = false;
        try {
            if (simulationThread != null) {
                simulationThread.join();
                revalidate();
                repaint();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * A program belépési pontja.
     *
     * @param args A parancssori argumentumok.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GridFrame().setVisible(true);
            }
        });
    }
}