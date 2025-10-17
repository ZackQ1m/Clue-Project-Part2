package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
    private TestBoard board;

    @BeforeEach
    public void setUp() {
        // The TestBoard constructor creates the grid, but the adjacency
        // lists and targets remain empty because the methods are stubbed.
        board = new TestBoard();
    }

    // --- ADJACENCY TESTS --- :))))))))
    // These tests will fail because getAdjList() returns an empty set.

    @Test
    public void testAdjacencyTopLeft() {
        // Test the top-left corner (0, 0)
        TestBoardCell cell = board.getCell(0, 0);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(1, 0)));
        assertTrue(testList.contains(board.getCell(0, 1)));
        assertEquals(2, testList.size());
    }

    @Test
    public void testAdjacencyBottomRight() {
        // Test the bottom-right corner (3, 3)
        TestBoardCell cell = board.getCell(3, 3);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(2, 3)));
        assertTrue(testList.contains(board.getCell(3, 2)));
        assertEquals(2, testList.size());
    }

    @Test
    public void testAdjacencyRightEdge() {
        // Test a cell on the right edge (1, 3)
        TestBoardCell cell = board.getCell(1, 3);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(0, 3)));
        assertTrue(testList.contains(board.getCell(2, 3)));
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertEquals(3, testList.size());
    }

    @Test
    public void testAdjacencyLeftEdge() {
        // Test a cell on the left edge (3, 0)
        TestBoardCell cell = board.getCell(3, 0);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(2, 0)));
        assertTrue(testList.contains(board.getCell(3, 1)));
        assertEquals(2, testList.size());
    }
    
    @Test
    public void testAdjacencyMiddle() {
        // Test a cell in the middle of the board (2, 2)
        TestBoardCell cell = board.getCell(2, 2);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertTrue(testList.contains(board.getCell(3, 2)));
        assertTrue(testList.contains(board.getCell(2, 1)));
        assertTrue(testList.contains(board.getCell(2, 3)));
        assertEquals(4, testList.size());
    }


    // --- TARGET CALCULATION TESTS --- :DDDDDD
    // These tests will fail because getTargets() returns an empty set.
    
    @Test
    public void testTargetsNormal() {
        // Test basic movement on an empty board, roll of 3 from (0,0)
        TestBoardCell cell = board.getCell(0, 0);
        board.calcTargets(cell, 3);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(6, targets.size());
        assertTrue(targets.contains(board.getCell(3, 0)));
        assertTrue(targets.contains(board.getCell(2, 1)));
        assertTrue(targets.contains(board.getCell(0, 1)));
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(0, 3)));
        assertTrue(targets.contains(board.getCell(1, 0)));
    }
    
    @Test
    public void testTargetsMaxRoll() {
        // Test basic movement with a max roll of 6 from (0,0)
        TestBoardCell cell = board.getCell(0, 0);
        board.calcTargets(cell, 6);
        Set<TestBoardCell> targets = board.getTargets();
        // Exclude the start cell; there are 7 valid targets
        assertEquals(7, targets.size());
        assertTrue(targets.contains(board.getCell(3, 3)));
        assertTrue(targets.contains(board.getCell(2, 2)));
        assertTrue(targets.contains(board.getCell(1, 1)));
        assertTrue(targets.contains(board.getCell(0, 2)));
        assertTrue(targets.contains(board.getCell(2, 0)));
        assertTrue(targets.contains(board.getCell(1, 3)));
        assertTrue(targets.contains(board.getCell(3, 1)));
    }

    @Test
    public void testTargetsWithOccupied() {
        // Place an occupied cell at (1,1) to block paths
        board.getCell(1, 1).setOccupied(true);
        TestBoardCell cell = board.getCell(0, 1);
        board.calcTargets(cell, 2);
        Set<TestBoardCell> targets = board.getTargets();
        // (1,1) is blocked, so we can't go through it
        assertEquals(3, targets.size());
        assertTrue(targets.contains(board.getCell(0, 3)));
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(1, 0)));
    }

    @Test
    public void testTargetsWithRoom() {
        // Place a room at (1,2)
        board.getCell(1, 2).setRoom(true);
        TestBoardCell cell = board.getCell(0, 2);
        board.calcTargets(cell, 4); // A roll of 4
        Set<TestBoardCell> targets = board.getTargets();
        // Can stop in the room (1,2). Rooms are terminal after entry.
        assertEquals(7, targets.size());
        assertTrue(targets.contains(board.getCell(1, 2))); // room is enterable
        // Reachable (exact 4 steps) without passing through the room:
        assertTrue(targets.contains(board.getCell(0, 0)));
        assertTrue(targets.contains(board.getCell(1, 1)));
        assertTrue(targets.contains(board.getCell(2, 0)));
        assertTrue(targets.contains(board.getCell(2, 2)));
        assertTrue(targets.contains(board.getCell(3, 1)));
        assertTrue(targets.contains(board.getCell(3, 3)));
    }

    @Test
    public void testTargetsMixed() {
        // A room at (0,2) and an occupied cell at (1,0)
        board.getCell(0, 2).setRoom(true);
        board.getCell(1, 0).setOccupied(true);
        TestBoardCell cell = board.getCell(0, 0);
        board.calcTargets(cell, 3);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(3, targets.size());
        assertTrue(targets.contains(board.getCell(0, 2))); // Can enter room
        assertTrue(targets.contains(board.getCell(2, 1)));
        assertTrue(targets.contains(board.getCell(1, 2)));
    }
}
