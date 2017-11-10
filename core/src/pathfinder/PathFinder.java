package pathfinder;

import java.util.PriorityQueue;

/**
 * First try at a pathfinder using the A* algorithm. Expected to be changed.
 *
 * @author Andreas Bonny
 */
public class PathFinder {

    //TODO: change X and Y axis, make smaller methods, assign premade arrays of blocked tiles and make some more tests

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

    private static int width;
    private static int height;

    public PathFinder(int width, int height, int startX, int startY, int targetX, int targetY, int[][] blocked) {
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;

        testPathFinder(blocked);
    }

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
    private static void findPath(Cell[][] grid, int startX, int startY, int targetX, int targetY) {
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

            updateCostForCells(grid, current);
        }
    }

    private static void updateCostForCells(Cell[][] grid, Cell current) {
        // check for not left border and update the costs
        updateCostNotLeft(grid, current);

        // check for not bottom border and update the costs
        updateCostNotBottom(grid, current);

        // check for not top border
        updateCostNotTop(grid, current);

        // check for not right border
        updateCostNotRight(grid, current);
    }

    private static void updateCostNotRight(Cell[][] grid, Cell current) {
        Cell target;

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

    private static void updateCostNotTop(Cell[][] grid, Cell current) {
        Cell target;

        if (current.getY() + 1 < grid[0].length) {
            target = grid[current.getX()][current.getY() + 1];
            updateCost(current, target, current.getFinalCost() + V_H_COST);
        }

    }

    private static void updateCostNotBottom(Cell[][] grid, Cell current) {
        Cell target;

        if (current.getY() - 1 >= 0) {
            target = grid[current.getX()][current.getY() - 1];
            updateCost(current, target, current.getFinalCost() + V_H_COST);
        }
    }

    private static void updateCostNotLeft(Cell[][] grid, Cell current) {
        Cell target;

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
    }

    /**
     * prints the map to the shell before A* is used and then after it it used
     * also shows the path used
     * @param blocked collection of blocked tiles
     */
    public static void testPathFinder(int[][] blocked) {
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
        initializeMap(grid, width, height);

        grid[startX][startY].setFinalCost(0);

        // assign blocked cells
        for (int i = 0; i < blocked.length; ++i) {
            setBlocked(blocked[i][0], blocked[i][1]);
        }

        // display initial map
        printMap(width, height, startX, startY, targetX, targetY);

        // search best path
        findPath(grid, startX, startY, targetX, targetY); 

        // print the map with the values
        System.out.println("\nValues of the Cells: ");

        printMapWithValues(width, height, grid);

        printPath(closed, targetX, targetY);
    }

    /**
     * prints the path from the start to the target found by the A* algorithm
     * @param closed the nodes taken
     * @param targetX x-coordinate of the target location
     * @param targetY y-coordinate of the target location
     */
    private static void printPath(boolean[][] closed, int targetX, int targetY) {
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

    /**
     * prints a grid with the calculated final costs
     * @param width width of the grid
     * @param height height of the grid
     * @param grid the map the values will be printed on
     */
    private static void printMapWithValues(int width, int height, Cell[][] grid) {
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
    }

    /**
     * initializes a grid with heuristic costs
     * @param grid the map
     * @param width the width of the map
     * @param height the height of the map
     */
    private static void initializeMap(Cell[][] grid, int width, int height) {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].setHeuristicCost(Math.abs(i - targetX) + Math.abs(j - targetY));
            }
        }
    }

    /**
     * prints the map to the shell
     * @param width width of the map
     * @param height height of the map
     * @param startX x-coordinate of the starting point
     * @param startY y-coordinate of the starting point
     * @param targetX x-coordinate of the target location
     * @param targetY y-coordinate of the target location
     */
    private static void printMap(int width, int height, int startX, int startY, int targetX, int targetY) {
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
    }

    public static void main(String[] args) throws Exception {
        new PathFinder(5, 5, 0, 0, 0, 4, new int[][]{{2,2}, {1,1}, {3,3}, {0, 3}});
        new PathFinder(5, 5, 0, 0, 2, 4, new int[][]{{1,0},{1,1}, {0,3},{2,3}, {3,2}, {4,1}});
    }
}
