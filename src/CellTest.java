import static org.junit.Assert.*;

import game.Cell;
import org.junit.Test;

public class CellTest {

    @Test
    public void testCellValue() {
        Cell cell = new Cell(1, 1);
        assertFalse(cell.isAlive());
        cell.setAlive(true);
        assertTrue(cell.isAlive());
    }

    @Test
    public void testCellPosition() {
        Cell cell = new Cell(2, 3);
        assertEquals(2, cell.getRow());
        assertEquals(3, cell.getColumn());
    }
}
