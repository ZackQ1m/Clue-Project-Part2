package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
    private int row;
    private int col;
    private Set<TestBoardCell> adjList;
    private boolean isRoom;
    private boolean isOccupied;
 // Minor update for clarity

    public TestBoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        this.adjList = new HashSet<>();
        this.isRoom = false;
        this.isOccupied = false;
    }

    public void addAdjacency(TestBoardCell cell) {
        if (cell != null) {
            adjList.add(cell);
        }
    }

    public Set<TestBoardCell> getAdjList() {
        return this.adjList;
    }

    public void setRoom(boolean isRoom) {
        this.isRoom = isRoom;
    }

    public boolean isRoom() {
        return this.isRoom;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean getOccupied() {
        return this.isOccupied;
    }
}
