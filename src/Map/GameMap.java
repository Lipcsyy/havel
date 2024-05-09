package Map;

import java.util.*;
import java.util.stream.Collectors;
import Room.Room;

public class GameMap {
    private final int width;
    private final int height;
    private HashMap<Room, Set<Room>> adjacencyList = new HashMap<>();
    private final Random rand = new Random();

    private static final int N = 1, S = 2, E = 4, W = 8;
    private static final int[] DIRECTIONS = {N, S, E, W};

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        // Initialize the graph with all cells having no neighbors
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Room cell = new Room();
                cell.setCoordinates(x, y);
                adjacencyList.put(cell, new HashSet<>());
            }
        }
    }

    public void generateMaze() {
        System.out.println("Generating maze...");
        // Start with a random cell
        Room startCell = getRandomCell();
        startCell.in = true;
        addFrontier(startCell);

        while (!allCellsIn()) {

            System.out.println("Cells in: " + adjacencyList.keySet().stream().filter(c -> c.in).count() + " / " + adjacencyList.size());

            Room frontierCell = removeRandomFrontierCell();
            List<Room> inNeighbors = getInNeighbors(frontierCell);

            if (!inNeighbors.isEmpty()) {
                Room neighbor = inNeighbors.get(rand.nextInt(inNeighbors.size()));
                connectCells(neighbor, frontierCell);
                addFrontier(frontierCell);
            }
        }

        System.out.println("Maze generated!");
    }

    private Room getRandomCell() {
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);
        return findCell(x, y);
    }

    private void addFrontier(Room cell) {
        int x = cell.x;
        int y = cell.y;
        for (int direction : DIRECTIONS) {
            int nx = x, ny = y;
            switch (direction) {
                case N: ny--; break;
                case S: ny++; break;
                case E: nx++; break;
                case W: nx--; break;
            }
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                Room neighbor = findCell(nx, ny);
                if (!neighbor.in && !adjacencyList.get(neighbor).contains(cell)) {
                    adjacencyList.get(neighbor).add(cell);
                }
            }
        }
    }

    private List<Room> getInNeighbors(Room cell) {
        return adjacencyList.get(cell).stream().filter(c -> c.in).collect(Collectors.toList());
    }

    private void connectCells(Room a, Room b) {
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a);
        a.in = true;
        b.in = true;
    }

    private boolean allCellsIn() {
        return adjacencyList.keySet().stream().allMatch(c -> c.in);
    }

    private Room removeRandomFrontierCell() {
        List<Room> frontierCells = new ArrayList<>(adjacencyList.keySet().stream().filter(c -> !c.in && !adjacencyList.get(c).isEmpty()).toList());
        System.out.println("Frontier cells: " + frontierCells.size());
        return frontierCells.remove(rand.nextInt(frontierCells.size()));
    }

    private Room findCell(int x, int y) {
        for (var cell : adjacencyList.keySet()) {
            if (cell.x == x && cell.y == y) {
                return cell;
            }
        }
        return null; // Should never happen
    }

    public int getWidth() {
        return width;
    }

    public HashMap<Room, Set<Room>> getAdjacencyList() {
        return adjacencyList;
    }

    public void displayMaze() {
        System.out.print(" ");
        for (int i = 0; i < width * 2 - 1; i++) {
            System.out.print("_");
        }
        System.out.println();

        for (int y = 0; y < height; y++) {
            System.out.print("|");
            for (int x = 0; x < width; x++) {
                Room cell = findCell(x, y);
                int finalY = y;
                System.out.print(adjacencyList.get(cell).stream().anyMatch(c -> c.y == finalY + 1) ? " " : "_");
                if (x < width - 1) {
                    Room rightCell = findCell(x + 1, y);
                    System.out.print(adjacencyList.get(cell).contains(rightCell) ? " " : "|");
                } else {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }
}