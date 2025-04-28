import static org.junit.Assert.*;

import game.Cell;
import game.CellMap;
import org.junit.Test;

public class CellMapTest {



    @Test
    public void testCountCellsInRow() {
        CellMap cellMap = new CellMap();
        Cell cell1 = new Cell(0,0);
        Cell cell2 = new Cell(1,0);
        Cell cell3 = new Cell(2,1);
        cellMap.addCell(cellMap,cell1);
        cellMap.addCell(cellMap,cell2);
        cellMap.addCell(cellMap,cell3);

        assertEquals(1, cellMap.countCellsInRow(0));
        assertEquals(1, cellMap.countCellsInRow(1));
        assertEquals(0, cellMap.countCellsInRow(3));
    }
    @Test
    public void testCountLiveNeighbors() {
        CellMap cellMap = new CellMap();
        cellMap.addCell(cellMap, new Cell(0, 0, true));
        cellMap.addCell(cellMap, new Cell(0, 1, false));
        cellMap.addCell(cellMap, new Cell(1, 0, true));
        cellMap.addCell(cellMap, new Cell(1, 1, false));
        cellMap.addCell(cellMap, new Cell(1, 2, true));
        cellMap.addCell(cellMap, new Cell(2, 1, false));

        assertEquals(3, cellMap.count_live_neighbors(cellMap, cellMap.getKey(1, 1)));
        assertEquals(1, cellMap.count_live_neighbors(cellMap, cellMap.getKey(1, 0)));
        assertEquals(3, cellMap.count_live_neighbors(cellMap, cellMap.getKey(0, 1)));
    }


    @Test
    public void testSaveAndLoad() {
        CellMap originalCellMap = new CellMap();
        originalCellMap.addCell(originalCellMap, new Cell(0, 0, true));
        originalCellMap.addCell(originalCellMap, new Cell(1, 1, false));
        originalCellMap.addCell(originalCellMap, new Cell(2, 2, true));

        originalCellMap.saveToFile("testSaveAndLoad.ser");

        CellMap loadedCellMap = CellMap.loadFromFile("testSaveAndLoad.ser");

        assertNotNull(loadedCellMap);
        assertEquals(originalCellMap.size(), loadedCellMap.size());

        for (String key : originalCellMap.keySet()) {
            assertTrue(loadedCellMap.containsKey(key));
            assertEquals(originalCellMap.get(key).isAlive(), loadedCellMap.get(key).isAlive());
        }
    }
}
