package game;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Az életjáték celláinak térképét reprezentáló osztály, ami a HashMap osztályból származik.
 * A térkép kulcsként használja a cellák pozícióját, és a cellákat tárolja azokhoz rendelt értékekkel.
 */
public class CellMap extends HashMap<String,Cell> implements Serializable {
    /** A cellákat tároló HashMap. */
    public HashMap<String,Cell> cellmap;
    /**
     * Konstruktor, létrehoz egy üres CellMap objektumot.
     */
    public CellMap(){
        cellmap = new HashMap<>();
    }

    /**
     * Hozzáad egy cellát a térképhez.
     *
     * @param m A CellMap, amelyhez a cellát hozzáadjuk.
     * @param c A hozzáadandó Cell objektum.
     */
    public void addCell(CellMap m, Cell c){
        String key = getKey(c.getRow(),c.getColumn());
        m.cellmap.put(key, c);
    }

    /**
     * Törli az összes cellát a térképről.
     */
    public void clearCells(){
        cellmap.clear();
    }
    /**
     * Visszaadja a cella pozíciójának kulcsát a sor- és oszlopindexek alapján.
     *
     * @param row    A cella sorindexe.
     * @param column A cella oszlopindexe.
     * @return A cella pozíciójának megfelelő kulcsa.
     */
    public String getKey(int row, int column){
        return String.valueOf(row) + '-' + String.valueOf(column);
    }
    /**
     * Megszámolja egy adott cella szomszédos élő celláinak számát.
     *
     * @param m   A CellMap, amely tartalmazza a cellát és annak szomszédait.
     * @param key A cella pozíciójának kulcsa.
     * @return Az élő szomszédos cellák száma.
     */
    public int count_live_neighbors(CellMap m,String key){
        int cnt = 0;
        Cell cell = m.cellmap.get(key);



        if(cell != null){
            int row = cell.getRow();
            int column = cell.getColumn();


            int[][] neighbors ={
                    {-1,-1}, {-1,0}, {-1,1},
                    {0,-1},          {0,1},
                    {1,-1},  {1,0},  {1,1}
            };

            for(int[] neighbor: neighbors){
                int neighbor_row = row + neighbor[0];
                int neighbor_column = column + neighbor[1];


                if(cellmap.containsKey(getKey(neighbor_row,neighbor_column))){
                    Cell neighbor_cell = cellmap.get(String.valueOf(neighbor_row) + '-' + String.valueOf(neighbor_column));
                    if(neighbor_cell.isAlive()){
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }
    /**
     * Megszámolja egy adott sorban lévő cellák számát.
     *
     * @param row A sor, amelyben a cellákat számoljuk.
     * @return A sorban lévő cellák száma.
     */
    public int countCellsInRow(int row) {
        int count = 0;
        for (Map.Entry<String, Cell> entry : cellmap.entrySet()) {
            Cell cell = entry.getValue();
            if (cell.getRow() == row) {
                count++;
            }
        }
        return count;
    }
    /**
     * Elmenti a CellMap objektumot egy fájlba.
     *
     * @param filename A fájl neve, amibe elmentjük a CellMap-ot.
     */
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("CellMap saved to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Betölti a CellMap objektumot egy fájlból.
     *
     * @param filename A fájl neve, amiből betöltjük a CellMap-ot.
     * @return A betöltött CellMap objektum.
     */
    public static CellMap loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            CellMap cellMap = (CellMap) ois.readObject();
            System.out.println("CellMap loaded from: " + filename);
            return cellMap;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new CellMap();
    }


}
