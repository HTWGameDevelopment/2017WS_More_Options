package pathfinder;

import java.util.PriorityQueue;

/**
 * First try at a pathfinder using the A* algorithm. Expected to be changed.
 *
 * @author Andreas Bonny
 */
public class PathFinder {

    //TODO: change X and Y axis, make smaller methods, assign premade arrays of blocked tiles and generally try to make this better

    // costs are chosen based on the characteristics of a triangle --> diagonal has higher cost than vertical or horizontal
    private static final int DIAGONAL_COST = 14;
    private static final int V_H_COST = 10;

    // the map
    private static Cell[][] grid;

    // cells left to be checked
    private static PriorityQueue<Cell> open;

    private static boolean closed[][];

    // starting coordinates of the cell
    private static int startX;
    private static int startY;

    // target of the algorithm
    private static int targetX;
    private static int targetY;

    /**
     * sets a coordinate inside the "room" to be impassable
     * @param x x-coordinate
     * @param y y-coordinate
     */
    private static void setBlocked(int x, int y) {
        grid[x][y] = null;
    }

    /**
     * assigns the coordinates of the starting point
     * @param x x-coordinate
     * @param y y-coordinate
     */
    private static void setStart(int x, int y) {
        startX = x;
        startY = y;
    }

    /**
     * assigns the coordinates of the target point
     * @param x x-coordinate
     * @param y y-coordinate
     */
    private static void setTarget(int x, int y) {
        targetX = x;
        targetY = y;
    }

    /**
     * reassigns the final cost of the target cell
     * @param current current cell
     * @param target target cell
     * @param cost current cost which will be updated
     */
    private static void updateCost(Cell current, Cell target, int cost) {
        if (target == null || closed[target.getX()][target.getY()]) {
            return;
        }

        int targetFinalCost = target.getHeuristicCost() + cost;

        boolean inOpen = open.contains(target);

        if (!inOpen || targetFinalCost < target.getFinalCost()) {
            target.setFinalCost(targetFinalCost);
            target.setParent(current);
            if (!inOpen) {
                open.add(target);
            }
        }
    }

    /**
     * actual A* algorithm to find the best path
     */
    public static void findPath() {
        open.add(grid[startX][startY]);

        Cell current;

        while (true) {
            current = open.poll();

            if (current == null) {
                break;
            }

            closed[current.getX()][current.getY()] = true;

            if (current.equals(grid[targetX][targetY])) {
                return;
            }

            Cell target;

            // check for not left border
            if (current.getX() - 1 >= 0) {
                target = grid[current.getX() - 1][current.getY()];
                updateCost(current, target, current.getFinalCost() + V_H_COST);

                // check for not bottom cell
                if (current.getY() - 1 >= 0) {
                    target = grid[current.getX() - 1][current.getY() - 1];
                    updateCost(current, target, current.getFinalCost() + DIAGONAL_COST);
                }

                // check for not top cell
                if (current.getY() + 1 < grid[0].length) {
                    target = grid[current.getX() - 1][current.getY() + 1];
                    updateCost(current, target, current.getFinalCost() + DIAGONAL_COST);
                }
            }

            // check for not bottom border
            if (current.getY() - 1 >= 0) {
                target = grid[current.getX()][current.getY() - 1];
                updateCost(current, target, current.getFinalCost() + V_H_COST);
            }

            // check for not top border
            if (current.getY() + 1 < grid[0].length) {
                target = grid[current.getX()][current.getY() + 1];
                updateCost(current, target, current.getFinalCost() + V_H_COST);
            }

            // check for not right border
            if (current.getX() + 1 < grid.length) {
                target = grid[current.getX() + 1][current.getY()];
                updateCost(current, target, current.getFinalCost() + V_H_COST);

                // check for not bottom cell
                if (current.getY() - 1 >= 0) {
                    target = grid[current.getX() + 1][current.getY() - 1];
                    updateCost(current, target, current.getFinalCost() + DIAGONAL_COST);
                }

                // check for not top cell
                if (current.getY() + 1 < grid[0].length) {
                    target = grid[current.getX() + 1][current.getY() + 1];
                    updateCost(current, target, current.getFinalCost() + DIAGONAL_COST);
                }
            }
        }
    }

    /**
     * prints the map to the shell before A* is used and then after it it used
     * also shows the path used
     * @param width width of the grid
     * @param height height of the grid
     * @param startX x-coordinate of the starting point
     * @param startY y-coordinate of the starting point
     * @param targetX x-coordinate of the target point
     * @param targetY y-coordinate of the target point
     * @param blocked collection of blocked tiles
     */
    public static void testPathFinder(int width, int height, int startX, int startY, int targetX, int targetY, int[][] blocked) {
        // reset

        grid = new Cell[width][height];
        closed = new boolean[width][height];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell) o1;
            Cell c2 = (Cell) o2;

            return c1.getFinalCost() < c2. getFinalCost() ? -1:
                    c1.getFinalCost() > c2.getFinalCost() ? 1:0;
        });

        // start and target location
        setStart(startX, startY);
        setTarget(targetX, targetY);

        // initialize cells with cost
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].setHeuristicCost(Math.abs(i - targetX) + Math.abs(j - targetY));
            }
        }

        grid[startX][startY].setFinalCost(0);

        // assign blocked cells
        for (int i = 0; i < blocked.length; ++i) {
            setBlocked(blocked[i][0], blocked[i][1]);
        }

        // display initial map
        System.out.println("Grid: ");
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (i == startX && j == startY) {
                    System.out.print("SO  ");
                } else if (i == targetX && j == targetY) {
                    System.out.print("TG  ");
                } else if (grid[i][j] != null) {
                    System.out.printf("%-3d ", 0);
                } else {
                    System.out.print("BL  ");
                }
            }
            System.out.println();
        }
        System.out.println();

        // search best path
        findPath();


        System.out.println("\nValues of the Cells: ");

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (grid[i][j] != null) {
                    System.out.printf("%-3d ", grid[i][j].getFinalCost());
                } else {
                    System.out.print("BL  ");
                }
            }
            System.out.println();
        }
        System.out.println();

        if (closed[targetX][targetY]) {
            // trace path

            System.out.println("Path: ");
            Cell current = grid[targetX][targetY];
            System.out.print(current);

            while (current.getParent() != null) {
                System.out.print(" -> " + current.getParent());
                current = current.getParent();
            }
            System.out.println();
        } else {
            System.out.println("No possible path");
        }
    }

    public static void main(String[] args) throws Exception {
        testPathFinder(5, 5, 0, 0, 0, 4, new int[][]{{2,2}, {1,1}, {3,3}, {0, 3}});

        testPathFinder(5, 5, 0, 0, 2, 4, new int[][]{{1,0},{1,1}, {0,3},{2,3}, {3,2}, {4,1}});
    }
}
