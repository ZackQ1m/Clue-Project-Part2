package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
    private TestBoardCell[][] grid;
    private Set<TestBoardCell> targets;
    private Set<TestBoardCell> visited;
    private static final int BOARD_SIZE = 4;

    public TestBoard() {
        grid = new TestBoardCell[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = new TestBoardCell(i, j);
            }
        }

        buildAdjacencies();

        targets = new HashSet<>();
        visited = new HashSet<>();
    }

    private void buildAdjacencies() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                TestBoardCell cell = grid[r][c];
                if (r - 1 >= 0) cell.addAdjacency(grid[r - 1][c]);
                if (r + 1 < BOARD_SIZE) cell.addAdjacency(grid[r + 1][c]);
                if (c - 1 >= 0) cell.addAdjacency(grid[r][c - 1]);
                if (c + 1 < BOARD_SIZE) cell.addAdjacency(grid[r][c + 1]);
            }
        }
    }
// Minor update for clarity
    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public void calcTargets(TestBoardCell startCell, int pathlength) {
        targets.clear();
        visited.clear();
        visited.add(startCell);
        findAllTargets(startCell, pathlength);
        visited.remove(startCell);
    }

    private void findAllTargets(TestBoardCell current, int stepsRemaining) {
        for (TestBoardCell neighbor : current.getAdjList()) {
            if (visited.contains(neighbor)) {
                continue;
            }

            if (neighbor.getOccupied() && !neighbor.isRoom()) {
                continue;
            }

            if (stepsRemaining == 1 || neighbor.isRoom()) {
                targets.add(neighbor);
                if (neighbor.isRoom()) {
                    continue;
                } else {
                    continue;
                }
            }

            visited.add(neighbor);
            findAllTargets(neighbor, stepsRemaining - 1);
            visited.remove(neighbor);
        }
    }

    public Set<TestBoardCell> getTargets() {
        return this.targets;
    }
}
