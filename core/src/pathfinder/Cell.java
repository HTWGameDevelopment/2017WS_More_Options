package pathfinder;

/**
 * An experimental cell for the pathfinder algorithm.
 * Will be changed to the actual rooms in the future as this is only for the test implementation of the pathfinding algorithm.
 */
public class Cell {
    private int heuristicCost = 0;
    private int finalCost = 0;
    private int x;
    private int y;
    private Cell parent;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    @Override

    public String toString() {
    return ("[" + this.x + ", " + this.y + "]");
    }
}
